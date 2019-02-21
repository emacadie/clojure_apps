(ns simply-clojure.chapter06-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter06 :refer :all]
            ; [simply-clojure.helper :as helper]
))

(test/deftest sign-test
  (test/testing "Testing sign-test"
    (test/is (= :positive (sign-number 1)))
    (test/is (= :negative (sign-number -1)))
    (test/is (= :positive (sign-number 0.1)))
    (test/is (= :negative (sign-number -0.1)))
    (test/is (= :zero (sign-number 0)))))

(test/deftest utensil-test
  (test/testing "Test if-utensil"
    (test/is (= :chopsticks (if-utensil :Chinese)))
    (test/is (= :fork (if-utensil :Merican)))
    ;; keywords are case-sensitive
    (test/is (= :fork (if-utensil :chinese)))
    (test/is (= :fork (if-utensil :Chines)))))

(test/deftest test-to-american-time
  (test/testing "Testing to-american-time"
    (test/is (= "9 PM" (to-american-time 21)))
    (test/is (= "12 PM" (to-american-time 12)))
    (test/is (= "11 AM" (to-american-time 11)))))

(test/deftest test-to-european-time
  (test/testing "Testing to-european-time"
    (test/is (= "8" (to-european-time "8 AM")))
    (test/is (= "0" (to-european-time "12 AM")))
    (test/is (= "11" (to-european-time "11 AM")))
    (test/is (= "12" (to-european-time "12 PM")))
    (test/is (= "16" (to-european-time "4 PM")))
    (test/is (thrown? java.lang.NumberFormatException (to-european-time "XX PM")))))

(test/deftest test-teen?
  (test/testing "Testing teen?"
    (test/is (= false (teen? "14")))
    (test/is (= false (teen? :14)))
    (test/is (= false (teen? 12)))
    (test/is (= true (teen? 13)))
    (test/is (= true (teen? 19)))
    (test/is (= false (teen? 20)))))

(test/deftest test-type-of
  (test/testing "Testing type-of"
    ; (test/is (= :boolean (type-of true)))
    (test/is (= :number (type-of 33)))
    (test/is (= :number (type-of 33.33)))
    (test/is (= :sentence (type-of "this is a sentence")))
    (test/is (= :word (type-of "thisisaword")))
    (test/is (= :nothing (type-of [1 2 3 4])))))

(test/deftest test-indef-article
  (test/testing "Testing inef-article"
    (test/is (= "a beatle") (indef-article "beatle"))
    (test/is (= "an beatle") (indef-article "album"))))

(test/deftest test-plural
  (test/testing "Testing plural"
    (test/is (= "boots" (plural "boot")))
    (test/is (= "hexes" (plural "hex")))
    (test/is (= "histories" (plural "history")))
    (test/is (= "toys" (plural "toy")))))

(test/deftest test-sort2
  (test/testing "Testing sort2"
    (test/is (= [1 2]) (sort2 [1 2]))
    (test/is (= [1 2]) (sort2 [2 1]))))

(test/deftest test-sort2-dest
  (test/testing "Testing sort2-dest"
    (test/is (= [1 2]) (sort2-dest [1 2]))
    (test/is (= [1 2]) (sort2-dest [2 1]))))

(test/deftest test-valid-date?
  (test/testing "Testing valid-date?"
    (test/is (= false (valid-date? 0 0 0)))
    (test/is (= false (valid-date? -1 -1 0)))
    (test/is (= false (valid-date? 13 31 0)))
    (test/is (= false (valid-date? 12 32 0)))
    (test/is (= false (valid-date? 9 31 2019)))
    (test/is (= true (valid-date? 9 30 2019)))
    (test/is (= false (valid-date? 10 32 2019)))
    (test/is (= true (valid-date? 10 31 2019)))
    (test/is (= false (valid-date? 2 29 2019)))
    (test/is (= true (valid-date? 2 28 2019)))
    (test/is (= true (valid-date? 2 29 2020)))
    ;; from the book
    (test/is (= true (valid-date? 10 4 1949)))
    (test/is (= false (valid-date? 20 4 1776)))
    (test/is (= false (valid-date? 5 0 1992)))
    (test/is (= false (valid-date? 2 29 1900)))
    (test/is (= true (valid-date? 2 29 2000)))
))

(test/deftest test-describe-time
  (test/testing "Testing describe-time"
    (test/is (= "15.5 minutes" (describe-time 930)))
    (test/is (= "45 seconds" (describe-time 45)))
    (test/is (= "9.506426344208686 centuries" (describe-time 30000000000)))))

