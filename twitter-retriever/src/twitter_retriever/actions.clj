(ns twitter-retriever.actions
  (:require [clojure.java.io :as io]
            [clojure.pprint  :as pp]
            [twitter.api.restful :as restful]
            [twitter.callbacks]
            [twitter-retriever.rdbms   :as rdbms]
            [twitter-retriever.process :as process])

  ; (:require [conman.core :refer [with-transaction]])
  (:import [twitter.callbacks.protocols SyncSingleCallback])
  (:gen-class))

(defn get-tweet-map-body [my-oauth-creds params-map]
  "max-id comes in as a map with a key of :min"
  ;; when you get tweets, you get a map with nested maps and vectors, etc
  ;; you have a section called "header" with metadata, and a section called "body" which is a vector of maps, 
  ;; with each tweet being a map in the vector
  (:body (restful/statuses-user-timeline :oauth-creds my-oauth-creds :params params-map )))

(defn create-user [user-name my-oauth-creds batch-time]
  (let [param-map {:screen-name user-name, :count 200, :include_rts false, :tweet_mode "extended"}]
      (def user-map (restful/users-show :oauth-creds my-oauth-creds :params {:screen-name user-name}))
      (println "Here is user-map: ", user-map)
      (rdbms/call-insert-user (:body user-map))
      (def max-id (get-in user-map [ :body :status :id ]))
  
      (println "Here is max-id: ", max-id)

      (loop [the-map-body (get-tweet-map-body my-oauth-creds (conj param-map {:max_id max-id}))]
        (println "in loop, here is count of the-map-body: ", (count the-map-body))
        ;; would "when" be better here?
        (if (> (count the-map-body) 0)
          (do
            (doseq [seq-body the-map-body]
              (do
                (rdbms/call-insert-tweet seq-body, batch-time)
                (def processed-string (process/create-processed-string seq-body user-name))
                (rdbms/call-insert-processed-tweet seq-body processed-string batch-time)))
            
            (println "Here is next-max-id: ", (rdbms/get-min-tweet-id {:screen_name user-name}))
            (def next-max-id (:min (rdbms/get-min-tweet-id {:screen_name user-name})))
            (recur (get-tweet-map-body my-oauth-creds (conj param-map {:max_id (dec next-max-id)}))))))))

(defn insert-more-tweets [user-name my-oauth-creds batch-time]
  (let [param-map {:screen-name user-name, :count 200, :include_rts false, :tweet_mode "extended"}]
      (def starting-tweet-id (:max (rdbms/get-max-tweet-id {:screen_name user-name})))
      (println "Here is starting-tweet-id: ", starting-tweet-id)
      (loop [the-map-body (get-tweet-map-body my-oauth-creds (conj param-map {:since_id starting-tweet-id}))]
        (println "in loop, here is count of the-map-body: ", (count the-map-body))
        ;; would "when" be better than "if" here?
        (when (> (count the-map-body) 0)
          (do
            (println "Here is min id from the group: ", (apply min (map :id the-map-body)))
            (doseq [seq-body the-map-body]
              (do
                (rdbms/call-insert-tweet seq-body, batch-time)
                (def processed-string (process/create-processed-string seq-body user-name))
                (rdbms/call-insert-processed-tweet seq-body processed-string batch-time)))
                 
            (recur (get-tweet-map-body my-oauth-creds (conj param-map {:since_id starting-tweet-id, :max_id (dec (apply min (map :id the-map-body)))})))))))
  (println "Done inserting")) 
;; line 83

