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

;; I could use my own appearances-string function
;; But this section is about recursion, so why not use a recursive function?
(defn word-appearances [the-word the-sent]
  (loop [sent-work the-sent
         word-count 0]
    (cond (empty? sent-work) word-count
          (= the-word (helper/first-string sent-work)) (recur (helper/butfirst-string sent-work) (inc word-count)) 
          :else (recur (helper/butfirst-string sent-work) word-count))))

(defn remove-dups [the-sent]
  (loop [sent-work the-sent
         outp ""]
    (cond (empty? sent-work) outp
          (> 1 (word-appearances (helper/last-string sent-work) sent-work)) (recur (helper/butlast-string sent-work) outp)
          :else (recur (helper/butlast-string sent-work) (str (helper/last-string sent-work) " " outp)))))

;;  14.4  
;; > (odds '(i lost my little girl))
;; (I MY GIRL)
;; This is like "keep" again
;; This needs a helper
(defn odds-r  [the-sent]
  (loop [sent-work the-sent
         outp ""
         counter 1]
    (cond (empty? sent-work) outp
          (odd? counter) (recur (helper/butfirst-string sent-work) (str outp " " (helper/first-string sent-work)) (inc counter))
          :else (recur (helper/butfirst-string sent-work) outp (inc counter)))))

;;  14.5  [8.7] Write a procedure letter-count that takes a sentence as its argument and returns the total number of letters in the sentence:
;; This one is accumulate.
;; When calling, set outp to 0
;; Or make your own helper.
(defn letter-count-r [the-sent]
  (loop [sent-work (helper/split-string-to-words the-sent)
         outp 0]
    (cond (empty? sent-work) outp
          :else (recur (rest sent-work) (+ outp (count (first sent-work)))))))

;; 14.6  Write member?.
;; This looks like accumulate
;; only works for strings
(defn member-r [the-word the-sent]
  (loop [sent-work the-sent
         found? false]
    (cond (empty? sent-work) found?
          (= the-word (helper/first-string sent-work)) true
          :else (recur (helper/butfirst-string sent-work) found?))))

;; 14.7  Write differences, which takes a sentence of numbers as its argument 
;; and returns a sentence containing the differences between adjacent elements. 
;; (The length of the returned sentence is one less than that of the argument.)
;; So we do not go all the way through.
;; > (differences '(4 23 9 87 6 12))
;; (19 -14 78 -81 6)
;; Sort of like every
;; works on a vector of numbers
(defn differences [nums]
  (loop [num-work (vec nums) 
         outp []]
    (cond (= (count num-work) 1) outp
          :else (recur (rest num-work) (conj outp (- (second num-work) (first num-work)))))))

;; 14.8  Write expand, which takes a sentence as its argument. 
;; It returns a sentence similar to the argument, 
;; except that if a number appears in the argument, 
;; then the return value contains that many copies of the following word:
;; > (expand '(4 calling birds 3 french hens))
;; (CALLING CALLING CALLING CALLING BIRDS FRENCH FRENCH FRENCH HENS)
;; > (expand '(the 7 samurai))
;; (THE SAMURAI SAMURAI SAMURAI SAMURAI SAMURAI SAMURAI SAMURAI)
;; I will have to make a helper function, that can also be recursive.
;; But this looks like every.

(defn print-n-times [num the-string]
  (loop [outp []
         count (int num)]
    (cond (= count 0) (clojure.string/join " " outp)
          :else (recur (conj outp the-string) (dec count)))))

(defn expand-r [the-sent]
  (println "using the not before the else")
  (loop [sent-work the-sent
         outp ""]
    (println "In loop with sent-work: ", sent-work, ", outp: " outp)
    (cond (empty? sent-work) outp
          (not (helper/is-string-number? (helper/first-string sent-work))) (recur (helper/butfirst-string sent-work) 
                                                                                  (str outp (helper/first-string sent-work) " "))
          :else  (recur (helper/butfirst-two-string sent-work) 
                        (str outp
                             (print-n-times (Double/parseDouble (helper/first-string sent-work)) 
                                            (helper/second-string sent-work))
                             " ")))))

;; 14.9  Write a procedure called location that takes two arguments, a word and a sentence. 
;; It should return a number indicating where in the sentence that word can be found. 
;; If the word isn't in the sentence, return #f. 
;; If the word appears more than once, return the location of the first appearance.
;; > (location 'me '(you never give me your money))
;; 4
;; Shouldn't it return 0 if the word is not found? I don't like the idea that it returns a number OR a boolean.
;; That kind of goes against his advice in chapter 12.
;; Sort of like accumulate, but like member? you do not have to go all the way through.

(defn location [word sent]
  (loop [sent-work sent
         counter 1]
    (cond (empty? sent-work) 0 
          (= word (helper/first-string sent-work)) counter
          :else (recur (helper/butfirst-string sent-work) (inc counter)))))

