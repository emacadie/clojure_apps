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
    (test/is (= :nothing (type-of [1 2 3 4])))
)
)

