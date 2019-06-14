(ns simply-clojure.chapter17-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter17 :refer :all]
            [simply-clojure.helper    :as helper]))

(test/deftest test-my-max
  (test/testing "Testing my-max"
    (test/is (= 4 (my-max 1 2 3 4)))
    (test/is (= 4 (my-max 1 2 4 3 4)))
    (test/is (= 4 (my-max 4 3 2 1)))
    (test/is (= 4 (my-max 4 3 4 2 1)))
    (test/is (= 5 (my-max 4 3 5 2 1)))))

(test/deftest test-my-max-loop-recur
  (test/testing "Testing my-max-loop-recur"
    (test/is (= 4 (my-max-loop-recur 1 2 3 4)))
    (test/is (= 4 (my-max-loop-recur 1 2 4 3 4)))
    (test/is (= 4 (my-max-loop-recur 4 3 2 1)))
    (test/is (= 4 (my-max-loop-recur 4 3 4 2 1)))
    (test/is (= 5 (my-max-loop-recur 4 3 5 2 1)))))

(test/deftest test-my-max-reduce
  (test/testing "Testing my-max-reduce"
    (test/is (= 4 (my-max-reduce 1 2 3 4)))
    (test/is (= 4 (my-max-reduce 1 2 4 3 4)))
    (test/is (= 4 (my-max-reduce 4 3 2 1)))
    (test/is (= 4 (my-max-reduce 4 3 4 2 1)))
    (test/is (= 5 (my-max-reduce 4 3 5 2 1)))))




