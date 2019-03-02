(ns simply-clojure.chapter08
  (:require [clojure.string :as str]
            [simply-clojure.helper :as helper]))
;; Higher order functions
;; every -> map
;; keep -> filter
;; accumulate -> reduce

;; 8.4  Write a procedure choose-beatles that takes a predicate function as its argument 
;; and returns a sentence of just those Beatles (John, Paul, George, and Ringo) that satisfy the predicate. 
;; so this sounds like filter/keep
(defn ends-vowel? [word]
  (helper/vowel? (helper/last-string word)))

(defn even-count? [word]
  (even? (count word)))

(defn choose-beatles [the-fn]
  (filter the-fn ["John" "Paul" "George" "Ringo"]))

(def sorted-beatles-set (sorted-set "John" "Paul" "George" "Ringo"))

(defn choose-beatles-set [the-fn]
  (filter the-fn sorted-beatles-set))
;; do again as a set

;; 8.5  Write a procedure transform-beatles that takes a procedure as an argument, 
;; applies it to each of the Beatles, and returns the results in a sentence:
;; this sounds like map, aka every
(defn amazify [name]
  (str "the amazing " name))

(defn transform-beatles [the-fn]
  (map the-fn ["John" "Paul" "George" "Ringo"]))

(defn transform-beatles-set [the-fn]
  (map the-fn sorted-beatles-set))

;; 8.6  When you're talking to someone over a noisy radio connection, 
;; you sometimes have to spell out a word in order to get the other person to understand it. 
;; But names of letters aren't that easy to understand either, 
;; so there's a standard code in which each letter is represented 
;; by a particular word that starts with the letter. For example, instead of "B" you say "bravo."
;; Write a procedure words that takes a word as its argument and returns a sentence of the names of the letters in the word:
; > (words 'cab)
;; (CHARLIE ALPHA BRAVO)
;; (You may make up your own names for the letters or look up the standard ones if you want.)
;; Hint: Start by writing a helper procedure that figures out the name for a single letter.
;; a map would come in handy here
;; to get the letters of a word:
;; (define (single letter) (word letter))
(def nato-letter-map {:a :alpha, :b :bravo, :c :charlie, :d :delta, :e :echo})

;; I could go to the end, but what's the point? 
(defn get-nato-letter [letter]
  ((keyword letter) nato-letter-map))

;; that slick string splitter from https://clojuredocs.org/clojure.string/split
(defn words [the-word]
  (map get-nato-letter (helper/split-word-to-letters the-word)))

;;  8.7  [14.5][9] Write a procedure letter-count that takes a sentence as its argument and returns the total number of letters in the sentence:
;; > (letter-count '(fixing a hole))
;; 11
;; sounds like reduce, aka accumulate
;; Just assume we get a string
(defn letter-count [the-sent]
  (reduce + (map count (helper/split-string-to-words the-sent))))

;; 8.8  [12.5] Write an exaggerate procedure which exaggerates sentences:
;; It should double all the numbers in the sentence, and it should replace "good" with "great," "bad" with "terrible," and anything else you can think of.
;; sounds like every with a dash of condx

(defn do-great-stuff [the-word]
  (cond (= the-word "good") (str "great ")
        (= the-word "bad") (str "terrible ")
        (helper/is-string-number?) (str (* 2 (Double/parseDouble the-word)) " ")
        :else (str the-word " ")))

(defn exaggerate [the-sent]
  (map do-great-stuff (helper/split-string-to-words the-sent)))

;; 8.9  What procedure can you use as the first argument to every so that for any sentence used as the second argument, every returns that sentence?
;; I tried "word" and it worked
;; What procedure can you use as the first argument to keep so that for any sentence used as the second argument, keep returns that sentence?
;; I tried "word?" and it worked
;; What procedure can you use as the first argument to accumulate so that for any sentence used as the second argument, accumulate returns that sentence?
;; I tried "sentence" and it worked

;; 8.10  Write a predicate true-for-all? that takes two arguments, a predicate procedure and a sentence. 
; It should return #t if the predicate argument returns true for every word in the sentence.
(defn true-for-all? [pred the-sent]
  (cond (= (count the-sent) (count (filter pred the-sent))) true 
        :else false))

;; 8.11  [12.6] Write a GPA procedure. It should take a sentence of grades as its argument and return the corresponding grade point average:
; (gpa '(A A+ B+ B))
;; 3.67

;; Hint: write a helper procedure base-grade that takes a grade as argument and returns 0, 1, 2, 3, or 4, 
;; and another helper procedure grade-modifier that returns −.33, 0, or .33, depending on whether the grade has a minus, a plus, or neither.
(def letter-grade-map {:a 4, :b 3, :c 2, :d 1})

(defn base-grade [grade]
  ((keyword (str/lower-case (helper/first-string grade))) letter-grade-map 0))

(defn modify-grade [grade]
  (cond (= (helper/last-string grade) "+") 0.33
        (= (helper/last-string grade) "-") -0.33
        :else 0))

(defn convert-grade-to-num [grade]
  (+ (base-grade grade) (modify-grade grade)))

(defn gpa [grades]
  (/ (reduce + 0 (map convert-grade-to-num grades)) (count grades)))

;; 8.12  [11.2] When you teach a class, people will get distracted if you say "um" too many times. 
;; Write a count-ums that counts the number of times "um" appears in a sentence:
(defn count-ums [the-sent]
  (count (filter #(= "um" %1) (helper/split-string-to-words the-sent))))

;; 8.13  [11.3] Write a procedure phone-unspell that takes a spelled version of a phone number, such as POPCORN, 
;; and returns the real phone number, in this case 7672676. 
;; You will need to write a helper procedure that uses an 8-way cond expression to translate a single letter into a digit.
;; instead of calling lower-case a lot, I guess I could put this in a let block
(defn phone-letter [letter]
  (let [lletter (str/lower-case letter)]
      (cond (contains? #{"a" "b" "c"} lletter) 2
            (contains? #{"d" "e" "f"} lletter) 3
            (contains? #{"g" "h" "i"} lletter) 4
            (contains? #{"j" "k" "l"} lletter) 5
            (contains? #{"m" "n" "o"} lletter) 6
            (contains? #{"p" "q" "r" "s"} lletter) 7
            (contains? #{"t" "u" "v"} lletter) 8
            (contains? #{"w" "x" "y" "z"} lletter) 9
            :else 0)))

(defn phone-unspell [the-word]
  (map phone-letter (helper/split-word-to-letters the-word)))

;; 8.14  Write the procedure subword that takes three arguments: a word, a starting position number, and an ending position number. 
;; It should return the subword containing only the letters between the specified positions:
;; I will have to look through clojuredoc. 
;; Doesn't Clojure have something that does this?
(defn func-n-times [the-fn input times]
  (loop [the-input input 
         the-times times]
    (if (not (= the-times 0))
      (do
        (recur (the-fn the-input) (dec the-times)))
      the-input)))
;; This is substr, the hard way
(defn subword [word start fin]
  (str (func-n-times helper/butfirst-string 
                     (func-n-times helper/butlast-string 
                                   word 
                                   (- (count word) fin)) 
                     (dec start))))



