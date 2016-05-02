;; Local bindings
;; Difficulty:Elementary
;; Topics:syntax
;; Clojure lets you give local names to values using the special let-form.

(def __ 7)

(= __ (let [x 5] (+ 2 x)))

(= __ (let [x 3, y 10] (- y x)))

(= __ (let [x 21] (let [y 3] (/ x y))))

