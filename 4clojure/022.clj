;; Count a Sequence
;; Difficulty:Easy
;; Topics:seqs core-functions
;; Write a function which returns the total number of elements in a sequence.
;; Special Restrictions: count

(def __ (fn [x]
          (inc (.lastIndexOf (seq x) (last (seq x))))))

(= (__ '(1 2 3 3 1)) 5)

(= (__ "Hello World") 11)

(= (__ [[1 2] [3 4] [5 6]]) 3)

(= (__ '(13)) 1)

(= (__ '(:a :b :c)) 3)
