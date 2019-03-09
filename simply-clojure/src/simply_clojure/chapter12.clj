(ns simply-clojure.chapter12
  (:require [clojure.string :as string]
            [simply-clojure.helper :as helper]
            [simply-clojure.chapter08 :as ch08]
))

;;  12.4  Here's the definition of a function f:
;; math display
;; Implement f as a Scheme procedure. What does f do? 
;; it reverses a list, I think
(defn ffunc [the-sent]
  (loop [sent-work the-sent
         new-sent ""]
    (if (string/blank? sent-work) 
      new-sent
      (recur (helper/butfirst-string sent-work) (str new-sent " " (helper/first-string sent-work))))))

;; 12.5  [8.8] Write an exaggerate procedure which exaggerates sentences:
;; > (exaggerate '(i ate 3 potstickers))
; (I ATE 6 POTSTICKERS)
;; > (exaggerate '(the chow fun is good here))
;; (THE CHOW FUN IS GREAT HERE)
;; It should double all the numbers in the sentence, and it should replace "good" with "great," "bad" with "terrible," and anything else you can think of.
(defn exaggerate12 [the-sent]
  (loop [sent-work the-sent
         new-sent ""]
    (if (string/blank? sent-work)
      new-sent
      (recur (helper/butfirst-string sent-work) 
             (str (ch08/do-great-stuff 
                   (helper/first-string sent-work)))))))

;; 12.6  [8.11] Write a GPA procedure. It should take a sentence of grades as its argument and return the corresponding grade point average:
;; > (gpa '(A A+ B+ B))
;; 3.67
;; Hint: write a helper procedure base-grade that takes a grade as argument and returns 0, 1, 2, 3, or 4, 
;; and another helper procedure grade-modifier that returns −.33, 0, or .33, depending on whether the grade has a minus, a plus, or neither.
;; how to keep the number in a recursive function that takes one arg?
;; no idea right now; just make another function
;; so it rounds a bit
;; the other two solutions did the same thing with another function in there
(defn get-grade-total12 [grades]
  (loop [grades-work grades
         total-work 0]
    (if (= 0 (count grades-work))
      total-work
      (recur (rest grades-work) 
             (+ total-work
                (ch08/base-grade (first grades-work)) 
                (ch08/modify-grade (first grades-work)))))))

(defn gpa12 [grades]
  (/ (get-grade-total12 grades) (count grades)))

;; 12.7  Write a procedure spell-number that spells out the digits of a number:
;; > (spell-number 1971)
;; (ONE NINE SEVEN ONE)
;; Use this helper procedure:
(defn spell-digit [digit]
  (nth ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine" ] digit))

(defn spell-number [num]
  (loop [nums-work (helper/split-word-to-letters (str num))
         str-work ""]
    (if (= 0 (count nums-work))
      str-work
      (recur (rest nums-work) 
             (str str-work " " (spell-digit (Integer/parseInt (first nums-work))))))))

;; 12.8  Write a procedure numbers that takes a sentence as its argument and returns another sentence containing only the numbers in the argument:
;; > (numbers '(76 trombones and 110 cornets))
;; (76 110)
;; this is filter/keep
(defn numbers [the-sent]
  (loop [sent-work the-sent
         nums-work ""]
    (if (string/blank? sent-work)
      (helper/remove-ending-space-from-string nums-work)
      (recur (helper/butfirst-string sent-work)
             (str nums-work 
                  (when (helper/is-string-number? (helper/first-string sent-work)) 
                    (str (helper/first-string sent-work) 
                         " ")))))))

;; 12.12  Write a procedure arabic which converts Roman numerals into Arabic numerals:
; > (arabic 'MCMLXXI)
;1971
;> (arabic 'MLXVI)
;1066
;; You will probably find the roman-value procedure from Chapter 6 helpful. 
;; Don't forget that a letter can reduce the overall value if the letter that comes after it has a larger value, such as the C in MCM.
(defn roman-value [letter]
  (case letter
    "I" 1
    "V" 5
    "X" 10
    "L" 50
    "C" 100
    "D" 500
    "M" 1000
    0))


(def first-roman-value  (comp roman-value helper/first-string))
(def second-roman-value (comp roman-value helper/first-string helper/butfirst-string))

(defn first-less-than-second [nums]
  (cond (= (count nums) 1) false
        (< (first-roman-value nums) (second-roman-value nums)) true
        :else false))

(defn subtract-first-from-second [nums]
  (- (second-roman-value nums) (first-roman-value nums)))

(comment
(define (arabic the-nums)
  (cond ((empty? the-nums) '())
        ((equal? (count the-nums) 1) (roman-value the-nums))
        ((first-less-than-second the-nums) (+ (subtract-first-from-second the-nums) (arabic (butfirst (butfirst the-nums)))))
        ((not (first-less-than-second the-nums)) (+ (roman-value (first the-nums)) (arabic (butfirst the-nums))))))
)
(defn arabic [the-nums]
  (cond (string/blank? the-nums) ""
        (= 1 (count the-nums)) (roman-value the-nums)
        (first-less-than-second the-nums) (+ (subtract-first-from-second the-nums) (arabic (helper/butfirst-string (helper/butfirst-string the-nums))))
        (not (first-less-than-second the-nums)) (+ (first-roman-value the-nums) (arabic (helper/butfirst-string the-nums)))
)
)




