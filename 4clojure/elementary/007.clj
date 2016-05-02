;; Vectors: conj
;; Difficulty:Elementary
;; When operating on a Vector, the conj function will return a new vector with one or more items "added" to the end.

(def __ [1 2 3 4])

(= __ (conj [1 2 3] 4))

(= __ (conj [1 2] 3 4))

