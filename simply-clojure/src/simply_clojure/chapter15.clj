(ns simply-clojure.chapter15
  (:require [clojure.string :as string]
            [simply-clojure.helper :as helper]
            [clojure.math.numeric-tower :as math-nt]))

;; Chapter 15 Advanced Recursion

;; Again, I think tail-recursion results in larger code

;; From the text:
;; "Sometimes you write a recursive procedure with a correct recursive case and a reasonable base case, 
;; but the program still doesn't work. 
;; The trouble may be that the base case doesn't quite catch all of the ways in which the problem can get smaller. 
;; A second base case may be needed."
;; I don't know what this says about me, but I have already come across that. 
;; Perhaps because I am trying to make everything tail-recursive.

;; 15.1  Write a procedure to-binary:
; > (to-binary 9)
;; 1001
;; > (to-binary 23)
;; 10111

; This actually gets a list of where the ones go
; come up with a better name later

(defn closest-power-of-2 [the-num out]
  (loop [num-work the-num
         out-work out]
    (cond (> (math-nt/expt 2 out-work) num-work) (dec out-work)
          (= (math-nt/expt 2 out-work) num-work) out-work
          :else (recur num-work (inc out-work)))))

(defn  get-all-powers-of-2  [the-num]
  (loop [num-work the-num
         outp []]
    (cond (= num-work 0) outp
        (= num-work 2) (concat 1 outp)
        :else (recur (- num-work (math-nt/expt 2 (closest-power-of-2 num-work 0))) 
                     (concat (vector (+ (closest-power-of-2 num-work 0) 1)) outp)))))

(defn to-binary-r [the-num]
  (loop [power-list (get-all-powers-of-2 the-num)
         counter 1
         outp []]
    (println "In loop of to-binary-r, with power-list: ", power-list, ", counter: " counter, ", outp: " outp)
    (cond (empty? power-list) outp
          (= (first power-list) counter) (recur (rest power-list) (inc counter) (vec (concat [1] outp)))
          :else (recur power-list (inc counter) (vec (concat [0] outp))))))

(comment
(define (to-binary-r power-list counter outp)
  (cond [(empty? power-list) outp]
        [(equal? (first power-list) counter) (to-binary-r (butfirst power-list) (+ 1 counter) (sentence 1 outp))]
        [else (to-binary-r power-list (+ 1 counter) (sentence 0 outp))]))
;; call this
(define (to-binary dec-num)
  (accumulate word 
              (to-binary-r (get-all-powers-of-2 (trim-leading-zeros dec-num) '()) 
                           1
                           '())))

(define (from-binary-r bin-num counter outp)
  (cond [(empty? bin-num) outp]
        [(equal? (last bin-num) 0) (from-binary-r (butlast bin-num) (+ counter 1) outp)]
        ;; else it's equal to 1, right? 
        [else
         (from-binary-r (butlast bin-num) (+ counter 1) (+ outp (expt 2 counter)))]))

(define (trim-leading-zeros the-num)
  (if (not (equal? (first the-num) 0))
      the-num
      (trim-leading-zeros (butfirst the-num))))

(define (from-binary bin-num)
  (from-binary-r (trim-leading-zeros bin-num) 0 0))


)


