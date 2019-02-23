(ns simply-clojure.chapter09
  (:require [clojure.string :as str]
            [simply-clojure.helper :as helper]))

;; In each of the following exercises, write the procedure in terms of lambda and higher-order functions. 
;; Do not use named helper procedures. If you've read Part IV, don't use recursion, either.

;; 9.5  Write prepend-every:
(defn prepend-every [prestring the-sent]
  (helper/butlast-string (apply str (map #(str prestring %1 " ") the-sent))))

;; 9.6  Write a procedure sentence-version that takes a function f as its argument and returns a function g. 
;; f should take a single word as argument. g should take a sentence as argument and return the sentence formed by applying f to each word of that argument.
;; This is just a convoluted "every", right? Can I use "every"?

(defn sentence-version [the-func]
  (fn [g] (map the-func g)))

; 9.7  Write a procedure called letterwords that takes as its arguments a letter and a sentence. 
; It returns a sentence containing only those words from the argument sentence that contain the argument letter:
(defn letterwords [letter the-sent]
  (filter #(str/includes? %1 letter) (helper/split-string-to-words the-sent)))

; 9.8  Suppose we're writing a program to play hangman. 
; In this game one player has to guess a secret word chosen by the other player, one letter at a time. 
; You're going to write just one small part of this program: 
; a procedure that takes as arguments the secret word and the letters guessed so far, 
; returning the word in which the guessing progress is displayed 
;; by including all the guessed letters along with underscores for the not-yet-guessed ones:
; Hint: You'll find it helpful to use the following procedure that determines how to display a single letter: 
(defn hang-letter [letter guesses]
  (if (contains? (set (helper/split-word-to-letters guesses) ) letter)
    letter
    "_")) 

(defn hang [word guesses]
  (map #(hang-letter %1 guesses) (helper/split-word-to-letters word)))

;; 9.9  Write a procedure common-words that takes two sentences as arguments 
;; and returns a sentence containing only those words that appear both in the first sentence and in the second sentence.
;; keep and every? How heavy!!
;; from chapter 8:
;; For instance, the keep function takes a predicate and a sentence as arguments. 
;; It returns a sentence containing only the words of the argument sentence for which the predicate is true.
;; every takes a function, and a collection (or sentence)
(defn common-words [first-sen second-sen]
  (let [first-set (set (helper/split-string-to-words first-sen))]
    (filter #(contains? first-set %1) (helper/split-string-to-words second-sen))))

;; 9.10  In Chapter 2 we used a function called appearances that returns 
;; the number of times its first argument appears as a member of its second argument. 
;; Implement appearances.
;; this would be accumulate?
;; no this is keep again
;; Perhaps this could be a multi-method?
(defn appearances-string [part whole]
  (if (helper/string-is-word whole)
    (count (filter #(= part %1) (helper/split-word-to-letters whole)))
    (count (filter #(= part %1) (helper/split-string-to-words whole)))))

(defmulti my-appearances (fn [the-map] (:type the-map)))

(defmethod my-appearances :sentence
  [the-map]
  (count (filter #(= (:part the-map) %1) (helper/split-string-to-words (:whole the-map)))))

(defmethod my-appearances :word [the-map]
  (count (filter #(= (:part the-map) %1) (helper/split-word-to-letters (:whole the-map)))))

(defmethod my-appearances :vector [the-map]
  (count (filter #(= (:part the-map) %1) (:whole the-map))))




