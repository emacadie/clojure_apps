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
;;6.5  Write a procedure european-time to convert a time from American AM/PM notation into European 24-hour notation. 
;; Also write american-time, which does the opposite:

;; Assume "number" is an integer, not a string
(defn to-american-time [number]
  (cond (not (helper/number-tween-inclusive number 0 24)) (str number)
        (= number 0) (str "12 AM")
        (helper/number-tween-inclusive number 1 11) (str number " AM")
        (= number 12) (str "12 PM")
        (helper/number-tween-inclusive number 13 23) (str (- number 12) " PM")
        :else (str "12 AM")))
(comment
(define (european-time time)
  ;; (display-all "first time: " (first time))
  (cond ((equal? (first time) "12")
         (if (equal? (second time) "am")
             (display-all "0")
             (display-all "12")))
        ((equal? (second time) "am") (display-all (first time)))
        ((equal? (second time) "pm") (display-all (+ (first time) 12)))))
)
(defn to-european-time [time]
  (cond (= (helper/second-string time) "AM") (str (helper/first-string time))
        (= (helper/second-string time) "PM") (str (helper/first-string time))
        :else time
)
)



