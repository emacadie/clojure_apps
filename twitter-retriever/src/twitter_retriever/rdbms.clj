(ns twitter-retriever.rdbms
  (:require [hugsql.core :as hugsql]
            [mount.core :refer [defstate]]
            [environ.core :refer [env]]
            [conman.core :as conman]
            [clojure.java.jdbc]
            [clj-time.format :as timef] 
            [clj-time.coerce :as timec]
            ))

(def pool-spec
  {
   :jdbc-url (env :database-url) ;; "postgres://localhost:5433/musicdb?user=music_lover&password=this-is-music"  
   :driver-class-name (env :db-driver )   ;; "org.postgresql.Driver"  
   })

(defstate ^:dynamic *db*
          :start (conman/connect! pool-spec)
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "twitter_retriever/sql/statements.sql")
(mount.core/start)

(defn month-string-to-num [month-str]
  (case month-str
    "Jan" "01"
    "Feb" "02"
    "Mar" "03"
    "Apr" "04"
    "May" "05"
    "Jun" "06"
    "Jul" "07"
    "Aug" "08"
    "Sep" "09"
    "Oct" "10"
    "Nov" "11"
    "Dec" "12"))

(defn create-time-from-map [time-from-map]
  (let [matches (re-matches #"(\w{3})\s(\w{3})\s(\d{2})\s(\d{2}\p{Punct}\d{2}\p{Punct}\d{2})\s\+\d{4}\s(\d{4})" 
                time-from-map)
        year        (nth matches 5)
        month       (month-string-to-num (nth matches 2))
        day         (nth matches 3)
        time-of-day (nth matches 4)
            ]
        (println year,"-", month, "-", day, " ", time-of-day  "seconds since midnight")
        (println "Here it is as a string: ", (str year,"-", month, "-", day, " ", time-of-day))
        (str year,"-", month, "-", day, " ", time-of-day)))

;; from https://stackoverflow.com/questions/9305541/clojure-jdbc-postgresql-i-am-trying-to-update-a-timestamp-value-in-postgresql-f/9305737#9305737
(defn get-time-from-map [time-from-map]
  (->> time-from-map
     (timef/parse (timef/formatter "EEE MMM dd HH:mm:ss Z yyyy"))
     timec/to-timestamp))

;; we get the map which is the value for the "body" key
(defn call-insert-user [user-map]
  (insert-user { :name (:name user-map) 
                :user_id_str (:id_str user-map) 
                :user_id (:id user-map) 
                :screen_name (:screen_name user-map) 
                :description (:description user-map)
                :location (:location user-map)
                :lang (:lang user-map) 
                :display_url  (get-in user-map [:entities :url :urls 0 :display_url])
                :expanded_url (get-in user-map [:entities :url :urls 0 :expanded_url])
                :friends_count (:friends_count user-map)
                :followers_count (:followers_count user-map)
                :has_extended_profile (:has_extended_profile user-map)
                :protected (:protected user-map)
                :statuses_count (:statuses_count user-map)
                :time_zone (:time_zone user-map)
                :utc_offset (:utc_offset user-map)
                :created_at (get-time-from-map (:created_at user-map))
                }))

(defn call-insert-tweet [tweet-map batch-time]
  (insert-tweet {:tweet_id_str (:id_str tweet-map)
                 :tweet_id (:id tweet-map)
                 :full_text (:full_text tweet-map)
                 :user_id  (get-in tweet-map [:user :id])
                 :user_id_str (get-in tweet-map [:user :id_str])
                 :in_reply_to_screen_name (:in_reply_to_screen_name tweet-map)
                 :in_reply_to_status_id (:in_reply_to_status_id tweet-map)
                 :in_reply_to_status_id_str (:in_reply_to_status_id_str tweet-map)
                 :in_reply_to_user_id (:in_reply_to_user_id tweet-map)
                 :in_reply_to_user_id_str (:in_reply_to_user_id_str tweet-map)
                 :created_at (get-time-from-map (:created_at tweet-map))
                 :batch_time batch-time
                 }))

