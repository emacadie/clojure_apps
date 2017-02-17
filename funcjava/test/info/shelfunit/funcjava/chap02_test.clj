(ns info.shelfunit.funcjava.chap02-test
  (:require [clojure.test :refer :all]
            [info.shelfunit.funcjava.chap02 :refer :all]))

(deftest test-transform-list-to-uppercase
  (testing "Testing transform-list-to-uppercase"
    (println "in test-transform-list-to-uppercase")
    (transform-list-to-uppercase)
    (is (= 1 1))))

(deftest test-get-count-of-names
  (testing "Testing get-count-of-names"
    (println "in test-get-count-of-names")
    (get-count-of-names)
    (is (= 1 1))))

(deftest test-reverse-upper-case
  (testing "Testing reverse-upper-case"
    (println "In test-reverse-upper-case ")
    (reverse-upper-case)
    (is (= 1 1))))

(deftest a-test
  (testing "Default test."
    (println "in a-test")
    (is (= 1 1))))
