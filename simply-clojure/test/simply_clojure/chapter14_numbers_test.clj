(ns simply-clojure.chapter14-numbers-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter14-numbers :refer :all]
            [simply-clojure.helper    :as helper]))

(test/deftest test-get-new-teen-table
  (test/testing "Testing get-new-teen-table"
    (test/is (= "ten"       (get-new-teen-table "")))
    (test/is (= "eleven"    (get-new-teen-table "one")))
    (test/is (= "twelve"    (get-new-teen-table "two")))
    (test/is (= "thirteen"  (get-new-teen-table "three")))
    (test/is (= "fourteen"  (get-new-teen-table "four")))
    (test/is (= "fifteen"   (get-new-teen-table "five")))
    (test/is (= "eighteen"  (get-new-teen-table "eight")))
    (test/is (= "nineteen"  (get-new-teen-table "nine")))))

(test/deftest test-exponent-helper
  (test/testing "Testing exponent-helper"
    (test/is (= "five hundred thirteen thousand" (exponent-helper "five hundred thirteen" 2)))
)
)

(test/deftest test-remove-leading-zeros
  (test/testing "Testing remove-leading-zeros"
    (test/is (= "100" (remove-leading-zeros "100")))
    (test/is (= "345" (remove-leading-zeros "345")))
    (test/is (= "45"  (remove-leading-zeros "045")))
    (test/is (= "5"   (remove-leading-zeros "005")))))

(test/deftest test-get-last-3
  (test/testing "Testing get-last-3"
    (test/is (= "345" (get-last-3 "12345")))
    (test/is (= "45"  (get-last-3 "45")))))

(test/deftest test-break-string-to-threes
  (test/testing "Testing break-string-to-threes"
    (test/is (= ["12" "345"]        (break-string-to-threes "12345")))
    (test/is (= ["12" "" "345"]     (break-string-to-threes "12000345")))
    (test/is (= ["12" "100" "345"]  (break-string-to-threes "12100345")))
    (test/is (= ["123" "456" "789"] (break-string-to-threes "123456789")))
    (test/is (= ["45"]              (break-string-to-threes "45")))))

(test/deftest test-nth-string-index 
  (test/testing "Testing nth-string-index"
    (test/is (= "hello" (nth-string-index ["jjj" "hello"] "1")))
    (test/is (= "jjj" (nth-string-index ["jjj" "hello"] "0")))))

(test/deftest test-num-name2-work
  (test/testing "Testing num-name2-work"
    (test/is (= "two hundred twenty-one" (num-name2-work "221")))
    (test/is (= "two hundred twenty" (num-name2-work "220")))
    (test/is (= "twenty-one" (num-name2-work "21")))
    (test/is (= "twenty" (num-name2-work "20")))
    (test/is (= "eighteen" (num-name2-work "18")))
    (test/is (= "two hundred eighteen" (num-name2-work "218")))
    (test/is (= "eight" (num-name2-work "8")))))



