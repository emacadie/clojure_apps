(ns simply-clojure.chapter14-numbers
  (:require [clojure.string :as string]
            [simply-clojure.helper :as helper]))



(def number-table ["" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])

(def power-1-table ["zero" "ten" "twenty" "thirty" "forty" "fifty" "sixty" "seventy" "eighty" "ninety"])

(defn get-new-teen-table [n]
  (cond (empty? n) "ten"
        (= n "one") "eleven"
        (= n "two") "twelve"
        (= n "three") "thirteen"
        (= n "five") "fifteen"
        (= n "eight") "eighteen"
        :else (str n "teen")))

(defn exponent-helper [num group]
  (cond (empty? num) ""
        (= group 1)  (str num)
        (= group 2)  (str num " thousand")
        (= group 3)  (str num " million")
        (= group 4)  (str num " billion")
        (= group 5)  (str num " trillion")
        (= group 6)  (str num " quadrillion") 
        (= group 7)  (str num " quintillion")
        (= group 8)  (str num " sextillion") 
        (= group 9)  (str num " septillion") 
        (= group 10) (str num " octillion")
        (= group 11) (str num " nonillion")
        (= group 12) (str num " decillion")))

(defn remove-leading-zeros [num] 
  (loop [num-work num
         outp ""]
    (cond (empty? num-work) outp
          (= "0" (helper/first-word num-work)) (recur (helper/butfirst-word num-work) outp)
          :else num-work)))

(defn get-last-3 [the-num]
  (if (< (count the-num) 3)
    the-num
    (str (helper/last-word (helper/butlast-word (helper/butlast-word the-num)))
        (helper/last-word (helper/butlast-word the-num))
        (helper/last-word the-num))))

(defn break-string-to-threes [num-string] ; num-list
  (loop [num-work num-string
         outp []]
    (cond (<= (count num-work) 3) (vec (reverse (conj outp num-work))) ; tupelo can do that for you
          :else (recur (helper/butlast-word (helper/butlast-word (helper/butlast-word num-work)))
                       (conj outp (remove-leading-zeros (str (get-last-3 num-work))))))))

(defn nth-string-index 
  ([coll str-index]
   (nth coll (helper/string-to-int str-index) "Not found")) 
  ([coll str-index default]
   (nth coll (helper/string-to-int str-index) default)))

(defn num-name2-work [num] ; num-work output depth
  (println "in num-name2-work with num: " num)
  (loop [; num-work (helper/split-word-to-letters (Integer/toString num)) ;; actual nums, but should they be strings?
         num-work (helper/split-word-to-letters num)
         output ""
         depth 1]
    (cond (empty? num-work) output        ;; skip ending zero
        (= "0" (last num-work)) (recur (helper/butlast-vec num-work) output (inc depth))
        (= depth 1) (recur (helper/butlast-vec num-work) 
                           (str (nth-string-index number-table (last num-work))) 
                           (inc depth))
        ; new teens
        (and (= depth 2) (= "1" (last num-work))) (recur (helper/butlast-vec num-work) 
                                                         (get-new-teen-table output) 
                                                         (inc depth))
        (= depth 2) (recur (helper/butlast-vec num-work) 
                             (if (empty? output)
                               (str (nth-string-index power-1-table (last num-work)) output) 
                               (str (nth-string-index power-1-table (last num-work)) "-" output))
                           (inc depth))
        (= depth 3) (recur (helper/butlast-vec num-work) 
                           (str (nth-string-index number-table (last num-work)) " hundred " output) 
                           (inc depth)))))

(comment
; "item" in Simply Scheme starts counting a list at "1", not "0" like C

(define (grand-num-name-caller raw-num)
  (keep (lambda (x) (not (empty? x)))
(grand-num-name-worker (do-string-break (number->string raw-num) '()) "" 1)))
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
)

(defn grand-num-name-worker [the-num]; the-num outp group
  (loop [num-work (break-string-to-threes (Integer/toString the-num))
         outp ""
         group 1
         ]
    (println "In loop for grand-num-name-worker: num-work: ", num-work, ", outp: ", outp, ", group: " group)
    (cond (empty? num-work) outp
          :else (do
                  (println "In else in grand-num-name-worker")
                  (recur (helper/butlast-vec num-work)
                         (str  (exponent-helper (num-name2-work (last num-work)) 
                                                group) 
                               outp) 
                         (inc group))))
)
  )

