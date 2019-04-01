(ns simply-clojure.chapter15-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter15 :refer :all]
            [simply-clojure.helper    :as helper]))

(test/deftest test-closest-power-of-2
  (test/testing "Testing closest-power-of-2"
    (test/is (= 4 (closest-power-of-2 22 0)))))

(test/deftest test-get-all-powers-of-2
  (test/testing "Test get-all-powers-of-2"
    (test/is (= [1 6] (get-all-powers-of-2 33)))))

(test/deftest test-to-binary
  (test/testing "Test to-binary"
    (test/is (= [1 0 0 1]   (to-binary-r 9)))
    (test/is (= [1 0 1 1 1] (to-binary-r 23)))))


