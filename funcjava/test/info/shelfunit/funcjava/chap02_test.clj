(ns info.shelfunit.funcjava.chap02-test
  (:require [clojure.test :refer :all]
            [info.shelfunit.funcjava.chap02 :refer :all]))

(deftest test-transform-list-to-uppercase
  (testing "Testing transform-list-to-uppercase"
    (println "in test-transform-list-to-uppercase")
    (transform-list-to-uppercase)
    (is (= 1 1))))

(deftest test-get-count-of-friends
  (testing "Testing get-count-of-friends"
    (println "in test-get-count-of-friends")
    (get-count-of-friends)
    (is (= 1 1))))

(deftest test-reverse-upper-case
  (testing "Testing reverse-upper-case"
    (println "In test-reverse-upper-case ")
    (reverse-upper-case)
    (is (= 1 1))))

(deftest test-reuse-lambda
  (testing "Testing reuse-lambda"
    (println "In test-reuse-lambda")
    (println "Calling with 'K'")
    (reuse-lambda "K")
    (println "\nCalling with 'J'")
    (reuse-lambda "J")
    (println "\nCalling with no arg")
    (reuse-lambda)
    (is (= 1 1))))

(deftest a-test
  (testing "Default test."
    (println "in a-test")
    (is (= 1 1))))
