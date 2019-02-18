(ns simply-clojure.chapter06
  (:require ; [clojure.string :as str]
            [simply-clojure.helper :as helper]))

;; 6.3  Rewrite the following procedure using a cond instead of the ifs:
(comment
  (define (sign number)
  (if (< number 0)
      'negative
      (if (= number 0)
            'zero
'positive)))
)
(defn sign-number [the-num]
  (cond (pos? the-num) :positive
        (neg? the-num) :negative
        :else :zero))

;; 6.4  Rewrite the following procedure using an if instead of the cond:
(comment
  (define (utensil meal)
  (cond ((equal? meal 'chinese) 'chopsticks)
        (else 'fork)))
)
(defn if-utensil [meal]
  (if (= meal :Chinese)
    :chopsticks
    :fork))

;; Real Exercises
;; Note: Writing helper procedures may be useful in solving some of these problems.
;; 6.5  Write a procedure european-time to convert a time from American AM/PM notation into European 24-hour notation. 
;; Also write american-time, which does the opposite:

;; Assume "number" is an integer, not a string
(defn to-american-time [number]
  (cond (not (helper/number-tween-inclusive number 0 24)) (str number)
        (= number 0) (str "12 AM")
        (helper/number-tween-inclusive number 1 11) (str number " AM")
        (= number 12) (str "12 PM")
        (helper/number-tween-inclusive number 13 23) (str (- number 12) " PM")
        :else (str "12 AM")))

(defn to-european-time [time]
  (cond (and (= (helper/first-string time) "12") (= (helper/second-string time) "AM")) (str "0") 
        (and (= (helper/first-string time) "12") (= (helper/second-string time) "PM")) (str "12") 
        (= (helper/second-string time) "AM") (str (helper/first-string time))
        ;; I am not clear why it only worked when the call to integer? was placed second
        (and (= (helper/second-string time) "PM") (integer? (Integer/parseInt (helper/first-string time)))) 
          (str (+ (Integer/parseInt (helper/first-string time)) 12))
        :else time))

;; 6.6  Write a predicate teen? that returns true if its argument is between 13 and 19.
(defn teen? [number]
  (cond (not (integer? number)) false
        (helper/number-tween-inclusive number 13 19) true
        :else false))

;; 6.7  Write a procedure type-of that takes anything as its argument and returns one of the words word, sentence, number, or boolean:
;; boolean? is in 1.9
(defn type-of [arg]
  (cond ; (boolean? arg) :boolean
        (number? arg) :number
        (and (string? arg) (not (helper/string-is-word))) :sentence
        (and (string? arg) (helper/string-is-word)) :word
        :else :nothing))

;; 6.8  Write a procedure indef-article that works like this:
;; ** examples omitted **
;; I only deal w/ words, not sentences
;; there is a better vowel up above
(comment
(define (vowel? arg)
  (cond ((or (equal? arg "a") (equal? arg "e") (equal? arg "i") (equal? arg "o") (equal? arg "u")) #t)
        ((or (equal? arg "a") (equal? arg "E") (equal? arg "I") (equal? arg "O") (equal? arg "U")) #t)
      (else #f)))
      
(define (indef-article arg)
  (cond ((and (word? arg) (vowel? (first arg))) (display-all "an " arg))
        ((word? arg) (display-all "a " arg))
(else arg)))
)

