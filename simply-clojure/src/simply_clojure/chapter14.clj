(ns simply-clojure.chapter14
  (:require [clojure.string :as string]
            [simply-clojure.helper :as helper]))

; a procedure to square every number in a sentence of numbers
(defn- square-sentr-work [sent outp]
  (loop [sent-work sent
         outp-work outp]
    (cond (empty? sent-work) outp-work
          (not (number? (first sent-work))) (recur (rest sent-work) outp-work)
          :else (recur (rest sent-work) (conj outp-work (helper/square (first sent-work)))))))
;; line 13
(defn square-sentr [sent]
  (square-sentr-work sent []))

(defn pigl [wd]
  (if (helper/vowel? (helper/first-string wd))
      (str wd "ay")
      (pigl (str (helper/butfirst-string wd) (helper/first-string wd)))))

;; tail recursive
(defn- pigl-sentr-work [sent outp]
  (loop [sent-work sent
         out-work outp]
    (cond (helper/string-is-word sent-work) (str out-work " " (pigl sent-work))
          :else (recur (helper/butfirst-string sent-work) 
                       (str out-work 
                            " " 
                            (pigl (helper/first-string sent-work)))))))

(defn pigl-sentr [sent]
  (if (string/blank? sent)
    sent
    (pigl-sentr-work sent "")))

(defn- disjoint-pairsr2 [wd outp]
  (loop [word-work wd
         out-work outp]
    (cond (empty? word-work) out-work 
	    (= (count word-work) 1) (str out-work, " ", word-work)
        :else (recur (helper/butfirst-string (helper/butfirst-string word-work))
                     (str out-work (str " "
                                        (helper/first-string word-work) 
                                        (helper/first-string (helper/butfirst-string word-work))))))))

;; I am covering some cases with the calling function
;; Perhaps my logic is not tight enough
(defn disjoint-pairs [word]
  ; remove beginning space by calling subs
  (helper/safe-subs (disjoint-pairsr2 word "") 1))

;; Every: one base case, one recursive case
;; Keep: one base case, two (or more?) recursive cases
(defn keep-three-letter-words-r 
  ([sent] (helper/safe-subs (keep-three-letter-words-r sent "") 1))
  ([sent outp]
  (loop [sent-work (helper/split-string-to-words sent) 
         outw outp]
    (cond (empty? sent-work) outw
          (= (count (first sent-work)) 3) (recur (rest sent-work) (str outw " " (first sent-work)))
          :else (recur (rest sent-work) outw)))))

(defn keep-vowels-r [wd outp]
  "takes a word wd and an output string outp to store the output for the loop recur"
  (loop [wdw wd
         outw outp]
    (cond (empty? wdw) outw
        (helper/vowel? (helper/first-word wdw)) (recur (helper/butfirst-word wdw) (str outw (helper/first-word wdw))) 
        :else (recur (helper/butfirst-word wdw) (str outw)))))

(defn doubles-r [wd outp]
  (loop [wdw wd
         outw outp]
    (cond (= (count wdw) 1) outw
        (= (helper/first-string wdw) 
                (helper/first-string (helper/butfirst-string wdw))) 
        (recur (helper/butfirst-string (helper/butfirst-string wdw)) 
               (str outw 
                    (helper/first-string wdw ) 
                    (helper/first-string (helper/butfirst-string wdw)))) 
        :else (recur (helper/butfirst-string wdw) outw))))

;; 14.1  
;; > (remove-once 'morning '(good morning good morning))
;; (GOOD GOOD MORNING)
;; (It's okay if your solution removes the other MORNING instead, as long as it removes only one of them.) 
;; This is sort of like "keep." The result has one less, so it's not "every", and there is more than one, so it's not "accumulate".
(defn remove-once [bad-word sent]
  (loop [sent-work sent
         outp ""]
    (cond (empty? sent-work) outp
          (= bad-word (helper/first-string sent-work)) (str outp " " (helper/butfirst-string sent-work))
          :else (recur (helper/butfirst-string sent-work) (str outp " " (helper/first-string sent-work))))))

;;  14.2  
;; > (up 'town)
; (T TO TOW TOWN)
;; This is kind of like every.
(defn upr [the-word]
  (loop [wordout the-word
         outp ""]
    (cond (empty? the-word) outp
          (= (count outp) 0) (recur (helper/butfirst-string the-word) (subs the-word 1))
          :else (recur (helper/butfirst-string the-word) 
                       (str outp " " (str (helper/last-string outp) (helper/first-string the-word)))))))

; 14.3  
; > (remdup '(ob la di ob la da))              ;; remove duplicates
; (OB LA DI DA)
; (It's okay if your procedure returns (DI OB LA DA) instead, as long as it removes all but one instance of each duplicated word.)
;; This is like "keep".
(comment
(define (remove-dup-r sent outp)
  (display-all "calling up-r with sent: " sent ", and outp: " outp)
  (cond ((empty? sent) outp)
        ((> (appearances (last sent) sent) 1) (remove-dup-r (butlast sent) outp))
        (else (remove-dup-r (butlast sent) (sentence (last sent) outp)))))
)

;; I could use my own appearances-string function
;; But this section is about recursion, so why not use a recursive function?


(defn (remove-dups [the-sent])
  (loop [sent-work the-sent
         outp ""]
    (cond (empty? sent-work) outp
          
)
)
)


