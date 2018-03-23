(ns twitter-retriever.process)

(defn make-links-from-hashtags [tweet-string]
  (clojure.string/replace tweet-string #"((\#)(\w+))++" "<a href=\"https://twitter.com/hashtag/$3?src=hash\">$1</a>"))

(defn convert-links [tweet-map tweet-string]
  (loop [url-vec (get-in tweet-map [:entities :urls])
         t-string tweet-string]
    (if (> (count url-vec) 0)
      (do
        (def url-map (peek url-vec))
        (def new-str (str "<a href=\"" (:expanded_url url-map) "\">" (:url url-map) "</a>"))
        (recur (pop url-vec)
               (clojure.string/replace t-string (:url url-map) new-str)))
      t-string))) ;; defn

(defn create-user-links [tweet-map tweet-string]
  (loop [user-vec (get-in tweet-map [:entities :user_mentions])
         t-string tweet-string]
    (if (> (count user-vec) 0)
      (do
        (def user-map (peek user-vec))
        (def new-str (str "<a href=\"https://twitter.com/intent/user?user_id=" (:id_str user-map) "\">@" (:screen_name user-map) "</a>"))
        (recur (pop user-vec)
               (clojure.string/replace t-string (str "@", (:screen_name user-map)) new-str)))
      t-string))) ;; defn

(defn create-in-reply-str [tweet-map tweet-string]
)


