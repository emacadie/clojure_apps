(ns simply-clojure.chapter09-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter09 :refer :all]
            [simply-clojure.helper :as helper]))

(test/deftest test-prepend-every
  (test/testing "Testing prepend-every"
    (test/is (= "she said she said" (prepend-every "s" ["he" "aid" "he" "aid"])))
    (test/is (= "antidote antipasto antigone antibody" (prepend-every "anti" ["dote" "pasto" "gone" "body"])))))

(test/deftest test-sentence-version
  (test/testing "Test sentence-version"
    (test/is (= '(64 4 16 36) ((sentence-version helper/square) [8 2 4 6])))
    (test/is (= '("i" "i" "f") ((sentence-version helper/first-string) (helper/split-string-to-words "if i fell"))))))

(test/deftest test-letterwords
  (test/testing "Test lettewords"
    (test/is (= '("got" "to" "you" "into") (letterwords "o" "got to get you into my life")))))



