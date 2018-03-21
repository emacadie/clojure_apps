(ns twitter-retriever.process)

;; 
(defn make-links-from-hashtags [tweet-string]
  (clojure.string/replace tweet-string #"((\#)(\w+))" "<a href=\"https://twitter.com/hashtag/$3?src=hash\">$1</a>"))

(defn convert-links [tweet-map tweet-string]
  (println "Here is tweet-map: " + tweet-map)
  (def urls (get-in tweet-map [:entities :urls]))
  (println "--\nHere is urls: ", urls)
  (println "In convert-links, here are keys to tweet-map: ", (keys tweet-map))
  (loop [url-vec (get-in tweet-map [:entities :urls])
         t-string tweet-string
         ;; result []
         ]
    (println "Here is count of url-vec: ", (count url-vec))
    (if (> (count url-vec) 0)
      (do
        (def url-map (nth url-vec 0))
        (def new-str (str "<a href=\"" (:expanded_url url-map) "\">" (:url url-map) "</a>"))
        (println "Here is url-map: ",  url-map)
        (println "Here is new-str: ", new-str)
        (recur (pop (reverse url-vec))
               (clojure.string/replace t-string (:url url-map) new-str)))
      t-string)
)
) ;; defn

(comment 
(loop [x 5
       result []]
  (println "Here is x: " x)
  (if (> x 0)
    (do
      (println "about to recur with x equal to " x)
      (recur (dec x) (conj result (+ 2 x))))
result))
)


(comment

:entities
 {:hashtags [{:text "Clojure", :indices [110 118]}],
  :symbols [],
  :user_mentions [],
  :urls
  [{:url "https://t.co/fjKWdYC6TV",
    :expanded_url
    "http://www.gigamonkeys.com/code-quarterly/2011/hal-abelson/",
    :display_url "gigamonkeys.com/code-quarterly…",
    :indices [81 104]}]}
  
"Test tweet (for my plugin) with a few links about #Clojure and #Groovy https://t.co/0loSXMSF13 https://t.co/Q7uJeABf8G"
)

