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

(test/deftest test-butfirst-string
  (test/testing "Testing a string 'this is a sentence'"
    (test/is (= "is a sentence" (butfirst-string "this is a sentence"))))
  (test/testing "Testing string 'thisisalongword'"
    (test/is (= "hisisalongword" (butfirst-string "thisisalongword")))))

(test/deftest test-first-string
  (test/testing "Testing first with string 'test with a sentence'"
    (test/is (= "test" (first-string "test with a sentence"))))
  (test/testing "testing first with string 'testwithfakesentence"
    (test/is (= "t" (first-string "testwithfakesentence")))))

(test/deftest test-last-string
  (test/testing "Testing first with string 'test with a sentence'"
    (test/is (= "sentence" (last-string "test with a sentence"))))
  (test/testing "testing first with string 'testwithfakesentence"
    (test/is (= "e" (last-string "testwithfakesentence")))))

(test/deftest test-butlast-string
  (test/testing "Testing a string 'this is a sentence'"
    (test/is (= "this is a" (butlast-string "this is a sentence"))))
  (test/testing "Testing string 'thisisalongword'"
    (test/is (= "thisisalongwor" (butlast-string "thisisalongword")))))

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
    (test/is (= "AM" (second-string "8 AM")))
))

