(ns simply-clojure.chapter15-phone
  (:require [clojure.string :as string]
            [simply-clojure.helper :as helper]))

;; Chapter 15 Advanced Recursion

;; Again, I think tail-recursion results in larger code

;; From the text:
;; "Sometimes you write a recursive procedure with a correct recursive case and a reasonable base case, 
;; but the program still doesn't work. 
;; The trouble may be that the base case doesn't quite catch all of the ways in which the problem can get smaller. 
;; A second base case may be needed."
;; I don't know what this says about me, but I have already come across that. 
;; Perhaps because I am trying to make everything tail-recursive.

;; more words of wisdom from the text:
;; You may be thinking that you never would have thought of that yourself. 
;; But we're just following the method: 
;; Look at the smaller case and see how it fits into the original problem.

;; 15.5 Suppose you have a phone number, such as 223-5766, and you'd like to figure out a clever way to spell it in letters for your friends to remember. 
;; Each digit corresponds to three possible letters. 
;; For example, the digit 2 corresponds to the letters A, B, and C. 
;; Write a procedure that takes a number as argument and returns a sentence of all the possible spellings:
;; > (phone-spell 2235766)
;; (AADJPMM AADJPMN ...CCFLSOO)
;; (We're not showing you all 2187 words in this sentence.) 
;; You may assume there are no zeros or ones in the number, since those don't have letters.
;; Hint: This problem has a lot in common with the subsets example. 

;; try with some higher-order functions, then convert
(defn first-combine-h [lttr list-b]
  (map #(str %1 lttr) list-b))

(defn first-combine-r [lttr sent-arg]
  (loop [sent sent-arg
         outp []]
    (if (empty? sent)
      outp ;; base case returns output
      (recur (rest sent) 
             (conj outp (str (first sent) lttr))))))

(defn higher-combine-h  [list-a list-b]
  (flatten (map #(first-combine-h %1 list-a) list-b)))

(defn higher-combine-r [list-a-arg list-b-arg]
  (loop [list-a list-a-arg
         list-b list-b-arg
         outp []]
    (if (empty? list-b)
      (flatten outp) ;; base case returns output
      (recur list-a (rest list-b) (conj outp (first-combine-r (first list-b) list-a))))))

(def num-vec [[0] [1] 
               ["a" "b" "c"] ["d" "e" "f"] ["g" "h" "i"] ["j" "k" "l"] 
               ["m" "n" "o"] ["p" "q" "r" "s"] ["t" "u" "v"] ["w" "x" "y" "z"]
])

(defn build-lttr-list-from-num [arg-num]
  (loop [the-num (helper/digits arg-num :vector)
         outp []]
    (cond (empty? the-num) outp
        :else (recur (helper/rest-vec the-num) 
                     (conj outp
                           (nth num-vec (first the-num)))))))
(defn phone-lttr-list [arg-list]
  (loop [lttr-list arg-list
         outp []]
    (cond (empty? lttr-list) outp
          (empty? outp) (recur (helper/rest-vec (rest lttr-list)) 
                               (higher-combine-r (first lttr-list) 
                                                 (nth lttr-list 1)))
         :else (recur (helper/rest-vec lttr-list) 
                      (higher-combine-r outp 
                                        (first lttr-list)))
)
)
  )
(comment
(define (try-phone-with-higher lttr-list outp)
  (cond [(empty? lttr-list) outp]
        [(empty? outp) (try-phone-with-higher (butfirst (butfirst lttr-list)) 
                                              (higher-combine (first lttr-list) 
                                                              (simply-second lttr-list)))]
        [else (try-phone-with-higher (butfirst lttr-list) 
                                     (higher-combine outp 
                                                     (first lttr-list)))]))
; (build-num-lttr-list 2345678 '())
;; returns '(abc def ghi jkl mno pqrs tuv)

;; get a list from build-num-lttr-list
; (try-phone-with-higher (build-num-lttr-list 2345678 '()) '())

;; call this
;; (first-phone-spell 1234567)
(define (first-phone-spell pnum)
  (try-phone-with-higher (build-num-lttr-list pnum '()) '()))
;; same number of results as the other person, 
;; but although it took me a while,
;; according to githib they gave up and came back six years later
;; although they did follow the text's recommendation better

)






