;; Flatten a Sequence
;; Difficulty:Easy
;; Topics:seqs core-functions
;; Write a function which flattens a sequence.
;; Special Restrictions: flatten

(def __ (fn [x]
          (filter (complement sequential?)
                  (rest (tree-seq sequential? seq x)))) )

(= (__ '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))

(= (__ ["a" ["b"] "c"]) '("a" "b" "c"))

(= (__ '((((:a))))) '(:a))
