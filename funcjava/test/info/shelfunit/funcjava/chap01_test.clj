(ns info.shelfunit.funcjava.chap01-test
  (:require [clojure.test :refer :all]
            [info.shelfunit.funcjava.chap01 :refer :all]))

(deftest test-the-long-way
  (testing "Testing the long way"
    (println "in test-the-long-way, which returns: " the-long-way)
    (is (= 67.5 (the-long-way)))))

(deftest test-the-one-liner
  (testing "Testing the one liner"
    (println "in test-the-one-liner, which returns: " the-one-liner)
    (is (= 67.5 (the-one-liner)))))

(deftest test-the-threaded-way
  (testing "Testing the threaded way"
    (println "in test-the-threaded-way, which returns: " the-threaded-way)
    (is (= 67.5 (the-threaded-way)))))

(deftest a-test
  (testing "Default test."
    (println "in a-test")
    (is (= 1 1))))
