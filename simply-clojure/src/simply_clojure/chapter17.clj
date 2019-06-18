(ns simply-clojure.chapter17
  (:require [clojure.string             :as string]
            [simply-clojure.helper      :as helper]
            [clojure.math.numeric-tower :as math-nt]))

;;  17.5  Here's a procedure that takes two numbers as arguments and returns whichever number is larger:
(defn max2 [a b] 
  (if (> b a) 
      b 
      a))
;; Use max2 to implement max, 
;; a procedure that takes one or more numeric arguments and returns the largest of them. 

;; Frankly, I think I like "cond" better in Scheme/Racket.
;; But I guess you can only change what brackets do just once
(defn my-max [number & rest-nums] 
  (cond (nil? rest-nums) number
        (= (count rest-nums) 1)  (max2 number (first rest-nums))
        (= (max2 number (first rest-nums)) number) (apply my-max (cons number (rest rest-nums)))
        (= (max2 number (first rest-nums)) (first rest-nums)) (apply my-max rest-nums) ;; okay
        :else number))

(defn my-max-loop-recur [the-num & rst-nums]
  (loop [number the-num
         rest-nums rst-nums]
    (cond (nil? rest-nums) number
          (= (count rest-nums) 1)  (max2 number (first rest-nums))
          (= (max2 number (first rest-nums)) number) (recur number (rest rest-nums))
          (= (max2 number (first rest-nums)) (first rest-nums)) (recur (first rest-nums) 
                                                                       (rest rest-nums)) ;; okay
          :else number)))

;; Winnin' with the 'duce
;; conj adds the second arg to the FRONT of a list or seq, but the rear of a vector
;; I know people rave about Clojure's abstractions, but maybe vectors should have a separate function for adding from lists and seqs
;; Maybe there is and it just escapes me now. I will look later.
;; I suppose I could make aliases or something.
(defn my-max-reduce [number & rest-nums]
   (reduce max2 0 (conj rest-nums number)))

;; 17.6  Implement append using car, cdr, and cons. 
;; (Note: The built-in append can take any number of arguments. 
;; First write a version that accepts only two arguments. 
;; Then, optionally, try to write a version that takes any number.) 
;; This should behave for lists and seqs like conj does for vectors.
;; Granted, there is a lot of reversing lists. I wonder if someone has done this?
; comp does not like macros - because the result does not carry. Got it.
(defn nil-or-empty? [the-list]
  (or (nil? the-list) (empty? the-list)))

(defn my-append [first-list second-list]
  (loop [list-a first-list
         list-b second-list]
    (cond (and (not (list? list-b)) (not (seq? list-b))) (reverse (conj (reverse list-a) list-b)) 
          (nil-or-empty? list-b) list-a
          (nil-or-empty? list-a) list-b
          :else (recur (reverse (conj (reverse list-a) (first list-b))) 
                       (rest list-b)))))

(defn list-append [first-list & second-list]
  (loop [list-a first-list
         list-b second-list]
    (cond (nil-or-empty? list-b) list-a
          (nil-or-empty? list-a) (recur (first list-b) (rest list-b)) 
          (and (= (count list-b) 1 ) (seq? (first list-b)) (empty? (first list-b))) list-a
          :else  (recur (my-append list-a (first list-b)) (rest list-b)))))

; try by converting to vectors and back
(defn my-vec-append [first-list second-list]
  (loop [list-a first-list
         list-b second-list]
    (cond (and (not (list? list-b)) (not (seq? list-b))) (seq (conj (vec list-a) list-b))
          (nil-or-empty? list-b) list-a
          (nil-or-empty? list-a) list-b
          :else (recur (seq (conj (vec list-a) (first list-b))) 
                       (rest list-b)))))

(defn list-vec-append [first-list & second-list]
  (loop [list-a first-list
         list-b second-list]
    (cond (nil-or-empty? list-b) list-a
          (nil-or-empty? list-a) (recur (first list-b) (rest list-b)) 
          (and (= (count list-b) 1 ) (seq? (first list-b)) (empty? (first list-b))) list-a
          :else  (recur (my-vec-append list-a (first list-b)) (rest list-b)))))

;; 17.7  Append may remind you of sentence. 
;; They're similar, except that append works only with lists as arguments, whereas sentence will accept words as well as lists. 
;; Actually, in R7RS Scheme and Racket append will take a word as the second arg
;; Implement sentence using append. 
;; (Note: The built-in sentence can take any number of arguments. 
;; First write a version that accepts only two arguments. 
;; Then, optionally, try to write a version that takes any number. 
;; Also, you don't have to worry about the error checking that the real sentence does.) 
;; Also from text:
;; How does our sentence point of view differ from the built-in Scheme point of view using lists? There are three differences:
;; A sentence can contain only words, not sublists.
;; Sentence selectors are symmetrical front-to-back.
;; Sentences and words have the same selectors.
;; I will still check that if it's a list it does not have a sublist
  
;; I will still check that if it's a list it does not have a sublist
; (set! *default-data-reader-fn* tagged-literal)

(defn list-with-sublists? [the-list]
  (cond (not (list? the-list)) false
        (= 0 (count (filter list? the-list))) false
        :else true)) 

(defn list-or-item [the-item]
  (cond (not (list? the-item)) (list the-item)
        (list-with-sublists? the-item) nil
        :else the-item))

(defn sentence-via-append [list-a list-b]
  (my-append (list-or-item list-a) (list-or-item list-b)))

(defn multi-sentence-append [first-list & second-list]
  (loop [list-a first-list
         list-b second-list]
    (cond (nil-or-empty? list-b) (remove nil? list-a)    
          (nil? list-a) (recur (first list-b) (rest list-b)) 
          ;; Racket got empty list in a list '(()) - might not need this for clojure
          ; (and (= 1 (count list-b)) (list? (first list-b)) (empty? (first list-b))) list-a 
          :else (recur (sentence-via-append list-a (first list-b)) (rest list-b))))) 

; once again, winnin' with the 'duce!!
(defn multi-sentence-reduce [list-a & list-b]
  (remove nil? (sentence-via-append list-a (reduce sentence-via-append list-b))))

;; 17.8  Write member.
;; I don't think you can use higher-order functions, because I think you have to go through the entire collection with HOF
(defn new-member [the-item the-list]
  (loop [item-a the-item
         list-a the-list]
      (cond (nil-or-empty? list-a) false
            (= item-a (first list-a)) list-a
            :else (recur item-a (rest list-a)))))

;; 17.9  Write list-ref. 
;; The list equivalent of item is called list-ref (short for "reference")
; it's different in that it counts items from zero instead of from one and takes its arguments in the other order
;; okay, I looked at the other solutions, and yes, decrementing is more efficient
(defn my-list-ref [the-list the-num]
  (loop [list-a the-list
         num-a the-num]
    (cond (nil-or-empty? list-a) false
          (= num-a 0) (first list-a)
          :else (recur (rest list-a) (dec num-a)))))

;; 17.10  Write length. 
(defn my-length [the-list]
  (loop [list-a the-list
         counter 0] ;; no need for variadic w/loop recur
    (cond (nil-or-empty? list-a) counter 
          :else (recur (rest list-a) (inc counter)))))

;; From https://github.com/buntine/Simply-Scheme-Exercises/blob/master/17-lists/17-10.scm
(defn length-reduce [lst]
  (reduce +
          (map (fn [x] 1) lst)))

;; 17.11  Write before-in-list?, which takes a list and two elements of the list. 
;; It should return #t if the second argument appears in the list argument before the third argument:
; > (before-in-list? '(back in the ussr) 'in 'ussr) returns #T
; > (before-in-list? '(back in the ussr) 'the 'back) returns #F
;; let's compare the length of our members
(defn before-in-list? [list-a item-a item-b]
  (cond (or (nil? item-a) (nil? item-b)) false
        (or (not (new-member item-a list-a)) (not (new-member item-b list-a))) false
        ; earlier item will have longer list from member
        (> (my-length (new-member item-a list-a))
           (my-length (new-member item-b list-a))) true
        :else false))

;; 17.12  Write a procedure called flatten that takes as its argument a list, possibly including sublists, 
; but whose ultimate building blocks are words (not Booleans or procedures). 
; It should return a sentence containing all the words of the list, in the order in which they appear in the original:
; so I based it on their functions (see 17.13), but I had to add an arg
; and it's not tail recursive
; > (flatten2 '(((a b) c (d e)) (f g) ((((h))) (i j) k)) '())
; (A B C D E F G H I J K)
; (flatten2 '(((the man) in ((the) moon)) ate (the) potstickers) '())
; (flatten2 '(the man (in ((the) moon)) ate (the) potstickers) '())
; these tests are failing, I will come back to them later
(defn flatten2 [lst outp]
  (println "-- calling flatten2 with lst: ", lst ", and outp: " outp)
  (cond (nil-or-empty? lst) outp
        (nil? (first lst)) outp
        (not (list? (first lst))) 
        (do
          (println "(first lst) is: ", (first lst), ", and outp is: ", outp)
          (flatten2 (rest lst) 
                    ; (multi-sentence-reduce outp (first lst))
                    (my-append outp (first lst))
)
          )
        :else (do
                (println "in the else")
                (multi-sentence-reduce (flatten2 (first lst) outp) 
                                     (flatten2 (rest lst) '()))
                )))

; buntine and sanjeevs used reduce, looking very similar to what is in 17.13 (I did 17.13 first)
; I should have seen that, given that the directions mention words and I saw that the cond-based function from 17.13 was the basis for flatten
;; Yo dawg, I heard you like reduce, so call a function that uses reduce in your call to reduce in your recursive function
(defn flatten-reduce [lst]
  (cond (not (list? lst)) lst
        :else
         (reduce multi-sentence-reduce
                 (map flatten-reduce lst))))

(defn deep-count [lst]
  ; (println "calling deep-count with lst: ", lst)
  (cond (nil? lst) 0
        (nil? (first lst)) 0
        (not (list? (first lst)))
        (do
          (println "first lst: ", (first lst), ", it's a ", (class (first lst)))
          (+ 1 (deep-count (rest lst)))
          )
        
        :else (+ (deep-count (first lst))
                 (deep-count (rest lst)))))

;; 17.14  Write a procedure branch that takes as arguments a list of numbers and a nested list structure. 
; It should be the list-of-lists equivalent of item, like this:
; > (branch '(3) '((a b) (c d) (e f) (g h)))
; (E F)
; > (branch '(3 2) '((a b) (c d) (e f) (g h)))
; F
; > (branch '(2 3 1 2) '((a b) ((c d) (e f) ((g h) (i j)) k) (l m)))
; H
; In the last example above, the second element of the list is
; ((C D) (E F) ((G H) (I J)) K)
; The third element of that smaller list is ((G H) (I J)); the first element of that is (G H); and the second element of that is just H. 
(defn branch [num-list-a nested-list-a]
  (loop [num-list num-list-a
         nested-list nested-list-a]
    (cond (nil-or-empty? num-list)  nested-list
          :else (recur (rest num-list) 
                       (my-list-ref nested-list 
                                    (- (first num-list) 1))))))

; 17.16  Write a predicate valid-infix? that takes a list as argument 
; and returns #t if and only if the list is a legitimate infix arithmetic expression 
; (alternating operands and operators, with parentheses—that is, sublists—allowed for grouping).
;> (valid-infix? '(4 + 3 * (5 - 2)))
;#T
;> (valid-infix? '(4 + 3 * (5 2)))
;#F
; you could flatten and then just do a count: if odd, it's good (since you would need a number after the last operator)
; But right now flatten is not working
(comment
(define (valid-infix-easy? op-list) 
  (cond (odd? (count (flatten-reduce op-list))) true
        :else false))

;; But what if some joker makes it out of order? Operators on one side, operands on the other
(define (valid-infix-helper op-list position)
  (cond (empty? op-list) true
        (< position 1) (valid-infix-helper op-list 1)
        (and (odd? position) (not (number? (car op-list)))) false
        (and (even? position) (number? (car op-list))) false
        :else (valid-infix-helper (cdr op-list) (+ 1 position))))

(define (valid-infix? op-list)
  (cond (even? (count (flatten-reduce op-list))) false
        :else (valid-infix-helper (flatten-reduce op-list) 1)))

)




(defn greet [name & rest-n]
  (println "is rest-n a list: " (list? rest-n))
  (println "is rest-n a seq: " (seq? rest-n))
  (println "Hello" name rest-n))




