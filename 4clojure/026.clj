;; Fibonacci Sequence
;; Difficulty:Easy
;; Topics:Fibonacci seqs
;; Write a function which returns the first X fibonacci numbers.
;; Not the best solution

(def __ (fn [x]
          (let [countx x]
            (case countx
              0 '()
              1 '(1)
              2 '(1 1)
              (loop [count-y 2 fib-list [1 1]]
                (if (= count-y x)
                  (into () (reverse fib-list))
                  (recur (inc count-y) (conj fib-list (+ (last fib-list) (second (reverse fib-list)))) )) )))))

(= (__ 3) '(1 1 2))

(= (__ 6) '(1 1 2 3 5 8))

(= (__ 6) '(1 1 2 3 5 8))
