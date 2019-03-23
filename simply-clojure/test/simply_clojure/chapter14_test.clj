(ns simply-clojure.chapter14-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter14 :refer :all]
            [simply-clojure.helper    :as helper]))

(comment
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
    (test/is (= "ookkee" (doubles-r "bookkeeper" ""))))))

(test/deftest test-remove-once
  (test/testing "Testing remove-once"
    (test/is (= "good good morning" (helper/remove-starting-space-from-string (remove-once "morning" "good morning good morning"))))
    (test/is (= "good morning good morning" (helper/remove-starting-space-from-string (remove-once "mourning" "good morning good morning"))))
    (test/is (= "morning good morning" (helper/remove-starting-space-from-string (remove-once "good" "good morning good morning"))))))

(test/deftest test-remove-dups
  (test/testing "Testing remove-dups"
    (test/is (= "ob la di la da") (remove-dups "ob la di da"))
    (test/is (= "good morning good morning") (remove-dups "good morning"))))

(test/deftest test-odds
  (test/testing "Testing odds"
    (test/is (= "i my girl") (odds-r "i lost my little girl"))
    (test/is (= "this not drill and") (odds-r "this is not a drill boys and girls"))))

(test/deftest test-letter-count-r
  (test/testing "Testing letter-count-r"
    (test/is (= 11 (letter-count-r "fixing a hole")))))

(test/deftest test-member-r
  (test/testing "Testing member-r"
    (test/is (= true (member-r "what" "ask not what your country can do for you")))
    (test/is (= false (member-r "when" "ask not what your country can do for you")))))

(test/deftest test-differences
  (test/testing "Testing differences"
    (test/is (= [19 -14 78 -81 6] (differences [4 23 9 87 6 12])))
    (test/is (= [19 -14 78 -81 6] (differences '(4 23 9 87 6 12))))))

(test/deftest test-print-n-times
  (test/testing "Testing print-n-times"
    (test/is (= "hello hello hello" (print-n-times 3 "hello")))
    (test/is (= "moon moon moon moon" (print-n-times 4 "moon")))
    (test/is (= "moon moon moon moon" (print-n-times 4.0 "moon")))))

(test/deftest test-expand-r
  (test/testing "Testing expand-r"
    ; (test/is (= "calling calling calling calling birds french french french hens" (expand-r "4 calling birds 3 french hens")))
    ; (test/is (= "the samurai samurai samurai samurai samurai samurai samurai" (expand-r "the 7 samurai")))
    ))

(test/deftest test-location
  (test/testing "Testing location"
    (test/is (= 4 (location "me" "you never give me your money")))
    (test/is (= 0 (location "me" "you never give them your money")))))




