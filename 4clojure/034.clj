;; Implement range
;; Difficulty:Easy
;; Topics:seqs core-functions
;; Write a function which creates a list of all integers in a given range.
;; Special Restrictions: range

(def __ (fn [x y]
          (take (- y x) (iterate inc x))) )

(= (__ 1 4) '(1 2 3))

(= (__ -2 2) '(-2 -1 0 1))

(= (__ 5 8) '(5 6 7))
