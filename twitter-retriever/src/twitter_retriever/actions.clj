(ns twitter-retriever.actions
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [twitter-retriever.rdbms :as rdbms]
            [twitter-retriever.process :as process])
  ; look into this later
  ; (:require [conman.core :refer [with-transaction]])
  (:use [twitter.oauth]
        [twitter.callbacks]
        [twitter.callbacks.handlers]
        [twitter.api.restful])
  (:import [twitter.callbacks.protocols SyncSingleCallback])
  (:gen-class))

(defn get-tweet-map [my-oauth-creds params-map]
  "max-id comes in as a map with a key of :min"
  (statuses-user-timeline :oauth-creds my-oauth-creds :params params-map ))

(defn create-user [user-name my-oauth-creds batch-time]
  (def user-map (users-show :oauth-creds my-oauth-creds :params {:screen-name user-name}))
  (println "Here is user-map: ", user-map)
  (rdbms/call-insert-user (:body user-map))
  (def max-id (get-in user-map [ :body :status :id ]))
  
  (def tweet-map (get-tweet-map my-oauth-creds {:screen-name user-name                                                    
                                                :max_id max-id  
                                                :count 200
                                                :include_rts false
                                                :tweet_mode "extended"}))
  
  (def map-body (:body tweet-map))
  (println "Here is max-id: ", max-id)

  (loop [the-map-body map-body]
    (println "in loop, here is count of the-map-body: ", (count the-map-body))
    ;; would "when" be better here?
    (if (> (count the-map-body) 0)
      (do
        (doseq [seq-body the-map-body]
         (do
           (print " Here is id: ", (:id seq-body))
           (rdbms/call-insert-tweet seq-body, batch-time)
           (def processed-string (process/create-processed-string seq-body user-name))
           (rdbms/call-insert-processed-tweet seq-body processed-string batch-time)))
        
        (println "Here is next-max-id: ", (rdbms/get-min-tweet-id {:screen_name user-name}))
        (def next-max-id (:min (rdbms/get-min-tweet-id {:screen_name user-name})))
        (recur (:body (get-tweet-map my-oauth-creds 
                                     {:screen-name user-name                                                    
                                      :max_id (dec next-max-id) 
                                      :count 200
                                      :include_rts false
                                      :tweet_mode "extended"})))))))

(defn get-with-default [arg-map the-key default-value]
  (def result (get arg-map the-key))
  (if (nil? result)
    default-value
    result))

(comment (defn get-tweet-map-with-since [user-name my-oauth-creds since-id]
   "max-id comes in as a map with a key of :min"
   (statuses-user-timeline :oauth-creds my-oauth-creds :params {:screen-name user-name                                                    
                                                                :since_id since-id  
                                                                :count 10
                                                                :include_rts false
                                                                :tweet_mode "extended"})))

(defn insert-more-tweets [user-name my-oauth-creds batch-time]
  (def starting-tweet-id (:max (rdbms/get-max-tweet-id {:screen_name user-name})))
  (println "Here is starting-tweet-id: ", starting-tweet-id)
  (def tweet-map (get-tweet-map  my-oauth-creds 
                                 {:screen-name user-name                                                    
                                  :since_id starting-tweet-id  
                                  :count 10
                                  :include_rts false
                                  :tweet_mode "extended"}))
  (def map-body (:body tweet-map))

    (loop [the-map-body map-body]
    (println "in loop, here is count of the-map-body: ", (count the-map-body))
    ;; would "when" be better than "if" here?
    (when (> (count the-map-body) 0)
      (do
        (doseq [seq-body the-map-body]
         (do
           (print " Here is id: ", (:id seq-body))
           (rdbms/call-insert-tweet seq-body, batch-time)
           (def processed-string (process/create-processed-string seq-body user-name))
           (rdbms/call-insert-processed-tweet seq-body processed-string batch-time)))
        
        (println "Here is next-since-id: ", (rdbms/get-max-tweet-id {:screen_name user-name}))
        (def next-since-id (:max (rdbms/get-max-tweet-id {:screen_name user-name})))
        (def next-max-id (rdbms/get-min-tweet-id-for-batch {:screen_name user-name, :batch_time batch-time}))
        (recur (:body (get-tweet-map my-oauth-creds {:screen-name user-name                                                    
                                                     :since_id starting-tweet-id  
                                                     :count 10
                                                     :max_id (dec (:min next-max-id))
                                                     :include_rts false
                                                     :tweet_mode "extended"}))))))
  (println "Done inserting"))

