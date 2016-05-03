;; Factorial Fun
;; Difficulty:Easy
;; Topics:math
;; Write a function which calculates factorials.

(def __ (fn [x]
          (reduce * (range 1 (inc x)))) )

(= (__ 1) 1)

(= (__ 3) 6)

(= (__ 5) 120)

(= (__ 8) 40320)
