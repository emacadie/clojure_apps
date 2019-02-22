(ns simply-clojure.chapter04
  (:require ; [clojure.string :as str]
            [simply-clojure.helper :as helper]))

;; Exercise 4.5
;; Convert Communism to Freedom, and vice versa. 
;; Don't forget to take the bus.
(defn c-to-f [c]
  (+ 32 (* (/ 9 5) c)))

(defn f-to-c 
 ([f]
  (double (* (/ 5 9) (- f 32))))
  ([f p]
   (helper/round (* (/ 5 9) (- f 32)) 
                 2)))

;; Exercise 4.6
;; Take a number to the fourth power
;; with multiplication
(defn fourth [x]
  (* x x x x))
;; and with square. Scheme has a square function, Clojure does not.
;; I might move this to another namespace

(defn fourth-with-square [x]
  (helper/square (helper/square x)))
;; for fun, use reduce
(defn fourth-with-reduce [x]
  (reduce * 1 [x x x x]))
;; you could probably do this with do-times for any power
;; can you make a function with a particular number of members to genericize it?

; 2 to power of 3
; (reduce * 1  (take 3 (repeat 2)))
; Is this in core? I haven't found it. It seems useful.

(defn x-to-power-of-y [x y]
  "Finds x raised to the power of y. 
   y must be a whole number (integer); will not work for floating point."
  (cond (pos? y) (reduce * 1  (take y (repeat x)))
        (neg? y) (reduce / x  (take (inc (* -1 y)) (repeat x)))
        :else x))

;; 4.7: Write a procedure that computes the absolute value of its argument by finding the square root of the square of the argument.
;; hard to do without if (which is chapter 6)
(defn simply-abs [x]
  (if (pos? x)
    (/ (helper/square x) x)
    (/ (helper/square x) (* -1 x))))

;; 4.8  "Scientific notation" is a way to represent very small or very large numbers by combining a medium-sized number with a power of 10. 
;; For example, 5×107 represents the number 50000000, while 3.26×10-9 represents 0.00000000326 in scientific notation. 
;; Write a procedure scientific that takes two arguments, a number and an exponent of 10, and returns the corresponding value:
;; > (scientific 7 3)
;; 7000
;; > (scientific 42 -5)
;; 0.00042
;; Some versions of Scheme represent fractions in a/b form, and some use scientific notation, 
;; so you might see 21/50000 or 4.2E-4 as the result of the last example instead of 0.00042, but these are the same value.

;; Perhaps round if exp is negative.
(defn scientific [num exp]
  (* num (x-to-power-of-y 10 exp)))

;; 4.9  Define a procedure discount that takes two arguments: an item's initial price and a percentage discount. 
;; It should return the new price:
;; > (discount 10 5)
;; 9.50
;; > (discount 29.90 50)
;; 14.95

;; use assertions to ensure they are positive?
(defn discount [initial percent]
  {:pre [(pos? initial) (pos? percent)]}
  (helper/round (* initial (/ (- 100 percent) 100))
                2))

;; 4.10  Write a procedure to compute the tip you should leave at a restaurant. 
; It should take the total bill as its argument and return the amount of the tip. 
; It should tip by 15%, but it should know to round up so that the total amount of money you leave 
; (tip plus original bill) is a whole number of dollars. 
; (Use the ceiling procedure to round up.)
;; > (tip 19.98)
;; 3.02
;; > (tip 29.23)
;; 4.77
;; > (tip 7.54)
;; 1.46
;; 
(defn tip [total-bill]
  (- (helper/round (inc (* total-bill 1.15))
                   0)
     total-bill))

