(ns simply-clojure.chapter17
  (:require [clojure.string             :as string]
            [simply-clojure.helper      :as helper]
            [clojure.math.numeric-tower :as math-nt]))

;;  17.5  Here's a procedure that takes two numbers as arguments and returns whichever number is larger:
(defn max2 [a b] 
  (if (> b a) 
      b 
      a))
;; Use max2 to implement max, 
;; a procedure that takes one or more numeric arguments and returns the largest of them. 

;; Frankly, I think I like "cond" better in Scheme/Racket.
;; But I guess you can only change what brackets do just once
(defn my-max [number & rest-nums] 
  (cond (nil? rest-nums) number
        (= (count rest-nums) 1)  (max2 number (first rest-nums))
        (= (max2 number (first rest-nums)) number) (apply my-max (cons number (rest rest-nums)))
        (= (max2 number (first rest-nums)) (first rest-nums)) (apply my-max rest-nums) ;; okay
        :else number))

;; could this be a loop-recur?
(defn my-max-loop-recur [the-num & rst-nums]
  (loop [number the-num
         rest-nums rst-nums]
    (cond (nil? rest-nums) number
          (= (count rest-nums) 1)  (max2 number (first rest-nums))
          (= (max2 number (first rest-nums)) number) (recur number (rest rest-nums))
          (= (max2 number (first rest-nums)) (first rest-nums)) (recur (first rest-nums) 
                                                                       (rest rest-nums)) ;; okay
          :else number)))

;; Winnin' with the 'duce
;; conj adds the second arg to the FRONT of a list or seq, but the rear of a vector
;; I know people rave about Clojure's abstractions, but maybe vectors should have a separate function for adding from lists and seqs
;; Maybe there is and it just escapes me now. I will look later.
;; I suppose I could make aliases or something.
(defn my-max-reduce [number & rest-nums]
   (reduce max2 0 (conj rest-nums number)))

(defn hello [greeting & who]
  (println "is who a list? " (list? who))
  (println "is who a seq?" (seq? who))
  (println greeting who))


