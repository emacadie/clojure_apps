(ns info.shelfunit.funcjava.chap01-test
  (:require [clojure.test :refer :all]
            [info.shelfunit.funcjava.chap01 :refer :all]))

(deftest test-calculate-the-long-way
  (testing "Testing the long way"
    (println "in test-calculcate-the-long-way, which returns: " calculate-the-long-way)
    (is (= 67.5 (calculate-the-long-way)))))

(deftest test-calculate-the-one-liner
  (testing "Testing the one liner"
    (println "in test-calculate-the-one-liner, which returns: " calculate-the-one-liner)
    (is (= 67.5 (calculate-the-one-liner)))))

(deftest test-calculate-the-threaded-way
  (testing "Testing the threaded way"
    (println "in test-calculate-the-threaded-way, which returns: " calculate-the-threaded-way)
    (is (= 67.5 (calculate-the-threaded-way)))))

(deftest a-test
  (testing "Default test."
    (println "in a-test")
    (is (= 1 1))))
