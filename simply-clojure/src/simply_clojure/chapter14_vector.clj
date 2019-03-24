(ns simply-clojure.chapter14-vector
  (:require [clojure.string :as string]
            [simply-clojure.helper :as helper]))

;; some of the stuff in chapter 14, but with vectors instead of strings

; a procedure to square every number in a sentence of numbers
(defn square-sent-v [sent]
  (loop [sent-work sent
         outp []]
    (cond (empty? sent-work) (helper/join-with-spaces outp)
          (not (number? (first sent-work))) (recur (rest sent-work) outp)
          :else (recur (rest sent-work) (conj outp (helper/square (first sent-work)))))))

(defn pigl [wd]
  (if (helper/vowel? (helper/first-string wd))
      (str wd "ay")
      (pigl (str (helper/butfirst-string wd) (helper/first-string wd)))))

;; tail recursive
(defn- pigl-sentv-work [sent]
  (loop [sent-work (helper/split-string-to-words sent)
         outp []]
    (cond (helper/string-is-word sent-work) (helper/join-with-spaces (flatten outp (pigl sent-work)) )
          :else (recur (helper/butfirst-string sent-work) 
                       (conj outp (pigl (helper/first-string sent-work)))))))

(defn pigl-sentv [sent]
  (if (string/blank? sent)
    sent
    (pigl-sentv-work sent)))

(defn disjoint-pairsr-v [wd]
  (loop [word-work (helper/split-word-to-letters wd)
         out-work []]
    (cond (empty? word-work) (flatten out-work) 
	    (= (count word-work) 1) (str out-work, " ", word-work)
        :else (recur (helper/butfirst-string (helper/butfirst-string word-work))
                     (conj out-work (str " "
                                        (helper/first-string word-work) 
                                        (helper/first-string (helper/butfirst-string word-work))))))))

;; Every: one base case, one recursive case
;; Keep: one base case, two (or more?) recursive cases
(defn keep-three-letter-words-v
  ([sent] (helper/safe-subs (keep-three-letter-words-v sent []) 1))
  ([sent outp]
  (loop [sent-work (helper/split-string-to-words sent) 
         outw []]
    (cond (empty? sent-work) (helper/join-with-spaces outw)
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
  (loop [sent-work (helper/split-string-to-words sent)
         outp []]
    (cond (empty? sent-work) (helper/join-with-spaces (flatten outp)) 
          (= bad-word (first sent-work)) (helper/join-with-spaces (flatten (conj outp (helper/rest-vec sent-work))))
          :else (recur (helper/rest-vec sent-work) (conj outp (first sent-work))))))

;; with vectors!
(defn remove-once-v [bad-word sent]
  (loop [sent-work (helper/split-string-to-words sent) 
         outp []]
    (cond (empty? sent-work) (string/join " " (flatten outp)) 
          (= bad-word (first sent-work)) (string/join " " (flatten (conj outp (helper/rest-vec sent-work))))
          :else (recur (helper/rest-vec sent-work) (conj outp (first sent-work))))))


;;  14.2  
;; > (up 'town)
; (T TO TOW TOWN)
;; This is kind of like every.
;; no need to use vectors for letters, probably more trouble than it is worth
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
          (= the-word (first sent-work)) (recur (rest sent-work) (inc word-count)) 
          :else (recur (rest sent-work) word-count))))

(defn butlast-vec [the-vec]
  (subvec the-vec 0 (dec (count the-vec)))
)
;; come back to this; it does not look like it should pass but it does
(defn remove-dups-v [the-sent]
  (loop [sent-work (helper/split-string-to-words the-sent) 
         outp []]
    (cond (empty? sent-work) (helper/join-with-spaces (reverse (flatten outp))) 
          (> (word-appearances (last sent-work) sent-work) 1 ) (recur (butlast-vec sent-work) outp)
          :else (recur (butlast-vec sent-work) (conj outp (last sent-work))))))

;;  14.4  
;; > (odds '(i lost my little girl))
;; (I MY GIRL)
;; This is like "keep" again
;; This needs a helper
(defn odds-v  [the-sent]
  (loop [sent-work (helper/split-string-to-words the-sent) 
         outp []
         counter 1]
    (cond (empty? sent-work) (helper/join-with-spaces (flatten outp)) 
          (odd? counter) (recur (rest sent-work) (conj outp (first sent-work)) (inc counter))
          :else (recur (rest sent-work) outp (inc counter)))))

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
(defn member-v [the-word the-sent]
  (loop [sent-work (helper/split-string-to-words the-sent) 
         found? false]
    (cond (empty? sent-work) found?
          (= the-word (first sent-work)) true
          :else (recur (rest sent-work) found?))))

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
    (cond (= count 0) outp
          :else (recur (conj outp the-string) (dec count)))))

(defn expand-v [the-sent]
  (loop [sent-work (helper/split-string-to-words the-sent) 
         outp []]
    (cond (empty? sent-work) (helper/join-with-spaces (flatten outp)) 
          (not (helper/is-string-number? (first sent-work))) (recur (rest sent-work) (conj outp (first sent-work)))
          :else  (recur (helper/rest-vec (rest sent-work)) 
                        (conj outp
                             (print-n-times (Double/parseDouble (first sent-work))  
                                            (second sent-work)))))))

;; 14.9  Write a procedure called location that takes two arguments, a word and a sentence. 
;; It should return a number indicating where in the sentence that word can be found. 
;; If the word isn't in the sentence, return #f. 
;; If the word appears more than once, return the location of the first appearance.
;; > (location 'me '(you never give me your money))
;; 4
;; Shouldn't it return 0 if the word is not found? I don't like the idea that it returns a number OR a boolean.
;; That kind of goes against his advice in chapter 12.
;; Sort of like accumulate, but like member? you do not have to go all the way through.

(defn location-v [word sent]
  (loop [sent-work (helper/split-string-to-words sent) 
         counter 1]
    (cond (empty? sent-work) 0 
          (= word (first sent-work)) counter
          :else (recur (rest sent-work) (inc counter)))))

;; 14.10  Write the procedure count-adjacent-duplicates that takes a sentence as an argument 
;; and returns the number of words in the sentence that are immediately followed by the same word:
;; > (count-adjacent-duplicates '(y a b b a d a b b a d o o))
;; 3
;; > (count-adjacent-duplicates '(yeah yeah yeah))
;; 2
;; I think this is like accumulate
;; (count-adjacent-dups-r '(y a b b a d a b b a d o o) 0)
(defn count-adjacent-dups-v [sent]
  (loop [sent-work (helper/split-string-to-words sent) 
         counter 0]
    (cond (= (count sent-work) 1) counter
          (= (first sent-work) (second sent-work)) (recur (rest sent-work) (inc counter))
          :else (recur (rest sent-work) counter))))

;; 14.11  Write the procedure remove-adjacent-duplicates that takes a sentence as argument 
;; and returns the same sentence but with any word that's immediately followed by the same word removed:
;; > (remove-adjacent-duplicates '(y a b b a d a b b a d o o))
;; (Y A B A D A B A D O)
;; > (remove-adjacent-duplicates '(yeah yeah yeah))
;; (YEAH)
;; This is like keep
;; (remove-adj-dups-r '(y a b b a d a b b a d o o) '())
;; (remove-adj-dups-r '(yeah yeah yeah) '())
;; their strings have no commas, so why not just do it all in vectors?
(defn remove-adjacent-dups-v [the-sent]
  (loop [sent-work (helper/split-string-to-words the-sent) 
         outp []]
    (cond (= 0 (count sent-work)) (helper/join-with-spaces outp) 
          (and (= (count sent-work) 1) (= (first sent-work) (last outp))) (helper/join-with-spaces outp) 
          (= (first sent-work) (second sent-work)) (recur (helper/rest-vec (rest sent-work)) (conj outp (first sent-work)))
          :else (recur (helper/rest-vec sent-work) (conj outp (first sent-work))))))

;; 14.12  Write a procedure progressive-squares? that takes a sentence of numbers as its argument. 
;; It should return #t if each number (other than the first) is the square of the number before it:
;; This is accumulate
(defn progressive-squares? [nums]
  (loop [num-work nums
         outp false]
    (cond (or (= (count num-work) 0) (= (count num-work) 1)) outp
          (= (helper/square (first num-work)) (second num-work)) (recur (rest num-work) true)
          :else false)))

;; 14.14  Write a predicate same-shape? that takes two sentences as arguments. 
;; It should return #t if two conditions are met: 
;; The two sentences must have the same number of words, 
;; and each word of the first sentence must have the same number of letters as the word in the corresponding position in the second sentence.
;; not tail-recursive

(defn same-shape? [first-sent second-sent]
  (loop [first-work (helper/split-string-to-words first-sent)
         second-work (helper/split-string-to-words second-sent)
         outp false]
    (cond (not (= (count first-work) (count second-work))) false
          (and (empty? first-work) (empty? second-work)) outp
          (= (count (first first-work)) (count (first second-work))) (recur (rest first-work) (rest second-work) true)
          :else false)))

;; 14.15  Write merge, a procedure that takes two sentences of numbers as arguments. 
;; Each sentence must consist of numbers in increasing order. 
;; Merge should return a single sentence containing all of the numbers, in order. 
;; (We'll use this in the next chapter as part of a sorting algorithm.)
;; > (merge '(4 7 18 40 99) '(3 6 9 12 24 36 50))
;; (3 4 6 7 9 12 18 24 36 40 50 99)
;; I will just assume that all of the numbers are already sorted
;; And that there are no numbers in both lists
;; Two into one: accumulate
;; (merge-r '(4 7 18 40 99) '(3 6 9 12 24 36 50) '())
;; not tail recursive, lots of conditions
(defn merge-v [nums-a nums-b]
  (loop [first-nums nums-a
         second-nums nums-b
         outp []]
    (cond (and (empty? first-nums) (empty? second-nums)) (vec outp)
          (empty? first-nums) (vec (flatten (conj outp second-nums)))
          (empty? second-nums) (vec (flatten (conj outp first-nums)))
          (< (first first-nums) (first second-nums)) (recur (rest first-nums) second-nums (conj outp (first first-nums)))
          (< (first second-nums) (first first-nums)) (recur first-nums (rest second-nums) (conj outp (first second-nums)))
          :else outp)))


