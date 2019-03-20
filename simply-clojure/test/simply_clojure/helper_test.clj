(ns simply-clojure.helper-test
  (:require [clojure.test :as test]
            [simply-clojure.helper :refer :all]))

(test/deftest a-test
  (test/testing "FIXME, I fail."
    (test/is (= 0 0))))

(test/deftest test-string-is-word
  (test/testing "Working with string 'this is not a word'"
    (test/is (= false (string-is-word "this is not a word"))))

  (test/testing "Working with string 'thisisalongword'"
    (test/is (= true (string-is-word "thisisalongword")))))

(test/deftest test-vowel?
  (test/testing "Testing vowel?"
    (test/is (= true (vowel? "A")))
    (test/is (= true (vowel? "a")))
    (test/is (= false (vowel? "B")))
    (test/is (= false (vowel? "b")))))

(test/deftest test-butfirst-string
  (test/testing "Testing a string 'this is a sentence'"
    (test/is (= "is a sentence" (butfirst-string "this is a sentence"))))
  (test/testing "Testing string 'thisisalongword'"
    (test/is (= "" (butfirst-string "thisisalongword")))))

(test/deftest test-first-string
  (test/testing "Testing first with string 'test with a sentence'"
    (test/is (= "test" (first-string "test with a sentence"))))
  (test/testing "testing first with string 'testwithfakesentence"
    (test/is (= "testwithfakesentence" (first-string "testwithfakesentence")))))

(test/deftest test-first-word
  (test/testing "Testing first with string 'test with a sentence'"
    (test/is (= "test with a sentence" (first-word "test with a sentence"))))
  (test/testing "testing first with string 'testwithfakesentence"
    (test/is (= "t" (first-word "testwithfakesentence")))))

(test/deftest test-last-string
  (test/testing "Testing first with string 'test with a sentence'"
    (test/is (= "sentence" (last-string "test with a sentence"))))
  (test/testing "testing first with string 'testwithfakesentence"
    (test/is (= "testwithfakesentence" (last-string "testwithfakesentence")))))

(test/deftest test-last-word
  (test/testing "Testing first with string 'test with a sentence'"
    (test/is (= "test with a sentence" (last-word "test with a sentence"))))
  (test/testing "testing first with string 'testwithfakesentence"
    (test/is (= "e" (last-word "testwithfakesentence")))))

(test/deftest test-butlast-string
  (test/testing "Testing a string 'this is a sentence'"
    (test/is (= "this is a" (butlast-string "this is a sentence"))))
  (test/testing "Testing string 'thisisalongword'"
    (test/is (= "" (butlast-string "thisisalongword")))))

(test/deftest test-butlast-word
  (test/testing "Testing a string 'this is a sentence'"
    (test/is (= "this is a sentence" (butlast-word "this is a sentence"))))
  (test/testing "Testing string 'thisisalongword'"
    (test/is (= "thisisalongwor" (butlast-word "thisisalongword")))))

(test/deftest test-number-tween-inclusive
  (test/testing "Test number-tween-inclusive"
    (test/is (= true (number-tween-inclusive 14 12 15)))
    (test/is (= true (number-tween-inclusive 14 13 15)))
    (test/is (= true (number-tween-inclusive 14 14 15)))
    (test/is (= true (number-tween-inclusive 14 13 14)))
    (test/is (= false (number-tween-inclusive 14 15 17)))
    (test/is (= false (number-tween-inclusive 18 15 17)))))

(test/deftest test-second-string
  (test/testing "Testing second-string"
    (test/is (= "do" (second-string "Let's do some Clojure")))
    (test/is (= "thisisalongword" (second-string "thisisalongword")))
    (test/is (= "AM" (second-string "8 AM")))))

(test/deftest test-is-string-number
  (test/testing "Testing is-string-number?"
    (test/is (= true (is-string-number? "3")))
    (test/is (= true (is-string-number? "-3")))
    (test/is (= true (is-string-number? "3.0")))
    (test/is (= true (is-string-number? "-3.09887")))
    (test/is (= false (is-string-number? "3A")))
    (test/is (= false (is-string-number? "H")))))

(test/deftest test-butfirst-two-string
  (test/testing "Testing butfirst-two-string"
    (test/is (= "my test string" (butfirst-two-string "This is my test string")))
    (test/is (= "" (butfirst-two-string "This")))))

(test/deftest test-butfirst-two-word
  (test/testing "Testing butfirst-two-word"
    (test/is (= "This is my test string" (butfirst-two-word "This is my test string")))
    (test/is (= "is" (butfirst-two-word "This")))))

(test/deftest test-safe-subs
  (test/testing "Testing safe-subs"
    (test/is (= "lojure" (safe-subs "Clojure" 1)))
    (test/is (= "lo" (safe-subs "Clojure" 1 3)))
    (test/is (= "Clojure" (safe-subs "Clojure" 1 20)))
    (test/is (= "" (safe-subs "" 1)))))
 

