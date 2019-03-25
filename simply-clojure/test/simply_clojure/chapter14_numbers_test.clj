(ns simply-clojure.chapter14-numbers-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter14-numbers :refer :all]
            [simply-clojure.helper    :as helper]))

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



