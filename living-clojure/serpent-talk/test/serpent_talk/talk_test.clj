(ns serpent-talk.talk-test
  (:require [clojure.test :refer :all]
            [serpent-talk.talk :refer :all]))

(deftest test-serpent-talk
  (testing "Cries serpent! with a snake_case verson of the input"
    (is (= "Serpent! You said: hello_there"
           (serpent-talk "hello there")))))

(deftest a-test
  (testing "Default test."
    (is (= 1 1))))
