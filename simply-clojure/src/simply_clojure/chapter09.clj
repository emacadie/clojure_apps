(ns simply-clojure.chapter09
  (:require [clojure.string :as str]
            [simply-clojure.helper :as helper]))

;; In each of the following exercises, write the procedure in terms of lambda and higher-order functions. 
;; Do not use named helper procedures. If you've read Part IV, don't use recursion, either.

;; 9.5  Write prepend-every:
(defn prepend-every [prestring the-sent]
  (helper/butlast-string (apply str (map #(str prestring %1 " ") the-sent))))

;; 9.6  Write a procedure sentence-version that takes a function f as its argument and returns a function g. 
;; f should take a single word as argument. g should take a sentence as argument and return the sentence formed by applying f to each word of that argument.
;; This is just a convoluted "every", right? Can I use "every"?

(defn sentence-version [the-func]
  (fn [g] (map the-func g)))

; 9.7  Write a procedure called letterwords that takes as its arguments a letter and a sentence. 
; It returns a sentence containing only those words from the argument sentence that contain the argument letter:

; > (letterwords 'o '(got to get you into my life))
; (GOT TO YOU INTO)
; This sounds like keep
(comment
 (define (letterwords letter the-sntnc)
(keep (lambda (x) (member? letter x)) the-sntnc))
)
(defn letterwords [letter the-sent]
  (filter #(str/includes? %1 letter) (helper/split-string-to-words the-sent))
)



