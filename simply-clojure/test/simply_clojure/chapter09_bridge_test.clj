(ns simply-clojure.chapter09-bridge-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter09-bridge :refer :all]
            [simply-clojure.helper      :as helper]))

(test/deftest test-card-val
  (test/testing "Testing card-val"
    (test/is (= 2 (card-val :cq)))
    (test/is (= 2 (card-val :cQ)))
    (test/is (= 0 (card-val :s7)))
    (test/is (= 4 (card-val :ha)))))

(test/deftest test-high-card-points
  (test/testing "Testing high-card-points"
    (test/is (= 9 (high-card-points '(:sa :s10 :hq :ck :c4))))
    (test/is (= 9 (high-card-points [:sa :s10 :hq :ck :c4])))
    (test/is (= 13 (high-card-points '(:sa :s10 :s7 :s6 :s2 :hq :hj :h9 :ck :c4 :dk :d9 :d3))))))

(test/deftest test-count-suit
  (test/testing "Testing count-suit"
    (test/is (= 2 (count-suit "s" '(:sa :s10 :hq :ck :c4))))
    (test/is (= 2 (count-suit "c" '(:sa :s10 :s7 :s6 :s2 :hq :hj :h9 :ck :c4 :dk :d9 :d3))))
    (test/is (= 5 (count-suit "d" '(:h3 :d7 :sk :s3 :c10 :dq :d8 :s9 :s4 :d10 :c7 :d4 :s2))))))

(test/deftest test-suit-counts
  (test/testing "Testing suit-counts"
    (test/is (= '(2 1 2 0) (suit-counts '(:sa :s10 :hq :ck :c4))))
    (test/is (= '(5 3 2 3) (suit-counts '(:sa :s10 :s7 :s6 :s2 :hq :hj :h9 :ck :c4 :dk :d9 :d3))))
    (test/is (= '(5 1 2 5) (suit-counts '(:h3 :d7 :sk :s3 :c10 :dq :d8 :s9 :s4 :d10 :c7 :d4 :s2))))))

(test/deftest test-suit-dist-points
  (test/testing "Testing suit-dist-points"
    (test/is (= 1 (suit-dist-points 2)))
    (test/is (= 0 (suit-dist-points 7)))
    (test/is (= 3 (suit-dist-points 0)))))

(test/deftest test-hand-dist-points
  (test/testing "Testing hand-dist-points"
    (test/is (= 1 (hand-dist-points '(:sa :s10 :s7 :s6 :s2 :hq :hj :h9 :ck :c4 :dk :d9 :d3))))
    (test/is (= 3 (hand-dist-points '(:h3 :d7 :sk :s3 :c10 :dq :d8 :s9 :s4 :d10 :c7 :d4 :s2))))))

(test/deftest test-bridge-val
  (test/testing "Testing bridge-val"
    (test/is (= 14 (bridge-val '(:sa :s10 :s7 :s6 :s2 :hq :hj :h9 :ck :c4 :dk :d9 :d3))))
    (test/is (= 8 (bridge-val '(:h3 :d7 :sk :s3 :c10 :dq :d8 :s9 :s4 :d10 :c7 :d4 :s2))))))

