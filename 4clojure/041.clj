;; Drop Every Nth Item
;; Difficulty:Easy
;; Topics:seqs
;; Write a function which drops every Nth item from a sequence.

(def __
(fn [coll n]
      (mapcat #(take (- n 1) %) (partition-all n coll))))

(= (__ [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])

(= (__ [:a :b :c :d :e :f] 2) [:a :c :e])

(= (__ [1 2 3 4 5 6] 4) [1 2 3 5 6])
