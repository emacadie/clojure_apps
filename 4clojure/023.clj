;; Reverse a Sequence
;; Difficulty:Easy
;; Topics:seqs core-functions
;; Write a function which reverses a sequence.
;; Special Restrictions: reverse, rseq
;; other solutions are better

(def __ (fn [items]
          (loop [itemsx items nx (count items) other '()]
            (if (= nx 0)
              (if (vector? items)
                (into [] other)
                other)
              (recur (take (- nx 1) itemsx) (dec nx) (concat other (list (last itemsx)) ) )))))

(= (__ [1 2 3 4 5]) [5 4 3 2 1])

(= (__ (sorted-set 5 7 2 7)) '(7 5 2))

(= (__ [[1 2][3 4][5 6]]) [[5 6][3 4][1 2]])

