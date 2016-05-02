;; Intro to Sequences
;; Difficulty:Elementary
;; All Clojure collections support sequencing. You can operate on sequences with functions like first, second, and last.

(def __ 3)

(= __ (first '(3 2 1)))

(= __ (second [2 3 4]))

(= __ (last (list 1 2 3)))
