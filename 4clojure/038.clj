;; Maximum value
;; Difficulty:Easy
;; Topics:core-functions
;; Write a function which takes a variable number of parameters and returns the maximum value.
;; Special Restrictions: max, max-key

(def __ (fn [& x] (last (sort x))) )

(= (__ 1 8 3 4) 8)

(= (__ 30 20) 30)

(= (__ 30 20) 30)
