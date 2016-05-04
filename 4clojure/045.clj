;; Intro to Iterate
;; Difficulty:Easy
;; Topics:seqs
;; The iterate function can be used to produce an infinite lazy sequence.

(def __ [1 4 7 10 13])

(= __ (take 5 (iterate #(+ 3 %) 1)))

