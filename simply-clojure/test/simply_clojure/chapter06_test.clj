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

;;> (european-time '(8 am))
;;8
;;> (european-time '(4 pm))
;;16
;;> (european-time '(12 am))
;;24
(test/deftest test-to-european-time
  (test/testing "Testing to-european-time"
    (test/is (= "8" (to-european-time "8 AM")))
)  
)


