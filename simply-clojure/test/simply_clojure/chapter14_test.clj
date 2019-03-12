(ns simply-clojure.chapter14-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter14 :refer :all]
            [simply-clojure.helper      :as helper]))

(test/deftest test-square-sentr
  (test/testing "Testing square-sentr"
    (test/is (= [4 9 16] (vec (square-sentr [2 3 4]))))
    (test/is (= [4 9 16 25] (vec (square-sentr [2 3 "AA" 4 5]))))))

(test/deftest test-pigl-sentr
  (test/testing "Testing pigl-sentr"
    (test/is (= "isthay isay igpay atinlay") (pigl-sentr "this is pig latin"))))

(test/deftest test-disjoint-pairs
  (test/testing "Testing disjoint-pairs"
    (test/is (= "TR IP OL I" (disjoint-pairs "TRIPOLI")))
    (test/is (= "CA NT AL OU PE" (disjoint-pairs "CANTALOUPE")))))

(test/deftest test-keep-three-letter-words-r
  (test/testing "Testing keep-three-letter-words-r"
    (test/is (= "mom new dog" (keep-three-letter-words-r "Give mom a new black dog")))
    (test/is (= "cuz the old one may die any day now" (keep-three-letter-words-r "cuz the old one may die any day now")))
    (test/is (= "" (keep-three-letter-words-r "I love to watch NCIS")))))

(test/deftest test-keep-vowels-r
  (test/testing "Testing keep-vowels-r"
    (test/is (= "aoea" (keep-vowels-r "Napolean" "")))))

(test/deftest test-doubles-r
  (test/testing "Testing doubles-r"
    (test/is (= "sssspp" (doubles-r "Mississippi" "")))
    (test/is (= "ookkee" (doubles-r "bookkeeper" "")))))



