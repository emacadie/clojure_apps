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
    (test/is (= ["ohn" "aul" "eorge" "ingo"] (transform-beatles helper/butfirst-word)))))

(test/deftest test-transform-beatles-set
  (test/testing "Testing transform-beatles-set"
    (test/is (= '("the amazing George" 
                  "the amazing John" 
                  "the amazing Paul" 
                  "the amazing Ringo") (transform-beatles-set amazify)))
    (test/is (= '("eorge" "ohn" "aul" "ingo") (transform-beatles-set helper/butfirst-word)))))

(test/deftest test-words
  (test/testing "Testing words"
    (test/is (= [:alpha :charlie :delta] (words "acd")))))

(test/deftest test-letter-count
  (test/testing "Testing letter-count"
    (test/is (= 11 (letter-count "Fixing a hole")))))

(test/deftest test-exaggerate
  (test/testing "Testing exaggerate"
    (test/is (= "I ate 6.0 potstickers") (exaggerate "I ate 3 potstickers"))
    (test/is (= "The chow fun is great here") (exaggerate "The chow fun is good here"))
    (test/is (= "But the egg drop soup is terrible") (exaggerate "But the egg drop soup is bad"))))

(test/deftest test-true-for-all?
  (test/testing "Testing true-for-all?"
    (test/is (= true (true-for-all? even? '(2 4 6 8))))
    (test/is (= false (true-for-all? even? '(2 6 3 4))))))

(test/deftest test-gpa
  (test/testing "Testing gpa"
    (test/is (= 3.665 (gpa ["A" "A+" "B+" "B"])))))

(test/deftest test-count-ums
  (test/testing "Testing count-ums"
    (test/is (= 3 (count-ums "today um we are going to um talk about functional um programming")))))

(test/deftest test-phone-unspell
  (test/testing "Testing phone-unspell"
    (test/is (= '(7 6 7 2 6 7 6) (phone-unspell "popcorn")))))

(test/deftest test-subword
  (test/testing "Testing subword"
    (test/is (= "then" (subword "polythene" 5 8)))))

