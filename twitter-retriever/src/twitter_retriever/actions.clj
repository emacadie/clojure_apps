(ns twitter-retriever.actions
  (:require [clojure.java.io :as io])
  (:require [clojure.pprint :as pp])
  (:require [twitter-retriever.rdbms :as rdbms])
  (:use [twitter.oauth]
        [twitter.callbacks]
        [twitter.callbacks.handlers]
        [twitter.api.restful])
  (:import [twitter.callbacks.protocols SyncSingleCallback])
  (:gen-class))

(defn create-user [user-name my-oauth-creds batch-time]
  (def user-map (users-show :oauth-creds my-oauth-creds :params {:screen-name user-name}))
  (println "Here is user-map: ", user-map)
  (rdbms/call-insert-user (:body user-map))
  (def tweet-map (statuses-user-timeline :oauth-creds my-oauth-creds :params {:screen-name user-name 
                                                                               ; :since-id 642515818043994112
                                                                              :max_id 609813046207213568 ; 642515818043994112
                                                                              ; :count 100
                                                                              :include_rts false
                                                                              :tweet_mode "extended"}))

  (def map-body (:body tweet-map))
  (doseq [the-body map-body]
    (do
      (println "Here is id: ", (:id the-body), " Here is text: ", (:full_text the-body))
      (println "here is the-body: ", the-body)
      (rdbms/call-insert-tweet the-body, batch-time)))
  (println "Done inserting"))

(defn insert-more-tweets [user-name my-oauth-creds batch-time]
  (def starting-tweet-id (rdbms/get-max-tweet-id {:screen_name user-name}))
  (println "Here is starting-tweet-id: ", starting-tweet-id)
  (def tweet-map (statuses-user-timeline :oauth-creds my-oauth-creds :params {:screen_name user-name 
                                                                               :since_id (:max starting-tweet-id) 
                                                                              ; :max_id 609813046207213568 ; 642515818043994112
                                                                              ; :count 100
                                                                              :include_rts false
                                                                              :tweet_mode "extended"}))
  (def map-body (:body tweet-map))
  (doseq [the-body map-body]
    (do
      (println "Here is id: ", (:id the-body), " Here is text: ", (:full_text the-body))      
      (rdbms/call-insert-tweet the-body batch-time)))
  (println "Done inserting"))

