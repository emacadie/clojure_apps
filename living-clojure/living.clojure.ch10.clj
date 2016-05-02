;; page 174
(.toUpperCase (str (first [:cat :dog :fish])))
;; same as with thread-first macro
(-> [:cat :dog :fish] first str .toUpperCase)

;; play with thread first and thread last
(defn first-single [x]
  (println "first-single takes " x)
  (inc x))
(defn second-single [x]
  (println "second-single takes " x)
  (* 2 x))

;; returns 7, 3 is doubled, then that is incremented
;; calls second, then first
(def d (first-single (second-single 3)))
;; returns 8, 3 is incremented, then that is doubled
;; calls first, then second
(def r (second-single (first-single 3)))
;; this returns 8
;; calls first, then second
(-> 3 first-single second-single)
;; this returns 7
;; calls second, then first
(-> 3 second-single first-single)

user=>
;; this returns 7
;; calls second, then first
(->> 3 second-single first-single)
;; this returns 8
;; calls first, then second
(->> 3 first-single second-single)

(macroexpand '(-> 3 first-single second-single))
(macroexpand '(->> 3 first-single second-single))

(defn first-double [x y]
  (println "calling first-double with " x ", " y)
  (+ x y))


(macroexpand '(-> 0 (+ 1) (+ 2) (+ 3)))
(macroexpand '(-> 0 (+ 1) (+ 2) (+ 3)))
(macroexpand '(->> [1 2 3 4 5 6 7 8] (filter even?) (take 3)))

(macroexpand '(->>  4 first-single (first-double 3) ))
;; result is:
(first-double 3 (first-single 4))

(macroexpand '(->>  4 (first-double 3) first-single  ))
;; result is:
(first-single (first-double 3 4))

;; thread-first macro
;; Threads the expr through the forms. Inserts x as the
;; second item in the first form, making a list of it if it is not a
;; list already. If there are more forms, inserts the first form as the
;; second item in second form, etc.
;; If the "first form" is a function, the second item would be its argument
;; more Clojure enlightenment?

;; code from 4clojure problem 68
(loop [x 5
       result []]
  (println "Here is x: " x)
  (if (> x 0)
    (do
      (println "about to recur with x equal to " x)
      (recur (dec x) (conj result (+ 2 x))))
    result))
;;
