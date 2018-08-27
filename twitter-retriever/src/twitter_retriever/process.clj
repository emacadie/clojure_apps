(ns twitter-retriever.process
  (:require [clj-time.format :as timef]
            [clj-time.local  :as timel]
            [twitter-retriever.rdbms :as rdbms]))

(defn make-links-from-hashtags [tweet-string]
  (clojure.string/replace tweet-string #"((\#)(\w+))++" "<a href=\"https://twitter.com/hashtag/$3?src=hash\">$1</a>"))

(defn convert-links [tweet-string tweet-map]
  (loop [url-vec (get-in tweet-map [:entities :urls])
         t-string tweet-string]
    (if (> (count url-vec) 0)
      (do
        (def url-map (peek url-vec))
        (def new-str (str "<a href=\"" (:expanded_url url-map) "\">" (:url url-map) "</a>"))
        (recur (pop url-vec)
               (clojure.string/replace t-string (:url url-map) new-str)))
      t-string))) 

(defn convert-media-links [tweet-string tweet-map]
  (loop [url-vec (get-in tweet-map [:entities :media])
         t-string tweet-string]
    (if (> (count url-vec) 0)
      (do
        (def url-map (peek url-vec))
        (def new-str (str "<a href=\"" (:media_url_https url-map) "\">" (:url url-map) "</a>"))
        (recur (pop url-vec)
               (clojure.string/replace t-string (:url url-map) new-str)))
      t-string)))

(defn create-user-links [tweet-string tweet-map]
  (loop [user-vec (get-in tweet-map [:entities :user_mentions])
         t-string tweet-string]
    (if (> (count user-vec) 0)
      (do
        (def user-map (peek user-vec))
        (def new-str (str "<a href=\"https://twitter.com/intent/user?user_id=" (:id_str user-map) "\">@" (:screen_name user-map) "</a>"))
        (recur (pop user-vec)
               (clojure.string/replace t-string (str "@", (:screen_name user-map)) new-str)))
      t-string))) 

(defn create-in-reply-str [tweet-string tweet-map]
  (def in-reply-to (:in_reply_to_screen_name tweet-map))
  (if (= 0 (count in-reply-to))
    (str tweet-string)
    (do
      (str tweet-string, 
       " <a href=\"http://twitter.com/", 
       in-reply-to
       "/status/" 
        (:in_reply_to_status_id_str tweet-map ), 
       "\">", 
       "in reply to ", 
       in-reply-to  
       "</a>"))))

(defn get-time-from-map [time-from-map]
  (->> time-from-map 
       (timef/parse (timef/formatter "EEE MMM dd HH:mm:ss Z yyyy"))
       (timef/unparse (timef/formatters :mysql))))

(defn append-timestamp [tweet-string tweet-map user-name]
  (str tweet-string, 
       " <a href=\"https://twitter.com/", 
       user-name, 
       "/status/", 
       (:id_str tweet-map), 
       "\">", 
       (get-time-from-map (:created_at tweet-map)), 
       "</a>" ))

(defn wrap-in-li-tags [tweet-string]
  (str "<li>", tweet-string, "</li>"))

(defn create-processed-string [tweet-map user-name]
  (->
   (make-links-from-hashtags (:full_text tweet-map)) 
   (convert-links       tweet-map)
   (convert-media-links tweet-map)
   (create-user-links   tweet-map) 
   (create-in-reply-str tweet-map) 
   (append-timestamp    tweet-map user-name)
   (wrap-in-li-tags)))

(defn create-file-name [user-name]
  (def custom-formatter (timef/formatter "yyyy-MM-dd_hh-mm-ss"))
  (def time-str (timef/unparse custom-formatter (timel/local-now)))
  (str time-str, "_", user-name, ".html"))

(defn write-processed-file [tweet-vec file-name]
  (println "Here is size of tweet-vec: ", (count tweet-vec))
  (with-open [w (clojure.java.io/writer file-name)]
    (.write w "<ul>\n")
  (doseq [line tweet-vec]
    (.write w (:final_html_text line))
    (.newLine w))
    (.write w "</ul>\n")))

(defn create-processed-file [user-name batch-time]
  (let [tweet-vec (rdbms/get-processed-tweets-by-user {:user_name user-name
                                                      :batch-time batch-time})]
    (when (not-empty tweet-vec)
      (write-processed-file tweet-vec (create-file-name user-name)))))




