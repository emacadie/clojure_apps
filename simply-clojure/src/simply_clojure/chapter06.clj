(ns simply-clojure.chapter06
  (:require ; [clojure.string :as str]
            [simply-clojure.helper :as helper]))

;; 6.3  Rewrite the following procedure using a cond instead of the ifs:
(defn sign-number [the-num]
  (cond (pos? the-num) :positive
        (neg? the-num) :negative
        :else :zero))

;; 6.4  Rewrite the following procedure using an if instead of the cond:
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
        (and (string? arg) (not (helper/string-is-word arg))) :sentence
        (and (string? arg) (helper/string-is-word arg)) :word
        :else :nothing))

;; 6.8  Write a procedure indef-article that works like this:
;; ** examples omitted **
;; I only deal w/ words, not sentences
(defn starts-with-vowel [the-string]
  (cond (not (string? the-string)) false
        (helper/vowel? (subs the-string 0 1)) true
        :else false))

(defn indef-article [arg]
  (cond (not (string? arg)) arg 
        (and (helper/string-is-word arg) (starts-with-vowel arg)) (str "an " arg)
        (helper/string-is-word arg) (str "a " arg)
        :else arg))

;; skipping to 6.12 since that deals with plural
;; 6.12  Make plural handle correctly words that end in y but have a vowel before the y, such as boy.
;; Then teach it about words that end in x (box). What other special cases can you find?
;; load vowel? from above
(defn plural-y [word]
  (cond (helper/vowel? (helper/last-string (helper/butlast-string word))) (str word "s")
        :else (str (helper/butlast-string word) "ies")))

(defn plural [word]
  (cond (not (string? word)) word
        (not (helper/string-is-word word)) word
        (= (helper/last-string word) "y") (plural-y word)
        (= (helper/last-string word) "x") (str word "es")
        :else (str word "s")))

;; 6.9  Sometimes you must choose the singular or the plural of a word: 1 book but 2 books.
;; Write a procedure thismany that takes two arguments, a number and a singular noun, and combines them appropriately:
;; load plural before loading this
(defn thismany [num thing]
  (cond (not (number? num)) (str "arg ", num, "is not a number")
        (= num 1) (str "1 " thing)
        :else (str num " " (plural thing))))

;; 6.10  Write a procedure sort2 that takes as its argument a sentence containing two numbers. 
;; It should return a sentence containing the same two numbers, but in ascending order:
(defn sort2 [nums]
"nums is a vector of two numbers"
  (cond (or (not (number? (get nums 0))) (not (number? (get nums 1)))) (str "one of your nums is not a number: " nums)
        (not (= (count nums) 2)) (str "You must send a vec of two nums")
        (< (get nums 1) (get nums 0)) (vector (get nums 1) (get nums 2))
        :else nums))

(defn sort2-dest [[num-a num-b]]
"Sending a vector of two numbers, with destructuring"
  (cond (or (not (number? num-a)) (not (number? num-b))) (str "one of your nums is not a number: " num-a ", " num-b)
        (< num-b num-a) [num-b num-a] ; You could also: (vector num-b num-a)
        :else (vector num-a num-b)))

;; 6.11 Write a predicate valid-date? that takes three numbers as arguments, representing a month, a day of the month, and a year.
;; Your procedure should return #t if the numbers represent a valid date (e.g., it isn't the 31st of September).
;; February has 29 days if the year is divisible by 4, except that if the year is divisible by 100 it must also be divisible by 400. 
;; uses divisible? defined above
(defn valid-month? [month day]
  (cond (and (contains? #{4 6 9 11} month) (<= day 30)) true
        (and (contains? #{1 3 5 7 8 10 12} month) (<= day 31) ) true
        :else false))

(defn valid-feb-date? [day year]
  (cond (<= day 28) true
        (and (= day 29) (= 0 (mod year 100)) (= 0 (mod year 400))) true
        (and (= day 29) (= 0 (mod year 100)) (not (= 0 (mod year 400)))) false
        (and (= day 29) (= 0 (mod year 4))) true
        :else false))

(defn valid-date? [month day year]
  (cond (or (not (number? month)) (not (number? day)) (not (number? year))) false
        (not (helper/number-tween-inclusive day 1 31)) false
        (not (helper/number-tween-inclusive month 1 12)) false
        (and (not (= month 2)) (valid-month? month day)) true
        (and (= month 2) (valid-feb-date? day year)) true
        :else false))

;; 6.14  Write a procedure describe-time that takes a number of seconds as its argument and returns a more useful description of that amount of time:

;; it seems to have an issue with large numbers
(defn get-time-number 
  ([time num-sec round-factor]
  (+ (quot time num-sec) (helper/round (/ (rem time num-sec) num-sec) round-factor)))
  ([time num-sec]
   (get-time-number time num-sec 4)))

(defn describe-time [time]
  (cond (not (number? time)) time
        (not (pos? time)) time
        (>= time 3155760000) (str (get-time-number time 3155760000 15) " centuries")
        (>= time 315576000)  (str (get-time-number time 315576000 15) " decades")
        (>= time 31557600)   (str (get-time-number time 31557600 15) " years")
        (>= time 86400) (str (get-time-number time 86400 10 " days")) 
        (>= time 3600) (str (get-time-number time 3600 10) " hours") 
        (>= time 60) (str (get-time-number time 60) " minutes")
        :else (str time " seconds")))

