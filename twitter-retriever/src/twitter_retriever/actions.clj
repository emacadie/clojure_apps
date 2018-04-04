(ns twitter-retriever.actions
  (:require [clojure.java.io :as io])
  (:require [clojure.pprint :as pp])
  (:require [twitter-retriever.rdbms :as rdbms])
  ; look into this later
  ; (:require [conman.core :refer [with-transaction]])
  (:use [twitter.oauth]
        [twitter.callbacks]
        [twitter.callbacks.handlers]
        [twitter.api.restful])
  (:import [twitter.callbacks.protocols SyncSingleCallback])
  (:gen-class))

(defn get-tweet-map-with-max [user-name my-oauth-creds max-id]
  "max-id comes in as a map with a key of :min"
  ; (println "In get-tweet-map-with-max for ", user-name ", and max-id of ", max-id)
  ; (println "Here is class of '(:min max-id): ", (class (:min max-id)))
  (statuses-user-timeline :oauth-creds my-oauth-creds :params {:screen-name user-name                                                    
                                                                              :max_id max-id  
                                                                              :count 200
                                                                              :include_rts false
                                                                              :tweet_mode "extended"}))

(defn create-user [user-name my-oauth-creds batch-time]
  (def user-map (users-show :oauth-creds my-oauth-creds :params {:screen-name user-name}))
  (println "Here is user-map: ", user-map)
  (rdbms/call-insert-user (:body user-map))
  (def max-id (get-in user-map [ :body :status :id ]))
  ;; can this be a function call?
  (def tweet-map (statuses-user-timeline :oauth-creds my-oauth-creds :params {:screen-name user-name 
                                                                              :count 200
                                                                              :include_rts false
                                                                              :tweet_mode "extended"}))

  (def map-body (:body tweet-map))
  (println "Here is max-id: ", max-id)

  (loop [the-map-body map-body]
    (println "in loop, here is count of the-map-body: ", (count the-map-body))
    (if (> (count the-map-body) 0)
      (do
        (doseq [seq-body the-map-body]
         (do
           (print " Here is id: ", (:id seq-body))
           (rdbms/call-insert-tweet seq-body, batch-time)))
        (println "Here is next-max-id: ", (rdbms/get-min-tweet-id {:screen_name user-name}))
        (def next-max-id (:min (rdbms/get-min-tweet-id {:screen_name user-name})))
        ;; can I make a var to hold next max-id and cut down on some clutter? 
        ;; I need the minimum at this particular point
        ; (recur (:body (get-tweet-map-with-max user-name my-oauth-creds (rdbms/get-min-tweet-id {:screen_name user-name}))))
        (recur (:body (get-tweet-map-with-max user-name my-oauth-creds (dec next-max-id))))))))

(defn get-with-default [arg-map the-key default-value]
  (def result (get arg-map the-key))
  (if (nil? result)
    default-value
    result))

(defn insert-more-tweets [user-name my-oauth-creds batch-time]
  (def starting-tweet-id (rdbms/get-max-tweet-id {:screen_name user-name}))
  (println "Here is starting-tweet-id: ", starting-tweet-id)
  (println "here it is with some validation: ", (get starting-tweet-id :max 642515818043994112) )
  (def tweet-map (statuses-user-timeline :oauth-creds my-oauth-creds :params {:screen_name user-name
                                                                              :since_id (get-with-default starting-tweet-id :max 642515818043994112)
                                                                              ; :since_id (get starting-tweet-id :max 642515818043994112) 
                                                                              ; :max_id 609813046207213568 ; 
                                                                              ; :count 100
                                                                              :include_rts false
                                                                              :tweet_mode "extended"}))
  (def map-body (:body tweet-map))
  (doseq [the-body map-body]
    (do
      (println "Here is id: ", (:id the-body), " Here is text: ", (:full_text the-body))      
      (rdbms/call-insert-tweet the-body batch-time)))
  (println "Done inserting"))

