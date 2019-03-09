(ns simply-clojure.chapter11
  (:require [clojure.string :as string]
            [simply-clojure.helper :as helper]
))

;;  11.2  [8.12][4] When you teach a class, people will get distracted if you say "um" too many times. 
;; Write a count-ums that counts the number of times "um" appears in a sentence:
;; Write count-ums recursively.
;; remember, cond needs an extra parens, and a parens around else
;; there is no function "else"
;; this uses two recursive cases
;; this would be easy with filter
(defn count-ums [sentence]
  (loop [sent-work sentence
         um-count 0]
    (cond (string/blank? sent-work)
          um-count
          (= "um" (helper/first-string sent-work)) 
          (recur (helper/butfirst-string sent-work) (inc um-count))
          :else 
          (recur (helper/butfirst-string sent-work) um-count))))

;; Clojure says that to recur from a position other than the tail is an error.
;; But if it is an error, shouldn't Clojure catch it?
;; Can I do this with an "if"?
(defn if-count-ums [sentence]
  (loop [sent-work sentence
         um-count 0]
    (if (string/blank? sent-work)
      um-count
      (recur (helper/butfirst-string sent-work)
             (if (= "um" (helper/first-string sent-work))
               (inc um-count)
               um-count)))))

;; You could wrap the second part of recur there into a function to make it cleaner.
(defn calc-ums-for-recur [sent-work um-count]
  (if (= "um" (helper/first-string sent-work))
    (inc um-count)
    um-count))

(defn if-count-ums-with-func [sentence]
  (loop [sent-work sentence
         um-count 0]
    (if (string/blank? sent-work)
      um-count
      (recur (helper/butfirst-string sent-work) 
             (calc-ums-for-recur sent-work um-count)))))

;; 11.3  [8.13] Write a procedure phone-unspell that takes a spelled version of a phone number, 
;; such as POPCORN, and returns the real phone number, in this case 7672676. 
;; You will need a helper procedure that translates a single letter into a digit:
;; Here are some some special-case phone-unspell procedures:
;; Write phone-unspell recursively.
(defn phone-unspell [word]
  (loop [word-work word
         nums-work []]
    (cond (= 1 (count word-work)) (conj nums-work (helper/phone-letter word-work))
          :else (recur (helper/butfirst-string word-work) 
                       (conj nums-work 
                             (helper/phone-letter (helper/first-string word-work)))))))

;;  11.5  Write a procedure initials that takes a sentence as its argument and returns a sentence of the first letters in each of the sentence's words:
; > (initials '(if i needed someone))
; (I I N S)
;; Am i missing something? It seems like the same thing.
;; Unless I am also supposed to check for 0-length?
(defn initials [the-sent]
  (loop [sent-work the-sent
         init-hold ""]
    (cond (string/blank? sent-work) init-hold
          :else (recur (helper/butfirst-string sent-work) 
                       (str init-hold (helper/first-string (helper/first-string sent-work)))))))

;;  11.6  Write a procedure countdown that works like this:
(defn countdown [number]
  (loop [num-hold number
         sent-hold ""]
    (cond (= 0 num-hold) (str sent-hold "BLASTOFF!")
          :else (recur (dec num-hold) (str sent-hold (str num-hold) " ")))))

;;  11.7  Write a procedure copies that takes a number and a word as arguments and returns a sentence containing that many copies of the given word:
; > (copies 8 'spam)
(defn copies [number the-word]
  (loop [num-work number
         str-work ""]
    (cond (= 0 num-work) (helper/remove-ending-space-from-string str-work) 
          :else (recur (dec num-work) (str str-work the-word " ")))))





