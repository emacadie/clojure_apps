(ns simply-clojure.chapter15
  (:require [clojure.string             :as string]
            [simply-clojure.helper      :as helper]
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
    ; (println "In loop of to-binary-r, with power-list: ", power-list, ", counter: " counter, ", outp: " outp)
    (cond (empty? power-list) outp
          (= (first power-list) counter) (recur (rest power-list) (inc counter) (vec (concat [1] outp)))
          :else (recur power-list (inc counter) (vec (concat [0] outp))))))

;; 15.2  A "palindrome" is a sentence that reads the same backward as forward.
;; Write a predicate palindrome? that takes a sentence as argument and decides whether it is a palindrome.
;; Do not reverse any words or sentences in your solution.
(defn palindrome-r [arg-word] 
  ; (display-all "calling palindrome-r with the-word: " the-word ", outp: " outp)
  (loop [the-word (helper/split-word-to-letters (string/replace (string/lower-case arg-word) #"[^a-zA-Z]" "")) 
         outp false]
    (cond (empty? the-word) true
          (= (count the-word) 1) true
          (not (= (first the-word) (last the-word))) false
          :else (recur (helper/rest-vec (helper/butlast-vec the-word)) true))))

;; 15.3  Write a procedure substrings that takes a word as its argument.
;; It should return a sentence containing all of the substrings of the argument.
;; A substring is a subset whose letters come consecutively in the original word.
;; For example, the word bat is a subset, but not a substring, of brat.
;; One drawback: If a letter appears multiple times in word, it will be in list multiple times
(defn substrings-r [arg-word]
  (loop [the-word (helper/split-word-to-letters arg-word) 
         outp []]
    (cond (empty? the-word) outp
          (empty? outp) (recur (helper/rest-vec the-word) 
                               (conj outp (first the-word)))
          :else (recur (helper/rest-vec the-word) 
                       (conj outp 
                             (str (last outp) (first the-word)))))))

(defn sub-whole-word-r [arg-word]
  (loop [the-word (helper/split-word-to-letters arg-word)
         outp []]
    (cond (empty? the-word) (filter #(not (empty? %1)) (flatten outp))
          ;; Why does the else skip the second letter?
          (empty? outp) (recur (helper/rest-vec the-word) 
                               (conj (substrings-r (string/join the-word)) 
                                     (substrings-r (string/join (helper/rest-vec the-word)))))
          :else (recur (helper/rest-vec the-word) 
                       (conj outp 
                             (substrings-r (string/join (helper/rest-vec the-word))))))))




