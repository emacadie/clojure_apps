;; Nth Element
;; Difficulty:Easy
;; Topics:seqs core-functions
;; Write a function which returns the Nth element from a sequence.
;; Special Restrictions: nth
;; The people I follow have better solutions
(def __ (fn [col indx]
          (get (apply array-map (interleave (range (count col)) col)) indx)))

(= (__ '(4 5 6 7) 2) 6)

(= (__ [:a :b :c] 0) :a)

(= (__ [1 2 3 4] 1) 2)

(= (__ '([1 2] [3 4] [5 6]) 2) [5 6])


