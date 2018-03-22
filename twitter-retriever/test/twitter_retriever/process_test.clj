(ns twitter-retriever.process-test
  (:require [clojure.test :refer :all]
            [twitter-retriever.process :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
(is (= 1 1))))

(def tweet-map-01 {:in_reply_to_screen_name nil,
 :is_quote_status false,
 :coordinates nil,
 :in_reply_to_status_id_str nil,
 :place nil,
 :geo nil,
 :in_reply_to_status_id nil,
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
  :urls []},
 :source
 "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
 :lang "en",
 :in_reply_to_user_id_str nil,
 :full_text
 "At @AustinClojure in @CapitalFactory - w/my best friends @mondotortilla @lisporleans @staylisp @FarOutMan",
 :id 950535791188545537,
 :contributors nil,
 :display_text_range [0 105],
 :truncated false,
 :retweeted false,
 :in_reply_to_user_id nil,
 :id_str "950535791188545537",
 :favorited false,
 :user {:id 111373627, :id_str "111373627"},
 :retweet_count 1,
 :favorite_count 3,
 :created_at "Tue Jan 09 01:12:47 +0000 2018"})

(def tweet-map-02 {:in_reply_to_screen_name nil,
 :is_quote_status false,
 :coordinates nil,
 :in_reply_to_status_id_str nil,
 :place nil,
 :possibly_sensitive false,
 :geo nil,
 :in_reply_to_status_id nil,
 :entities
 {:hashtags [{:text "Clojure", :indices [110 118]}],
  :symbols [],
  :user_mentions [],
  :urls
  [{:url "https://t.co/fjKWdYC6TV",
    :expanded_url
    "http://www.gigamonkeys.com/code-quarterly/2011/hal-abelson/",
    :display_url "gigamonkeys.com/code-quarterly…",
    :indices [81 104]}]},
 :source
 "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
 :lang "en",
 :in_reply_to_user_id_str nil,
 :full_text
 "SICP author Hal Abelson says coders should read code, just as people read novels https://t.co/fjKWdYC6TV What #Clojure projects have code devs should read?",
 :id 966910875159244800,
 :contributors nil,
 :display_text_range [0 155],
 :truncated false,
 :retweeted false,
 :in_reply_to_user_id nil,
 :id_str "966910875159244800",
 :favorited false,
 :user {:id 111373627, :id_str "111373627"},
 :retweet_count 1,
 :favorite_count 0,
 :created_at "Fri Feb 23 05:41:31 +0000 2018"}
)

(def tweet-map-03 {:in_reply_to_screen_name nil,
 :is_quote_status false,
 :coordinates nil,
 :in_reply_to_status_id_str nil,
 :place nil,
 :possibly_sensitive false,
 :geo nil,
 :in_reply_to_status_id nil,
 :entities
 {:hashtags
  [{:text "Clojure", :indices [50 58]}
   {:text "Groovy", :indices [63 70]}],
  :symbols [],
  :user_mentions [],
  :urls
  [{:url "https://t.co/0loSXMSF13",
    :expanded_url "https://clojure.org/reference/data_structures",
    :display_url "clojure.org/reference/data…",
    :indices [71 94]}
   {:url "https://t.co/Q7uJeABf8G",
    :expanded_url "http://groovy-lang.org/metaprogramming.html",
    :display_url "groovy-lang.org/metaprogrammin…",
    :indices [95 118]}]},
 :source
 "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
 :lang "en",
 :in_reply_to_user_id_str nil,
 :full_text
 "Test tweet (for my plugin) with a few links about #Clojure and #Groovy https://t.co/0loSXMSF13 https://t.co/Q7uJeABf8G",
 :id 975605770913026049,
 :contributors nil,
 :display_text_range [0 118],
 :truncated false,
 :retweeted false,
 :in_reply_to_user_id nil,
 :id_str "975605770913026049",
 :favorited false,
 :user {:id 111373627, :id_str "111373627"},
 :retweet_count 0,
 :favorite_count 0,
 :created_at "Mon Mar 19 05:31:56 +0000 2018"}

)

(comment (defn make-links-from-hashtags [tweet-string]
  (clojure.string/replace tweet-string #"(\#\w+)" "<a href=\"https://twitter.com/hashtag/$1?src=hash\">$1</a>")
  ))

(deftest test-make-links-from-hashtags
  (is (= 0 (compare (make-links-from-hashtags "Let's work on some #Clojure at CapitalFactory")
                    "Let's work on some <a href=\"https://twitter.com/hashtag/Clojure?src=hash\">#Clojure</a> at CapitalFactory")))
  (def hashtag-str "<a href=\"https://twitter.com/hashtag/")


  (comment (println "Result of calling (make-links-from-hashtags \"Let's work on some #Clojure at #CapitalFactory \":",
            (make-links-from-hashtags "Let's work on some #Clojure at #CapitalFactory")))
  (comment (println "Expected outcome: ", (str "Let's work on some "
                                       hashtag-str
                                       "Clojure?src=hash\">#Clojure</a> at "
                                       hashtag-str
                                       "CapitalFactory?src=hash\">#CapitalFactory</a>")))

  (is (= 0 (compare (make-links-from-hashtags "Let's work on some #Clojure at #CapitalFactory")
                    (str "Let's work on some "
                         hashtag-str
                         "Clojure?src=hash\">#Clojure</a> at "
                         hashtag-str
                         "CapitalFactory?src=hash\">#CapitalFactory</a>")
                    )))
)

(comment (defn convert-links [tweet-map]))



(deftest test-convert-links 
  (println "\n\n\n\n\n--------------")
  ;; (println "keys for tweet-map-02: ", (keys tweet-map-02))
  ;; (println "result of (convert-links tweet-map-02 (:full_text tweet-map-02)):", (convert-links tweet-map-02 (:full_text tweet-map-02)))
  (is (= 0 (compare "SICP author Hal Abelson says coders should read code, just as people read novels <a href=\"http://www.gigamonkeys.com/code-quarterly/2011/hal-abelson/\">https://t.co/fjKWdYC6TV</a> What #Clojure projects have code devs should read?"
                    (convert-links tweet-map-02 (:full_text tweet-map-02)))))
)

(deftest test-convert-multiple-links

(comment
"Test tweet (for my plugin) with a few links about #Clojure and #Groovy <a href=\"https://clojure.org/reference/data_structures\">https://t.co/0loSXMSF13</a> <a href=\"http://groovy-lang.org/metaprogramming.html\">https://t.co/Q7uJeABf8G</a>",
  [{:url "https://t.co/0loSXMSF13",
    :expanded_url "https://clojure.org/reference/data_structures",
    :display_url "clojure.org/reference/data…",
    :indices [71 94]}
   {:url "https://t.co/Q7uJeABf8G",
    :expanded_url "http://groovy-lang.org/metaprogramming.html",
    :display_url "groovy-lang.org/metaprogrammin…",
    :indices [95 118]}]
)
  (testing "converting multiple links"
    (def result-string (str "Test tweet (for my plugin) with a few links about #Clojure and #Groovy "
                          "<a href=\"https://clojure.org/reference/data_structures\">https://t.co/0loSXMSF13</a> "
                          "<a href=\"http://groovy-lang.org/metaprogramming.html\">https://t.co/Q7uJeABf8G</a>"))
    (println "Result in test-convert-multiple links: ", (convert-links tweet-map-03 (:full_text tweet-map-03)))
    (is (= 0 (compare result-string (convert-links tweet-map-03 (:full_text tweet-map-03))))))
)


