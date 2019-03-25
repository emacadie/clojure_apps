(ns simply-clojure.chapter14-numbers
  (:require [clojure.string :as string]
            [simply-clojure.helper :as helper]))

(comment
; "item" in Simply Scheme starts counting a list at "1", not "0" like C
(define number-table '(one two three four five six seven eight nine zero))

(define power-1-table '(ten twenty thirty forty fifty sixty seventy eighty ninety))

(define (get-new-teen-table n)
  (cond [(empty? n) 'ten]
        [(equal? (first n) 'one) 'eleven]
        [(equal? (first n) 'two) 'twelve]
        [(equal? (first n) 'three) 'thirteen]
        [else (word (first n) 'teen)]))

(define (num-name2-work num-work output depth)
  (cond [(empty? num-work) output]        ;; skip ending zero
        [(zero? (last num-work)) (num-name2-work (butlast num-work) 
                                                 output 
                                                 (+ 1 depth))]
        [(equal? depth 1) (num-name2-work (butlast num-work) 
                                          (sentence (item (last num-work) number-table)) 
                                          (+ 1 depth))]
        ; new teens
        [(and (equal? depth 2) (equal? 1 (last num-work))) (num-name2-work (butlast num-work) 
                                                                           (get-new-teen-table output) 
                                                                           (+ 1 depth))]
        [(equal? depth 2) (num-name2-work (butlast num-work) 
                                          (sentence (item (last num-work) power-1-table)  output) 
                                          (+ 1 depth))]
        [(equal? depth 3) (num-name2-work (butlast num-work) 
                                          (sentence (item (last num-work) number-table) 'hundred output) 
                                          (+ 1 depth))]))

(define (number-name-2 number)
  (num-name2-work (last (do-string-break (number->string number) '())) "" 1))

(define (grand-num-name-worker the-num outp group)
  (cond ((empty? the-num) outp)
        (else (grand-num-name-worker (butlast the-num)
                                     (sentence (exponent-helper (num-name2-work (last the-num) "" 1) 
                                                                group) outp) 
                                     (+ 1 group)))))

(define (grand-num-name-caller raw-num)
  (keep (lambda (x) (not (empty? x)))
(grand-num-name-worker (do-string-break (number->string raw-num) '()) "" 1)))
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
)

(defn exponent-helper [num group]
  (cond (empty? num) ""
        (= group 1)  (str num)
        (= group 2)  (str num " thousand")
        (= group 3)  (str num " million")
        (= group 4)  (str num " billion")
        (= group 5)  (str num " trillion")
        (= group 6)  (str num " quadrillion") 
        (= group 7)  (str num " quintillion")
        (= group 8)  (str num " sextillion") 
        (= group 9)  (str num " septillion") 
        (= group 10) (str num " octillion")
        (= group 11) (str num " nonillion")
        (= group 12) (str num " decillion")))

(defn remove-leading-zeros [num] 
  (loop [num-work num
         outp ""]
    (cond (empty? num-work) outp
          (= "0" (helper/first-word num-work)) (recur (helper/butfirst-word num-work) outp)
          :else num-work)))

(defn get-last-3 [the-num]
  (if (< (count the-num) 3)
    the-num
    (str (helper/last-word (helper/butlast-word (helper/butlast-word the-num)))
        (helper/last-word (helper/butlast-word the-num))
        (helper/last-word the-num))))

(defn break-string-to-threes [num-string] ; num-list
  (loop [num-work num-string
         outp []]
    (cond (<= (count num-work) 3) (vec (reverse (conj outp num-work))) ; tupelo can do that for you
          :else (recur (helper/butlast-word (helper/butlast-word (helper/butlast-word num-work)))
                       (conj outp (remove-leading-zeros (str (get-last-3 num-work))))))))

