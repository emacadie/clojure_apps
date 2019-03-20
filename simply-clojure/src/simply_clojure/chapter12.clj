(ns simply-clojure.chapter12
  (:require [clojure.string           :as string]
            [simply-clojure.helper    :as helper]
            [simply-clojure.chapter08 :as ch08]))

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


(def first-roman-value  (comp roman-value helper/first-word))
(def second-roman-value (comp roman-value helper/first-word helper/butfirst-word))

(defn first-less-than-second [nums]
  (cond (= (count nums) 1) false
        (string/blank? nums) false
        (< (first-roman-value nums) (second-roman-value nums)) true
        :else false))

(defn subtract-first-from-second [nums]
  (- (second-roman-value nums) (first-roman-value nums)))

(defn arabic [the-nums]
  (cond (string/blank? the-nums) ""
        (= 1 (count the-nums)) (roman-value the-nums)
        (first-less-than-second the-nums) (+ (subtract-first-from-second the-nums) 
                                             (arabic (helper/butfirst-two-word the-nums)))
        (not (first-less-than-second the-nums)) (+ (first-roman-value the-nums) 
                                                   (arabic (helper/butfirst-word the-nums)))))

;; In order to go through the string with loop-recur AND have an output,
;; I would need two data-structures
;; But I suppose for a lot of data, this might be a better way
(defn arabic-recur  [the-nums]
  (loop [nums-string the-nums
         output-work 0]
    (cond (and (string/blank? nums-string) (= output-work 0)) 0
          (string/blank? nums-string) output-work
          (first-less-than-second nums-string) (recur (helper/butfirst-two-word nums-string)
                                                      (+ (subtract-first-from-second nums-string) output-work)) 
          (not (first-less-than-second nums-string))(recur (helper/butfirst-word nums-string)
                                                           (+ (first-roman-value nums-string) output-work)))))

;;  12.13  Write a new version of the describe-time procedure from Exercise . Instead of returning a decimal number, it should behave like this:

; Can you make the program smart about saying 1 CENTURY instead of 1 CENTURIES? 
(def CENTURY 3155760000)
(def YEAR 31557600)
(def DAY 86400)
(def WEEK (* 7 DAY))
(def HOUR 3600)
(def MINUTE 60)

(defn get-time-measure [time]
  (cond (>= time (* 2 CENTURY)) " CENTURIES "
        (>= time CENTURY)       " CENTURY "
        (>= time (* 2 YEAR))    " YEARS "
        (>= time YEAR)          " YEAR "
        (>= time (* 2 WEEK))    " WEEKS "
        (>= time WEEK)          " WEEK "
        (>= time (* 2 DAY))     " DAYS "
        (>= time DAY)           " DAY "
        (>= time (* 2 HOUR))    " HOURS "
        (>= time HOUR)          " HOUR "
        (>= time (* 2 MINUTE))  " MINUTES "
        (>= time MINUTE)        " MINUTE "
        (= time 1)              " SECOND"
        :else                   " SECONDS"))

; quotient is like floor
(defn get-time-floor [time]
  (cond (>= time CENTURY) (quot time CENTURY) 
        (>= time YEAR)    (quot time YEAR) 
        (>= time WEEK)    (quot time WEEK)
        (>= time DAY)     (quot time DAY)
        (>= time HOUR)    (quot time HOUR)
        (>= time )        (quot time MINUTE)
        :else :seconds))

(defn get-time-multiplier [time]
  (cond (>= time CENTURY) CENTURY
        (>= time YEAR)    YEAR
        (>= time WEEK)    WEEK
        (>= time DAY)     DAY
        (>= time HOUR)    HOUR
        (>= time MINUTE)  MINUTE
        ;:else :SECONDS
))

(defn describe-time12 [time]
  
  (cond (not (number? time)) time
        (= time 0) (str time " SECONDS")
        (not (pos? time)) time
        (= time 1) (str time " SECOND")
        (< time MINUTE)  (str time " SECONDS")
        :else (str (get-time-floor time) 
                   (get-time-measure time) 
                   (describe-time12 (- time 
                                       (* (get-time-floor time) 
                                          (get-time-multiplier time)))))))


(defn describe-time12-r [time]
  (loop [time-work time
         outp ""]
    (cond (or (not (number? time-work)) (neg? time-work)) time-work
        (= time-work 0) (str outp time-work " SECONDS")
        (= time-work 1) (str outp time-work " SECOND")
        (< time-work MINUTE)  (str outp time-work " SECONDS")
        :else (recur (- time-work (* (get-time-floor time-work) (get-time-multiplier time-work)))
                     (str outp 
                          (get-time-floor time-work) 
                          (get-time-measure time-work))))))


;; Most of what follows is from Simply Scheme repo:
;; I did not do tail recursion. But then again, the authors did not mention tail-recursion.
;; I don't think I got it when I did the other Scheme tutorials.
;; But I was able to use loop-recur in Clojure, and I think that is similar to tail-recursion:
;; from https://clojure.org/reference/special_forms#recur
;; "recur in other than a tail position is an error."
;; The page also says it is not tail-call optimized, and the only thing to look out for is that you are not using an infinite sequence:
;; "There is no tail-call optimization and the use of self-calls for looping of unknown bounds is discouraged."
;; I wonder if there is a library that can check your recursive calls.

;; In the Shido tutorial, Shido will have a wrapper procedure that calls the tail-recursive procedure.
;; Also, look here: https://stackoverflow.com/questions/13664639/tail-recursive-functions-in-scheme
;; https://stackoverflow.com/questions/33923/what-is-tail-recursion

;; A few bits of advice from the text:
;; If your function is supposed to return a number, it must return a number all the time, even in the base case. 
;; You can use this idea to help you check the correctness of the base case expression.
;; One issue is that Scheme and Clojure are not statically typed
;; If your base case doesn't make sense in its own right, it probably means that you're trying to compensate for a mistake in the recursive case. 


