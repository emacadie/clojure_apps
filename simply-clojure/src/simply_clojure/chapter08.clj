(ns simply-clojure.chapter08
  (:require [clojure.string :as str]
            [simply-clojure.helper :as helper]))
;; Higher order functions
;; every -> map
;; keep -> filter
;; accumulate -> reduce

;; 8.4  Write a procedure choose-beatles that takes a predicate function as its argument 
;; and returns a sentence of just those Beatles (John, Paul, George, and Ringo) that satisfy the predicate. 
;; so this sounds like filter/keep
(defn ends-vowel? [word]
  (helper/vowel? (helper/last-string word)))

(defn even-count? [word]
  (even? (count word)))

(defn choose-beatles [the-fn]
  (filter the-fn ["John" "Paul" "George" "Ringo"]))

(def sorted-beatles-set (sorted-set "John" "Paul" "George" "Ringo"))

(defn choose-beatles-set [the-fn]
  (filter the-fn sorted-beatles-set))
;; do again as a set

;; 8.5  Write a procedure transform-beatles that takes a procedure as an argument, 
;; applies it to each of the Beatles, and returns the results in a sentence:
;; this sounds like map, aka every
(defn amazify [name]
  (str "the amazing " name))

(defn transform-beatles [the-fn]
  (map the-fn ["John" "Paul" "George" "Ringo"]))

(defn transform-beatles-set [the-fn]
  (map the-fn sorted-beatles-set))

;; 8.6  When you're talking to someone over a noisy radio connection, 
;; you sometimes have to spell out a word in order to get the other person to understand it. 
;; But names of letters aren't that easy to understand either, 
;; so there's a standard code in which each letter is represented 
;; by a particular word that starts with the letter. For example, instead of "B" you say "bravo."
;; Write a procedure words that takes a word as its argument and returns a sentence of the names of the letters in the word:
; > (words 'cab)
;; (CHARLIE ALPHA BRAVO)
;; (You may make up your own names for the letters or look up the standard ones if you want.)
;; Hint: Start by writing a helper procedure that figures out the name for a single letter.
;; a map would come in handy here
;; to get the letters of a word:
;; (define (single letter) (word letter))
(def nato-letter-map {:a :alpha, :b :bravo, :c :charlie, :d :delta, :e :echo})

;; I could go to the end, but what's the point? 
(defn get-nato-letter [letter]
  ((keyword letter) nato-letter-map))
;; that slick string splitter from https://clojuredocs.org/clojure.string/split
(defn words [the-word]
  (map get-nato-letter (str/split the-word #"")))

;;  8.7  [14.5][9] Write a procedure letter-count that takes a sentence as its argument and returns the total number of letters in the sentence:
;; > (letter-count '(fixing a hole))
;; 11
;; sounds like reduce, aka accumulate
;; Just assume we get a string
(defn letter-count [the-sent]
  (reduce + (map count (str/split the-sent #" "))))



