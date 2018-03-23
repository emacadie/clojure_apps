(ns twitter-retriever.process-test
  (:require [clojure.test :refer :all]
            [twitter-retriever.process :refer :all]
            [clojure.edn :as edn]))

(deftest a-test
  (testing "FIXME, I fail."
(is (= 1 1))))

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
  (def tweet-map-02 (edn/read-string (slurp "test/twitter_retriever/tweet-map-02.edn")))
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
    (def tweet-map-03 (edn/read-string (slurp "test/twitter_retriever/tweet-map-03.edn")))
    (def result-string (str "Test tweet (for my plugin) with a few links about #Clojure and #Groovy "
                          "<a href=\"https://clojure.org/reference/data_structures\">https://t.co/0loSXMSF13</a> "
                          "<a href=\"http://groovy-lang.org/metaprogramming.html\">https://t.co/Q7uJeABf8G</a>"))
    (println "Result in test-convert-multiple links: ", (convert-links tweet-map-03 (:full_text tweet-map-03)))
    (is (= 0 (compare result-string (convert-links tweet-map-03 (:full_text tweet-map-03))))))
)


