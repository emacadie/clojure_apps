(ns simply-clojure.chapter12-test
  (:require [simply-clojure.chapter12 :refer :all]
            [clojure.test          :as test]            
            [simply-clojure.helper :as helper]))

(test/deftest test-ffunc
  (test/testing "Testing ffunc"
    (test/is (= "time a upon once") (ffunc "once upon a time"))))

(test/deftest test-exaggerate12
  (test/testing "Testing exaggerate12"
    (test/is (= "I ate 6.0 potstickers") (exaggerate12 "I ate 3 potstickers"))
    (test/is (= "The chow fun is great here") (exaggerate12 "The chow fun is good here"))
    (test/is (= "But the egg drop soup is terrible") (exaggerate12 "But the egg drop soup is bad"))))

(test/deftest test-gpa
  (test/testing "Testing gpa"
    (test/is (= 3.665 (gpa12 ["A" "A+" "B+" "B"])))))

(test/deftest test-spell-number
  (test/testing "Testing spell-number"
    (test/is (= "one nine seven one") (spell-number 1971))))

(test/deftest test-numbers
  (test/testing "Testing numbers"
    (test/is (= "76 110" (numbers "76 trombones and 110 cornets")))))

(test/deftest test-arabic
  (test/testing "Testing arabic"
    (test/is (= 1066 (arabic "MLXVI")))
    (test/is (= 1971 (arabic "MCMLXXI")))))

(test/deftest test-arabic-recur
  (test/testing "Testing arabic-recur"
    (test/is (= 10 (arabic-recur "X")))
    (test/is (= 1066 (arabic-recur "MLXVI")))
    (test/is (= 1971 (arabic-recur "MCMLXXI")))))

(test/deftest test-describe-time12
  (test/testing "Testing describe-time12"
    (test/is (= "6 HOURS 10 MINUTES 22 SECONDS" (describe-time12 22222)))
    (test/is (= "1 CENTURY 57 YEARS 20 WEEKS 6 DAYS 8 HOURS 54 MINUTES 1 SECOND" (describe-time12 4967189641)))
    (test/is (= "1 CENTURY 57 YEARS 20 WEEKS 6 DAYS 8 HOURS 54 MINUTES 0 SECONDS" (describe-time12 4967189640)))))

(test/deftest test-describe-time12-r
  (test/testing "Testing describe-time12-r"
    (test/is (= "6 HOURS 10 MINUTES 22 SECONDS" (describe-time12-r 22222)))
    (test/is (= "1 CENTURY 57 YEARS 20 WEEKS 6 DAYS 8 HOURS 54 MINUTES 1 SECOND" (describe-time12-r 4967189641)))
    (test/is (= "1 CENTURY 57 YEARS 20 WEEKS 6 DAYS 8 HOURS 54 MINUTES 0 SECONDS" (describe-time12-r 4967189640)))))


