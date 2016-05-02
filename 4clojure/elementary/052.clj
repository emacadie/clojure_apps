;; Intro to Destructuring
;; Difficulty:Elementary
;; Topics:destructuring
;; Let bindings and function parameter lists support destructuring.

;; does not work in repl as-is

(def __ (vector c e))

(= [2 4] (let [[a b c d e] [0 1 2 3 4]] __))
