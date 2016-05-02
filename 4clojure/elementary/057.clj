;; Simple Recursion
;; Difficulty:Elementary
;; A recursive function is a function which calls itself. This is one of the fundamental techniques used in functional programming.

(def __ '(5 4 3 2 1))

(= __ ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5))
