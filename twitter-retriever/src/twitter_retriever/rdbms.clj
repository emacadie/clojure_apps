(ns twitter-retriever.rdbms
  (:require ; [hugsql.core  :as hugsql]
            [mount.core   :as mount]
            [environ.core :as environ]
            [conman.core  :as conman]
            [clj-time.format :as timef] 
            [clj-time.coerce :as timec]))

(def pool-spec
  {
   :jdbc-url (environ/env :database-url) 
   :driver-class-name (environ/env :db-driver ) 
   })

(mount/defstate ^:dynamic *db*
          :start (conman/connect! pool-spec)
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "twitter_retriever/sql/statements.sql")

;; from https://stackoverflow.com/questions/9305541/clojure-jdbc-postgresql-i-am-trying-to-update-a-timestamp-value-in-postgresql-f/9305737#9305737
(defn get-time-from-map [time-from-map]
  (->> time-from-map
     (timef/parse (timef/formatter "EEE MMM dd HH:mm:ss Z yyyy"))
     timec/to-timestamp))

;; we get the map which is the value for the "body" key
(defn call-insert-user [user-map]
  (insert-user { :name       (:name user-map) 
                :user_id_str (:id_str user-map) 
                :user_id     (:id user-map) 
                :screen_name (:screen_name user-map) 
                :description (:description user-map)
                :location    (:location user-map)
                :lang        (:lang user-map) 
                :display_url     (get-in user-map [:entities :url :urls 0 :display_url])
                :expanded_url    (get-in user-map [:entities :url :urls 0 :expanded_url])
                :friends_count   (:friends_count user-map)
                :followers_count (:followers_count user-map)
                :has_extended_profile (:has_extended_profile user-map)
                :protected       (:protected user-map)
                :statuses_count  (:statuses_count user-map)
                :time_zone  (:time_zone user-map)
                :utc_offset (:utc_offset user-map)
                :created_at (get-time-from-map (:created_at user-map))
                }))

(defn call-insert-tweet [tweet-map batch-time]
  (insert-tweet {:tweet_id_str (:id_str tweet-map)
                 :tweet_id     (:id tweet-map)
                 :full_text    (subs (:full_text tweet-map) 0 (min 2056 (.length (:full_text tweet-map))))
                 :user_id      (get-in tweet-map [:user :id])
                 :user_id_str  (get-in tweet-map [:user :id_str])
                 :in_reply_to_screen_name   (:in_reply_to_screen_name tweet-map)
                 :in_reply_to_status_id     (:in_reply_to_status_id tweet-map)
                 :in_reply_to_status_id_str (:in_reply_to_status_id_str tweet-map)
                 :in_reply_to_user_id       (:in_reply_to_user_id tweet-map)
                 :in_reply_to_user_id_str   (:in_reply_to_user_id_str tweet-map)
                 :created_at (get-time-from-map (:created_at tweet-map))
                 :batch_time batch-time
                 }))

(defn call-insert-processed-tweet [tweet-map processed-string batch-time]
  (insert-processed-tweet {:tweet_id_str (:id_str tweet-map), 
                           :tweet_id     (:id tweet-map), 
                           :user_id      (get-in tweet-map [:user :id]), 
                           :user_id_str  (get-in tweet-map [:user :id_str]), 
                           :final_html_text processed-string, 
                           :created_at   (get-time-from-map (:created_at tweet-map)), 
                           :batch_time batch-time
                           }))


