(ns twitter-retriever.process)

;; 
(defn make-links-from-hashtags [tweet-string]
  (clojure.string/replace tweet-string #"(\#\w+)" "<a href=\"https://twitter.com/hashtag/$1?src=hash\">$1</a>"))

(defn convert-links [tweet-map])

