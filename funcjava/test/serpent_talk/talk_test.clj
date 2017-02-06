(ns serpent-talk.talk-test
  (:require [clojure.test :refer :all]
            [serpent-talk.talk :refer :all]))

(deftest test-serpent-talk
  (testing "Cries serpent! with a snake_case verson of the input"
    (println "in test-serpent-talk")
    (is (= "Serpent! You said: hello_there"
           (serpent-talk "hello there")))))

(deftest a-test
  (testing "Default test."
    (println "in a-test")
    (is (= 1 1))))
