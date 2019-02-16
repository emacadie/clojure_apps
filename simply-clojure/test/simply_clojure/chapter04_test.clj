(ns simply-clojure.chapter04-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter04 :refer :all]
            [simply-clojure.helper :as helper]))

(test/deftest test-x-to-power-of-y
  (test/testing "Testing x-to-the-power-of-y"
    (test/is (= 8 (x-to-power-of-y 2 3)))
    (test/is (= 1024 (x-to-power-of-y 4 5)))
    (test/is (= 1/1024 (x-to-power-of-y 4 -5)))))

(test/deftest test-simply-abs
  (test/testing "Testing simply-abs"
    (test/is (= 33 (simply-abs 33)))
    (test/is (= 33 (simply-abs -33)))))

(test/deftest test-scientific
  (test/testing "Testing scientific"
    (test/is (= 7000 (scientific 7 3)))
    ; Work on this later
    ; (test/is (= 0.00042 (helper/round (scientific 42 -5) 4)))
))

(test/deftest test-discount
  (test/testing "Testing discount"
    (test/is (= 9.50 (discount 10 5)))
    (test/is (= 14.95 (discount 29.90 50))))
  (test/testing "Testing positive assertions"
    (test/is (thrown? AssertionError (discount 29.90 -50)))
    (test/is (thrown? AssertionError (discount -29.90 50)))))

(test/deftest test-tip
  (test/testing "Testing tip"
    ; (test/is (= 3.02 (tip 19.98)))
    (test/is (= 4.77 (tip 29.23)))
    (test/is (= 1.46 (tip 7.54)))
)
)

