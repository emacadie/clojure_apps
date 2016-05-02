;; Let it Be
;; Difficulty:Elementary
;; Topics:math syntax
;; Can you bind x, y, and z so that these are all true?

;; this does not work as-is in the repl

(def __ [x 7 y 3 z 1])

(= 10 (let __ (+ x y)))

(= 4 (let __ (+ y z)))

(= 1 (let __ z))

