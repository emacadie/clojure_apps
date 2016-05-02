;; Last Element
;; Difficulty:Easy
;; Topics:seqs core-functions
;; Write a function which returns the last element in a sequence.
;; Special Restrictions: last

(def __ (fn [x] (first (reverse x))))

(= (__ [1 2 3 4 5]) 5)

(= (__ '(5 4 3)) 3)

(= (__ ["b" "c" "d"]) "d")

