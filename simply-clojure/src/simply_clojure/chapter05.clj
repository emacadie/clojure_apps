(ns simply-clojure.chapter05
  (:require ; [clojure.string :as str]
            [simply-clojure.helper :as helper]))

;; 5.14 Write a procedure third that selects the third letter of a word (or the third word of a sentence).
;; or use item



(defn third [x]
  (if (helper/string-is-word x)
    (helper/first-word (helper/butfirst-word (helper/butfirst-word x)))
    (helper/first-string (helper/butfirst-string (helper/butfirst-string x)))))

;; 5.15 Write a procedure first-two that takes a word as its argument, 
;; returning a two-letter word containing the first two letters of the argument.
;; > (first-two 'ambulatory)
;; AM

;; This is intended for words. It does not check if they are words or sentences.
(defn first-two [the-word]
  (str (helper/first-word the-word) 
       (helper/first-word 
        (helper/butfirst-word the-word))))

;; 5.16 Write a procedure two-first that takes two words as arguments, 
;; returning a two-letter word containing the first letters of the two arguments.
;; > (two-first 'brian 'epstein)
;; BE
;; Now write a procedure two-first-sent that takes a two-word sentence as argument, 
;; returning a two-letter word containing the first letters of the two words.
;; > (two-first-sent '(brian epstein))
;; BE
(defn two-first [word1 word2]
  (str (helper/first-word word1) (helper/first-word word2)))

(defn two-first-sent [the-sent]
  (str (helper/first-word (helper/first-string the-sent))
       (helper/first-word (subs the-sent (inc (clojure.string/index-of the-sent " "))))))

;; 5.17 Write a procedure knight that takes a person's name as its argument and returns the name with "Sir" in front of it.
;; > (knight '(david wessel))
;; (SIR DAVID WESSEL)
(defn knight [arg-name] 
  (str "Sir " arg-name))

;; 5.19 Write a procedure insert-and that takes a sentence of items and returns a new sentence with an "and" in the right place:
;; > (insert-and '(john bill wayne fred joey))
;; (JOHN BILL WAYNE FRED AND JOEY)
(defn insert-and [the-sent]
  (str (helper/butlast-string the-sent) " and " (helper/last-string the-sent)))

;; 5.20 Define a procedure to find somebody's middle names:
;; > (middle-names '(james paul mccartney))
;; (PAUL)
;; > (middle-names '(john ronald raoul tolkien))
;; (RONALD RAOUL)
;; > (middle-names '(bugs bunny))
;; ()
;; > (middle-names '(peter blair denis bernard noone))
;; (BLAIR DENIS BERNARD)
(comment
  (define (middle-names name-arg)
  (se (butfirst (butlast name-arg))))
)
(defn middle-names [the-name]
  (cond (helper/string-is-word the-name) ""
        (helper/string-is-word (helper/butfirst-string the-name)) ""
        :else (str (helper/butfirst-string (helper/butlast-string the-name)))))

;; 5.21  Write a procedure query that turns a statement into a question by swapping the first two words and adding a question mark to the last word:
;; > (query '(you are experienced))
;; (ARE YOU EXPERIENCED?)

;; > (query '(i should have known better))
;; (SHOULD I HAVE KNOWN BETTER?)
;; Ignoring case
(defn query [the-sent]
  (if (helper/string-is-word the-sent)
    the-sent
    (str
     (helper/second-string the-sent) ; second
     " "
     (helper/first-string the-sent)    ; first
     " "
     (helper/butfirst-string (helper/butfirst-string the-sent)) ; rest
     "?")))

