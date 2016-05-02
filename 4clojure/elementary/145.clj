;; For the win
;; Difficulty:Elementary
;; Topics:core-functions seqs
;; Clojure's for macro is a tremendously versatile mechanism for producing a sequence based on some other sequence(s).
;; It can take some time to understand how to use it properly, but that investment will be paid back with clear, concise sequence-wrangling later.
;; With that in mind, read over these for expressions and try to see how each of them produces the same result.


(def __ '(1 5 9 13 17 21 25 29 33 37))

(= __ (for [x (range 40)
            :when (= 1 (rem x 4))]
        x))

(= __ (for [x (iterate #(+ 4 %) 0)
            :let [z (inc x)]
            :while (< z 40)]
        z))

(= __ (for [[x y] (partition 2 (range 20))]
        (+ x y)))
