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
                    (convert-links (:full_text tweet-map-02) tweet-map-02 )))))

(deftest test-convert-multiple-links
  (testing "converting multiple links"
    (def tweet-map-03 (edn/read-string (slurp "test/twitter_retriever/tweet-map-03.edn")))
    (def result-string (str "Test tweet (for my plugin) with a few links about #Clojure and #Groovy "
                          "<a href=\"https://clojure.org/reference/data_structures\">https://t.co/0loSXMSF13</a> "
                          "<a href=\"http://groovy-lang.org/metaprogramming.html\">https://t.co/Q7uJeABf8G</a>"))
    ; (println "Result in test-convert-multiple links: ", (convert-links tweet-map-03 (:full_text tweet-map-03)))
    (is (= 0 (compare result-string (convert-links (:full_text tweet-map-03) tweet-map-03))))))

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
    ; (println "here is function call: ", (create-user-links (:full_text tweet-map-01) tweet-map-01))
    (is (= 0 (compare result-string (create-user-links (:full_text tweet-map-01) tweet-map-01))))))

(deftest test-create-in-reply-str
  (testing "testing creation of 'in reply to' str"
    ; (println "---")
    (def tweet-map-04 (edn/read-string (slurp "test/twitter_retriever/tweet-map-04.edn")))
    (def result-str (str "@lincoln You could send a message via LinkedIn", 
                         " <a href=\"http://twitter.com/lincoln/status/971093559494418432\">in reply to lincoln</a>"))
    ; (println "Here is result-str:    ", result-str)
    ; (println "Here is function call: ", (create-in-reply-str (:full_text tweet-map-04) tweet-map-04))
    (is (= 0 (compare result-str (create-in-reply-str (:full_text tweet-map-04) tweet-map-04))))))

(deftest test-create-in-reply-with-no-reply
  (testing "Testing a tweet with no reply"
    (def tweet-map-03 (edn/read-string (slurp "test/twitter_retriever/tweet-map-03.edn")))
    (def tweet-string (:full_text tweet-map-03))
    ; (println "Here is tweet-string: ", tweet-string)
    ; (println "here is function:     ", (create-in-reply-str tweet-string tweet-map-03))
    (is (= 0 (compare tweet-string (create-in-reply-str tweet-string tweet-map-03))))))

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
    ; (println "Here is function call: ", (append-timestamp (:full_text tweet-map-04) tweet-map-04 user-name))
    (is (= 0 (compare result-str (append-timestamp (:full_text tweet-map-04) tweet-map-04 user-name))))))

(deftest test-wrap-in-li-tags
  (testing "Wrap it in li tags"
    (def tweet-map-04 (edn/read-string (slurp "test/twitter_retriever/tweet-map-04.edn")))
    (def result-str (str "<li>@lincoln You could send a message via LinkedIn</li>"))
    (is (= 0 (compare result-str (wrap-in-li-tags (:full_text tweet-map-04)))))))

(deftest test-map-01-threading
  (testing "testing map 01 with and without threading macro"
    (def tweet-map-01 (edn/read-string (slurp "test/twitter_retriever/tweet-map-01.edn")))
    (def tweet-string (:full_text tweet-map-01))
    (def user-name "Kai0p4ka")
    (def non-threading-string (wrap-in-li-tags (append-timestamp (create-in-reply-str (create-user-links (convert-links (make-links-from-hashtags tweet-string) tweet-map-01) tweet-map-01) tweet-map-01) tweet-map-01 user-name)))
    ; (println "here is non-threading-string: ", non-threading-string)
    (def threading-string (->
                           (make-links-from-hashtags tweet-string) 
                           (convert-links       tweet-map-01) 
                           (create-user-links   tweet-map-01) 
                           (create-in-reply-str tweet-map-01) 
                           (append-timestamp    tweet-map-01 user-name)
                           (wrap-in-li-tags)))
    ; (println "Here is the threading-string: ", threading-string)
    (println "Here is the expanded macro: ", (macroexpand '(->
                           (make-links-from-hashtags tweet-string) 
                           (convert-links       tweet-map-01) 
                           (create-user-links   tweet-map-01) 
                           (create-in-reply-str tweet-map-01) 
                           (append-timestamp    tweet-map-01 user-name)
                           (wrap-in-li-tags))))

    (is (= 0 (compare threading-string non-threading-string)))))

(deftest test-map-02-threading
  (testing "testing map 02 with and without threading macro"
    (def tweet-map-02 (edn/read-string (slurp "test/twitter_retriever/tweet-map-02.edn")))
    (def tweet-string (:full_text tweet-map-02))
    (def user-name "Kai0p4ka")
    (def non-threading-string (wrap-in-li-tags (append-timestamp (create-in-reply-str (create-user-links (convert-links (make-links-from-hashtags tweet-string) tweet-map-02) tweet-map-02) tweet-map-02) tweet-map-02 user-name)))
    ; (println "here is non-threading-string: ", non-threading-string)
    (def threading-string (->
                           (make-links-from-hashtags tweet-string) 
                           (convert-links       tweet-map-02) 
                           (create-user-links   tweet-map-02) 
                           (create-in-reply-str tweet-map-02) 
                           (append-timestamp    tweet-map-02 user-name)
                           (wrap-in-li-tags)))
    ; (println "Here is the threading-string: ", threading-string)
    (println "Here is the expanded macro: ", (macroexpand '(->
                           (make-links-from-hashtags tweet-string) 
                           (convert-links       tweet-map-02) 
                           (create-user-links   tweet-map-02) 
                           (create-in-reply-str tweet-map-02) 
                           (append-timestamp    tweet-map-02 user-name)
                           (wrap-in-li-tags))))

    (is (= 0 (compare threading-string non-threading-string)))))

(deftest test-map-03-threading
  (testing "testing map 03 with and without threading macro"
    (def tweet-map-03 (edn/read-string (slurp "test/twitter_retriever/tweet-map-03.edn")))
    (def tweet-string (:full_text tweet-map-03))
    (def user-name "Kai0p4ka")
    (def non-threading-string (wrap-in-li-tags (append-timestamp (create-in-reply-str (create-user-links (convert-links (make-links-from-hashtags tweet-string) tweet-map-03) tweet-map-03) tweet-map-03) tweet-map-03 user-name)))
    ; (println "here is non-threading-string: ", non-threading-string)
    (def threading-string (->
                           (make-links-from-hashtags tweet-string) 
                           (convert-links       tweet-map-03) 
                           (create-user-links   tweet-map-03) 
                           (create-in-reply-str tweet-map-03) 
                           (append-timestamp    tweet-map-03 user-name)
                           (wrap-in-li-tags)))
    ; (println "Here is the threading-string: ", threading-string)
    (println "Here is the expanded macro: ", (macroexpand '(->
                           (make-links-from-hashtags tweet-string) 
                           (convert-links       tweet-map-03) 
                           (create-user-links   tweet-map-03) 
                           (create-in-reply-str tweet-map-03) 
                           (append-timestamp    tweet-map-03 user-name)
                           (wrap-in-li-tags))))

    (is (= 0 (compare threading-string non-threading-string)))))

(deftest test-map-04-threading
  (testing "testing map 04 with and without threading macro"
    (def tweet-map-04 (edn/read-string (slurp "test/twitter_retriever/tweet-map-04.edn")))
    (def tweet-string (:full_text tweet-map-04))
    (def user-name "Kai0p4ka")
    (def non-threading-string (wrap-in-li-tags (append-timestamp (create-in-reply-str (create-user-links (convert-links (make-links-from-hashtags tweet-string) tweet-map-04) tweet-map-04) tweet-map-04) tweet-map-04 user-name)))
    ; (println "here is non-threading-string: ", non-threading-string)
    (def threading-string (->
                           (make-links-from-hashtags tweet-string) 
                           (convert-links       tweet-map-04) 
                           (create-user-links   tweet-map-04) 
                           (create-in-reply-str tweet-map-04) 
                           (append-timestamp    tweet-map-04 user-name)
                           (wrap-in-li-tags)))
    ; (println "Here is the threading-string: ", threading-string)
    (println "Here is the expanded macro: ", (macroexpand '(->
                                                            (make-links-from-hashtags tweet-string) 
                                                            (convert-links tweet-map-04) 
                                                            (create-user-links tweet-map-04) 
                                                            (create-in-reply-str tweet-map-04) 
                                                            (append-timestamp tweet-map-04 user-name)
                                                            (wrap-in-li-tags))))

    (is (= 0 (compare threading-string non-threading-string)))))

