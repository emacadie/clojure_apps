(ns simply-clojure.chapter05-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter05 :refer :all]))

(test/deftest test-third
  (test/testing "Testing third"
    (test/is (= "George" (third "John Paul George Ringo")))
    (test/is (= "i" (third "thisisalongword")))))

(test/deftest test-first-two
  (test/testing "Testing first-two"
    (test/is (= "he" (first-two "hello")))))

(test/deftest test-two-first
  (test/testing "Testing two-first and two-first-sent"
    (test/is (= "GO" (two-first "Good" "One")))
    (test/is (= "GO" (two-first-sent "Good One")))))

(test/deftest test-knight
  (test/testing "Test Knight"
    (test/is (= "Sir Crap Bag" (knight "Crap Bag")))))

(test/deftest test-insert-and
  (test/testing "Test insert-and"
    (test/is (= "John Bill Wayne Fred and Joey" (insert-and "John Bill Wayne Fred Joey")))))

(test/deftest test-middle-names
  (test/testing "Test middle-names"
    (test/is (= "Paul" (middle-names "James Paul McCartney")))
    (test/is (= "Ronald Raoul" (middle-names "John Ronald Raoul Tolkein")))
    (test/is (= "" (middle-names "Bugs Bunny")))
    (test/is (= "Blair Denis Bernard" (middle-names "Peter Blair Denis Bernard Noone")))))

(test/deftest test-query
  (test/testing "Testing query"
    (test/is (= "should I have known better?" (query "I should have known better")))
    (test/is (= "are you experienced?" (query "you are experienced")))))

