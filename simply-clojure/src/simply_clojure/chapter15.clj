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

;; 15.4 Write a predicate procedure substring? that takes two words as arguments and returns #t if and only if the first word is a substring of the second. 
;; (See Exercise 15.3 for the definition of a substring.)
;; Be careful about cases in which you encounter a "false start," like this:
;; > (substring? 'ssip 'mississippi)
; #T
;; and also about subsets that don't appear as consecutive letters in the second word:
;; > (substring? 'misip 'mississippi)
(defn substring? [sub the-string]
 (contains? (set (sub-whole-word-r the-string)) sub))

;; 15.6  Let's say a gladiator kills a roach. 
;; If we want to talk about the roach, we say "the roach the gladiator killed." 
;; But if we want to talk about the gladiator, we say "the gladiator that killed the roach."

;; People are pretty good at understanding even rather long sentences as long as they're straightforward: 
;; "This is the farmer who kept the cock that waked the priest that married the man that kissed the maiden
;; that milked the cow that tossed the dog that worried the cat that killed the rat that ate the malt 
;; that lay in the house that Jack built." 
;; But even a short nested sentence is confusing: 
;; "This is the rat the cat the dog worried killed." 
;; Which rat was that?
;; Write a procedure unscramble that takes a nested sentence as argument and returns a straightforward sentence about the same cast of characters:
;; You may assume that the argument has exactly the structure of these examples, 
;; with no special cases like "that lay in the house" or "that Jack built."

;; only works for those weird types of sentences
;; this is the noun3 the noun2 the noun1 verb1 verb2
; A lot of calls to "vec" and "vector". 
;; Vectors are the barbeque sauce of Clojure. Don't know what to do? Make it a vector.
(defn unscramble[arg-sent]
  (let [sent-vec (helper/split-string-to-words arg-sent)
        first-two (subvec sent-vec 0 2)]
    (loop [the-sent (subvec sent-vec 2 (count sent-vec)) 
           outp []]
      (cond (empty? the-sent) (helper/join-with-spaces (flatten (vector first-two outp))) 
            (= (count the-sent) 2) (recur "" (concat (vector the-sent outp)))
            :else (recur (concat (helper/butlast-vec (helper/rest-vec (helper/rest-vec the-sent))))
                         (concat (vector "that" (last the-sent) (first the-sent) (first (helper/rest-vec the-sent)) outp)))))))


