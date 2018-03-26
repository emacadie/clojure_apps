(ns twitter-retriever.process-test
  (:require [clojure.test :refer :all]
            [twitter-retriever.process :refer :all]
            [clojure.edn :as edn]))

(deftest test-make-links-from-hashtags
  (is (= 0 (compare (make-links-from-hashtags "Let's work on some #Clojure at CapitalFactory")
                    "Let's work on some <a href=\"https://twitter.com/hashtag/Clojure?src=hash\">#Clojure</a> at CapitalFactory")))
  (def hashtag-str "<a href=\"https://twitter.com/hashtag/")

  (is (= 0 (compare (make-links-from-hashtags "Let's work on some #Clojure at #CapitalFactory")
                    (str "Let's work on some "
                         hashtag-str
                         "Clojure?src=hash\">#Clojure</a> at "
                         hashtag-str
                         "CapitalFactory?src=hash\">#CapitalFactory</a>")))))

(deftest test-convert-links 
  ; (println "-------------")
  (def tweet-map-02 (edn/read-string (slurp "test/twitter_retriever/tweet-map-02.edn")))
  (is (= 0 (compare (str "SICP author Hal Abelson says coders should read code, ",
                         "just as people read novels ",
                         "<a href=\"http://www.gigamonkeys.com/code-quarterly/2011/hal-abelson/\">https://t.co/fjKWdYC6TV</a> ",
                         "What #Clojure projects have code devs should read?")
                    (convert-links tweet-map-02 (:full_text tweet-map-02))))))

(deftest test-convert-multiple-links
  (testing "converting multiple links"
    (def tweet-map-03 (edn/read-string (slurp "test/twitter_retriever/tweet-map-03.edn")))
    (def result-string (str "Test tweet (for my plugin) with a few links about #Clojure and #Groovy "
                          "<a href=\"https://clojure.org/reference/data_structures\">https://t.co/0loSXMSF13</a> "
                          "<a href=\"http://groovy-lang.org/metaprogramming.html\">https://t.co/Q7uJeABf8G</a>"))
    ; (println "Result in test-convert-multiple links: ", (convert-links tweet-map-03 (:full_text tweet-map-03)))
    (is (= 0 (compare result-string (convert-links tweet-map-03 (:full_text tweet-map-03)))))))

(deftest test-create-user-links
  (testing "testing the creation of user links"
    (def tweet-map-01 (edn/read-string (slurp "test/twitter_retriever/tweet-map-01.edn")))
    (def user-link-str "https://twitter.com/intent/user?user_id=")
    (def result-string (str "At <a href=\"", user-link-str, "2418042062",  "\">@AustinClojure</a> in ",
                            "<a href=\"",  user-link-str, "16887429", "\">@CapitalFactory</a> - w/my best friends ",
                            "<a href=\"",  user-link-str, "123456", "\">@mondotortilla</a> ",
                            "<a href=\"",  user-link-str, "1234567890", "\">@lisporleans</a> "
                            "<a href=\"",  user-link-str, "25836914", "\">@staylisp</a> "
                            "<a href=\"",  user-link-str, "9876543210", "\">@FarOutMan</a>"))
    ; (println "Here is result-string: ", result-string)
    ; (println "here is function call: ", (create-user-links tweet-map-01  (:full_text tweet-map-01)))
    (is (= 0 (compare result-string (create-user-links tweet-map-01 (:full_text tweet-map-01)))))))

(deftest test-create-in-reply-str
  (testing "testing creation of 'in reply to' str"
    ; (println "---")
    (def tweet-map-04 (edn/read-string (slurp "test/twitter_retriever/tweet-map-04.edn")))
    (def result-str (str "@lincoln You could send a message via LinkedIn", 
                         " in reply to <a href=\"http://twitter.com/lincoln/status/971093559494418432\">lincoln</a>"))
    ; (println "Here is result-str:    ", result-str)
    ; (println "Here is function call: ", (create-in-reply-str tweet-map-04 (:full_text tweet-map-04)))
    (is (= 0 (compare result-str (create-in-reply-str tweet-map-04 (:full_text tweet-map-04)))))))


(deftest test-append-timestamp
  (testing "testing append timestamp"
    (def tweet-map-04 (edn/read-string (slurp "test/twitter_retriever/tweet-map-04.edn")))
    (def user-name "KaiOpaka")
    (def result-str (str "@lincoln You could send a message via LinkedIn ", 
                         "<a href=\"https://twitter.com/",
                         user-name,
                         "/status/",
                         (:id_str tweet-map-04)
                         "\">",
                         "2018-03-08 01:47:41",
                         "</a>"))
    ; (println "Here is result-str:    ", result-str)
    ; (println "Here is function call: ", (append-timestamp tweet-map-04 (:full_text tweet-map-04) user-name))
    (is (= 0 (compare result-str (append-timestamp tweet-map-04 (:full_text tweet-map-04) user-name))))))

