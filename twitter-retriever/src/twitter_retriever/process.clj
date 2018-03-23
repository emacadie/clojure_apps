(ns twitter-retriever.process)

;; 
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
      t-string)
)
) ;; defn

(defn create-user-links [tweet-map tweet-string]
  (loop [user-vec (get-in tweet-map [:entities :user_mentions])
         t-string tweet-string]
    (if (> (count user-vec) 0)
      (do
        (def user-map (peek user-vec))
        ; (println "here is user-map: ", user-map)
        ; (println "here is t-string: ", t-string)
        (def new-str (str "<a href=\"https://twitter.com/intent/user?user_id=" (:id_str user-map) "\">@" (:screen_name user-map) "</a>"))
        (recur (pop user-vec)
               (clojure.string/replace t-string (str "@", (:screen_name user-map)) new-str)))
      t-string)
)
) ;; defn
(comment
{:screen_name "AustinClojure",
    :name "AustinClojure",
    :id 2418042062,
    :id_str "2418042062",
    :indices [3 17]}
https://twitter.com/intent/user?user_id=3077765161
)


(comment 
(loop [x 5
       result []]
  (println "Here is x: " x)
  (if (> x 0)
    (do
      (println "about to recur with x equal to " x)
      (recur (dec x) (conj result (+ 2 x))))
result))

:entities
 {:hashtags [],
  :symbols [],
  :user_mentions
  [{:screen_name "AustinClojure",
    :name "AustinClojure",
    :id 2418042062,
    :id_str "2418042062",
    :indices [3 17]}
   {:screen_name "CapitalFactory",
    :name "Capital Factory",
    :id 16887429,
    :id_str "16887429",
    :indices [21 36]}
   {:screen_name "mondotortilla",
    :name "David Betrayus",
    :id 123456,
    :id_str "123456",
    :indices [57 71]}
   {:screen_name "lisporleans",
    :name "Nora Nora",
    :id 1234567890,
    :id_str "1234567890",
    :indices [72 84]}
   {:screen_name "staylisp",
    :name "Lisp Is Best #CLOS-the-Ultimate-OO",
    :id 25836914,
    :id_str "25836914",
    :indices [85 94]}
   {:screen_name "FarOutMan",
    :name "Far Outman",
    :id 9876543210,
    :id_str "9876543210",
    :indices [95 105]}],
  :urls []}

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

