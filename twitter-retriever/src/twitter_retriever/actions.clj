(ns twitter-retriever.actions
  (:require [twitter.api.restful :as restful]
            [twitter.callbacks]
            [twitter-retriever.process :as process]
            [twitter-retriever.rdbms   :as rdbms])
  (:import [twitter.callbacks.protocols SyncSingleCallback]))

(defn get-tweet-map-body [my-oauth-creds params-map]
  "max-id comes in as a map with a key of :min"
  ;; when you get tweets, you get a map with nested maps and vectors, etc
  ;; you have a section called "header" with metadata, and a section called "body" which is a vector of maps, 
  ;; with each tweet being a map in the vector
  (:body (restful/statuses-user-timeline :oauth-creds my-oauth-creds :params params-map )))

(defn create-user [user-name my-oauth-creds batch-time]
  (let [param-map {:screen-name user-name, :count 200, :include_rts false, :tweet_mode "extended"}
        user-map (restful/users-show :oauth-creds my-oauth-creds :params {:screen-name user-name})
        max-id (get-in user-map [ :body :status :id ])]
      
      (rdbms/call-insert-user (:body user-map))
      
      (loop [the-map-body (get-tweet-map-body my-oauth-creds 
                                              (conj param-map {:max_id max-id}))]
        (when (> (count the-map-body) 0)
          (do
            (doseq [seq-body the-map-body]
              (do
                (rdbms/call-insert-tweet seq-body batch-time)
                (rdbms/call-insert-processed-tweet seq-body 
                                                   (process/create-processed-string seq-body 
                                                                                    user-name) 
                                                   batch-time)))
            
            (def next-max-id (:min (rdbms/get-min-tweet-id {:screen_name user-name})))
            (recur (get-tweet-map-body my-oauth-creds 
                                       (conj param-map {:max_id (dec next-max-id)}))))))))

(defn insert-more-tweets [user-name my-oauth-creds batch-time]
  (let [param-map {:screen-name user-name, :count 200, :include_rts false, :tweet_mode "extended"}
        starting-tweet-id (:max (rdbms/get-max-tweet-id {:screen_name user-name}))]
      (loop [the-map-body (get-tweet-map-body my-oauth-creds 
                                              (conj param-map {:since_id starting-tweet-id}))]
        (when (> (count the-map-body) 0)
          (do
            (doseq [seq-body the-map-body]
              (do
                (rdbms/call-insert-tweet seq-body batch-time)
                (rdbms/call-insert-processed-tweet seq-body 
                                                   (process/create-processed-string seq-body 
                                                                                    user-name) 
                                                   batch-time)))                 
            (recur (get-tweet-map-body my-oauth-creds (conj param-map 
                                                            {:since_id starting-tweet-id 
                                                             :max_id (dec (apply min (map :id the-map-body)))})))))))) 
;; line 83, 59, 52

