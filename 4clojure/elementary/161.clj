;; Subset and Superset
;; Difficulty:Elementary
;; Topics:set-theory
;; Set A is a subset of set B, or equivalently B is a superset of A, if A is "contained" inside B. A and B may coincide.

(def __ #{1 2})

(clojure.set/superset? __ #{2})

(clojure.set/subset? #{1} __)

(clojure.set/superset? __ #{1 2})

(clojure.set/subset? #{1 2} __)
