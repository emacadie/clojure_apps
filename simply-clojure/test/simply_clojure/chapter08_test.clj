(ns simply-clojure.chapter08-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter08 :refer :all]
            [simply-clojure.helper :as helper]))

(test/deftest test-choose-beatles
  (test/testing "Testing choose-beatles"
    (test/is (= ["George" "Ringo"] (choose-beatles ends-vowel?)))
    (test/is (= ["John" "Paul" "George"] (choose-beatles even-count?)))
    (test/is (= ["George" "Ringo"] (choose-beatles #(>= (count %1) 5))))))

(test/deftest test-choose-beatles-set
  (test/testing "Testing choose-beatles-set"
    (test/is (= '("George" "Ringo") (choose-beatles-set ends-vowel?)))
    (test/is (= '("George" "John" "Paul") (choose-beatles-set even-count?)))
    (test/is (= '("George" "Ringo")  (choose-beatles-set #(>= (count %1) 5))))))

(test/deftest test-transform-beatles
  (test/testing "Testing transform-beatles"
    (test/is (= ["the amazing John" 
                 "the amazing Paul" 
                 "the amazing George" 
                 "the amazing Ringo"] (transform-beatles amazify)))
    (test/is (= ["ohn" "aul" "eorge" "ingo"] (transform-beatles helper/butfirst-string)))))

(test/deftest test-transform-beatles-set
  (test/testing "Testing transform-beatles-set"
    (test/is (= '("the amazing George" 
                  "the amazing John" 
                  "the amazing Paul" 
                  "the amazing Ringo") (transform-beatles-set amazify)))
    (test/is (= '("eorge" "ohn" "aul" "ingo") (transform-beatles-set helper/butfirst-string)))))

(test/deftest test-words
  (test/testing "Testing words"
    (test/is (= [:alpha :charlie :delta] (words "acd")))))

(test/deftest test-letter-count
  (test/testing "Testing letter-count"
    (test/is (= 11 (letter-count "Fixing a hole")))))



