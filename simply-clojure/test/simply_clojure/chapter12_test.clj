(ns simply-clojure.chapter12-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter12 :refer :all]
            [simply-clojure.helper      :as helper]
            ; [clojure.math.numeric-tower :as math-nt]
            )
)

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

