;; Maps: conj
;; Difficulty:Elementary
;; When operating on a map, the conj function returns a new map with one or more key-value pairs "added".

(def __ [:b 2])

(= {:a 1, :b 2, :c 3} (conj {:a 1} __ [:c 3]))
