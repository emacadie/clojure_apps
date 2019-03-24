(ns simply-clojure.chapter14-vector-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter14-vector :refer :all]
            [simply-clojure.helper    :as helper]))

(comment
(test/deftest test-square-sentr
  (test/testing "Testing square-sentr"
    (test/is (= [4 9 16] (vec (square-sent-v [2 3 4]))))
    (test/is (= [4 9 16 25] (vec (square-sent-v [2 3 "AA" 4 5]))))))

(test/deftest test-pigl-sentv
  (test/testing "Testing pigl-sentv"
    (test/is (= "isthay isay igpay atinlay") (pigl-sentv "this is pig latin"))))

(test/deftest test-disjoint-pairs-v
  (test/testing "Testing disjoint-pairs-v"
    (test/is (= "TR IP OL I" (disjoint-pairs-v "TRIPOLI")))
    (test/is (= "CA NT AL OU PE" (disjoint-pairs-v "CANTALOUPE")))))

(test/deftest test-keep-three-letter-words-v
  (test/testing "Testing keep-three-letter-words-v"
    (test/is (= "mom new dog" (keep-three-letter-words-v "Give mom a new black dog")))
    (test/is (= "cuz the old one may die any day now" (keep-three-letter-words-v "cuz the old one may die any day now")))
    (test/is (= "" (keep-three-letter-words-v "I love to watch NCIS")))))

(test/deftest test-keep-vowels-r
  (test/testing "Testing keep-vowels-r"
    (test/is (= "aoea" (keep-vowels-r "Napolean" "")))))

(test/deftest test-doubles-r
  (test/testing "Testing doubles-r"
    (test/is (= "sssspp" (doubles-r "Mississippi" "")))
    (test/is (= "ookkee" (doubles-r "bookkeeper" ""))))))

(test/deftest test-remove-once
  (test/testing "Testing remove-once"
    (test/is (= "good good morning" (remove-once "morning" "good morning good morning")))
    (test/is (= "good morning good morning" (remove-once "mourning" "good morning good morning")))
    (test/is (= "morning good morning" (remove-once "good" "good morning good morning")))))

(test/deftest test-remove-once-v
  (test/testing "Testing remove-once-v"
    (test/is (= "good good morning" (remove-once-v "morning" "good morning good morning")))
    (test/is (= "good morning good morning" (remove-once-v "mourning" "good morning good morning")))
    (test/is (= "morning good morning" (remove-once-v "good" "good morning good morning")))))

(test/deftest test-remove-dups-v
  (test/testing "Testing remove-dups-v"
    (test/is (= "ob la di da" (remove-dups-v "ob la di ob la da")))
    ; (println "Here is (remove-dups-v 'ob la di ob la da'): ", (remove-dups-v "ob la di ob la da"))
    (test/is (= "good morning" (remove-dups-v "good morning good morning")))))

(test/deftest test-odds-v
  (test/testing "Testing odds-v"
    (test/is (= "i my girl" (odds-v "i lost my little girl")))
    (test/is (= "this not drill and" (odds-v "this is not a drill boys and girls")))))

(test/deftest test-letter-count-r
  (test/testing "Testing letter-count-r"
    (test/is (= 11 (letter-count-r "fixing a hole")))))

(test/deftest test-member-v
  (test/testing "Testing member-v"
    (test/is (= true (member-v "what" "ask not what your country can do for you")))
    (test/is (= false (member-v "when" "ask not what your country can do for you")))))

(test/deftest test-differences
  (test/testing "Testing differences"
    (test/is (= [19 -14 78 -81 6] (differences [4 23 9 87 6 12])))
    (test/is (= [19 -14 78 -81 6] (differences '(4 23 9 87 6 12))))))

(test/deftest test-print-n-times
  (test/testing "Testing print-n-times"
    (test/is (= "hello hello hello" (helper/join-with-spaces (flatten (print-n-times 3 "hello")) )))
    (test/is (= "moon moon moon moon" (helper/join-with-spaces (flatten (print-n-times 4 "moon")))))
    (test/is (= "moon moon moon moon" (helper/join-with-spaces (flatten (print-n-times 4.0 "moon")))))))

(test/deftest test-expand-v
  (test/testing "Testing expand-v"
    (test/is (= "calling calling calling calling birds french french french hens" (expand-v "4 calling birds 3 french hens")))
    (test/is (= "the samurai samurai samurai samurai samurai samurai samurai" (expand-v "the 7 samurai")))))

(test/deftest test-location-v
  (test/testing "Testing location-v"
    (test/is (= 4 (location-v "me" "you never give me your money")))
    (test/is (= 0 (location-v "me" "you never give them your money")))))

(test/deftest test-count-adjacent-dups-v
  (test/testing "Testing count-adjacent-dups-v"
    (test/is (= 3 (count-adjacent-dups-v "y a b b a d a b b a d o o")))
    (test/is (= 2 (count-adjacent-dups-v "yeah yeah yeah")))
    (test/is (= 0 (count-adjacent-dups-v "this string has no dups")))))

(test/deftest test-remove-adjacent-dups-v
  (test/testing "Testing remove-adjacent-dups-v"
    (test/is (= "y a b a d a b a d o" (remove-adjacent-dups-v "y a b b a d a b b a d o o")))
    (test/is (= "yeah" (remove-adjacent-dups-v "yeah yeah yeah")))))

(test/deftest test-progressive-squares?
  (test/testing "Testing progressive-squares?"
    (test/is (= true  (progressive-squares? [3 9 81 6561])))
    (test/is (= false (progressive-squares? [25 36 49 64])))))

(test/deftest test-same-shape?
  (test/testing "Testing same-shape?"
    (test/is (= true  (same-shape? "the fool on the hill" "you like me too much" )))
    (test/is (= false (same-shape? "the fool on the hill" "and your bird can sing")))))

(test/deftest test-merge-v
  (test/testing "Testing merge-v"
    (test/is (= [3 4 6 7 9 12 18 24 36 40 50 99] (merge-v [4 7 18 40 99] [3 6 9 12 24 36 50])))
    (test/is (= [3 4 6 7 9 12 18 24 36 40 50 88 99] (merge-v [4 7 18 40 88 99] [3 6 9 12 24 36 50])))
    (test/is (= [3 4 6 7 9 12 18 24 36 40 50] (merge-v [4 7 18 40] [3 6 9 12 24 36 50])))))


