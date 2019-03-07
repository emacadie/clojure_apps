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
(defn letterwords [letter the-sent]
  (filter #(str/includes? %1 letter) (helper/split-string-to-words the-sent)))

; 9.8  Suppose we're writing a program to play hangman. 
; In this game one player has to guess a secret word chosen by the other player, one letter at a time. 
; You're going to write just one small part of this program: 
; a procedure that takes as arguments the secret word and the letters guessed so far, 
; returning the word in which the guessing progress is displayed 
;; by including all the guessed letters along with underscores for the not-yet-guessed ones:
; Hint: You'll find it helpful to use the following procedure that determines how to display a single letter: 
(defn hang-letter [letter guesses]
  (if (contains? (set (helper/split-word-to-letters guesses) ) letter)
    letter
    "_")) 

(defn hang [word guesses]
  (map #(hang-letter %1 guesses) (helper/split-word-to-letters word)))

;; 9.9  Write a procedure common-words that takes two sentences as arguments 
;; and returns a sentence containing only those words that appear both in the first sentence and in the second sentence.
;; keep and every? How heavy!!
;; from chapter 8:
;; For instance, the keep function takes a predicate and a sentence as arguments. 
;; It returns a sentence containing only the words of the argument sentence for which the predicate is true.
;; every takes a function, and a collection (or sentence)
(defn common-words [first-sen second-sen]
  (let [first-set (set (helper/split-string-to-words first-sen))]
    (filter #(contains? first-set %1) (helper/split-string-to-words second-sen))))

;; 9.10  In Chapter 2 we used a function called appearances that returns 
;; the number of times its first argument appears as a member of its second argument. 
;; Implement appearances.
;; this would be accumulate?
;; no this is keep again
;; Perhaps this could be a multi-method?
(defn appearances-string [part whole]
  (if (helper/string-is-word whole)
    (count (filter #(= part %1) (helper/split-word-to-letters whole)))
    (count (filter #(= part %1) (helper/split-string-to-words whole)))))

(defmulti my-appearances (fn [the-map] (:type the-map)))

(defmethod my-appearances :sentence
  [the-map]
  (count (filter #(= (:part the-map) %1) (helper/split-string-to-words (:whole the-map)))))

(defmethod my-appearances :word [the-map]
  (count (filter #(= (:part the-map) %1) (helper/split-word-to-letters (:whole the-map)))))

(defmethod my-appearances :vector [the-map]
  (count (filter #(= (:part the-map) %1) (:whole the-map))))

;; 9.11  Write a procedure unabbrev that takes two sentences as arguments. 
;; It should return a sentence that's the same as the first sentence, 
;; except that any numbers in the original sentence should be replaced with words from the second sentence. 
;; A number 2 in the first sentence should be replaced with the second word of the second sentence, a 6 with the sixth word, and so on.

;; > (unabbrev '(john 1 wayne fred 4) '(bill hank kermit joey))
;; (JOHN BILL WAYNE FRED JOEY)

;; > (unabbrev '(i 3 4 tell 2) '(do you want to know a secret?))
;; (I WANT TO TELL YOU)

;; every something-with-second-sen first-sen
;; use item somehow: (item 4 '(this is a sentence))
;; he said not to use helper functions, but in 9.8 he did, so I will too
;; or we will have a nasty lambda
(defn- unabbrev-work [first-sent second-sent]
  (let [second-vec (helper/split-string-to-words second-sent)]
    (map (fn [x]
           (if (helper/is-string-number? x)
             (nth second-vec (dec (Double/parseDouble x)) " ")
             x)) 
         (helper/split-string-to-words first-sent))))

(defn unabbrev [first-sen second-sen]
  (clojure.string/join " " (unabbrev-work first-sen second-sen)))

;;  9.12  Write a procedure first-last whose argument will be a sentence. 
;; It should return a sentence containing only those words in the argument sentence whose first and last letters are the same:
; > (first-last '(california ohio nebraska alabama alaska massachusetts))
; (OHIO ALABAMA ALASKA)

(defn first-last [the-sent]
  (filter #(= (get %1 0) (get %1 (dec (count %1)))) the-sent))

;;  9.13  Write a procedure compose that takes two functions f and g as arguments. 
;; It should return a new function, the composition of its input functions, which computes f(g(x)) when passed the argument x.
;> ((compose sqrt abs) -25)
; 5
; > (define secondf (compose first bf))
; > (secondf '(higher order function))
; ORDER
;; maybe I am getting the hang of this

;; they say only one arg, so that is what I will do.
;; I might look at source for comp later
(defn compose [first-func second-func]
  (fn [x]
    (first-func (second-func x))))

;; I am totally failing on this one.
;; Maybe you cannot make a function with variable arguments. 
;; And Clojure does not handle types the same way Scheme does.
;; 2019-02-26: Skipping for now.

;; 9.14  Write a procedure substitute that takes three arguments, two words and a sentence. 
;; It should return a version of the sentence, but with every instance of the second word replaced with the first word:

;> (substitute 'maybe 'yeah '(she loves you yeah yeah yeah))
; (SHE LOVES YOU MAYBE MAYBE MAYBE)

(defn- substitute-word [first-word second-word the-sent]
  (map (fn [x]
         (if (= x second-word)
           first-word
           x))
       (helper/split-string-to-words the-sent)))

(defn substitute [first-word second-word the-sent]
  (str/join " " (substitute-word first-word second-word the-sent)))

;; 9.15 Many functions are applicable only to arguments in a certain domain and result in error messages if given arguments outside that domain. 
;; For example, sqrt may require a nonnegative argument in a version of Scheme that doesn't include complex numbers. 
;; (In any version of Scheme, sqrt will complain if its argument isn't a number at all!) 
;; Once a program gets an error message, it's impossible for that program to continue the computation.

; Write a procedure type-check that takes as arguments a one-argument procedure f and a one-argument predicate procedure pred. 
;; Type-check should return a one-argument procedure that first applies pred to its argument 
; if that result is true, the procedure should return the value computed by applying f to the argument; 
; if pred returns false, the new procedure should also return #f:
; > (define safe-sqrt (type-check sqrt number?))
; > (safe-sqrt 16)
; 4
; > (safe-sqrt 'sarsaparilla)
; #F
;; only works with one arg functions
(defn type-check [the-func the-pred]
  (fn [the-arg]
    (if (the-pred the-arg)
      (the-func the-arg)
      false)))

;;  9.16  In the language APL, most arithmetic functions can be applied either to a number, 
; with the usual result, or to a vector—the APL name for a sentence of numbers—in which case 
; the result is a new vector in which each element is the result of applying the function to the corresponding element of the argument. 
; For example, the function sqrt applied to 16 returns 4 as in Scheme, 
; but sqrt can also be applied to a sentence such as (16 49) and it returns (4 7).

; Write a procedure aplize that takes as its argument a one-argument procedure whose domain is numbers or words. 
; It should return an APLized procedure that also accepts sentences:

; > (define apl-sqrt (aplize sqrt))
; > (apl-sqrt 36)
; 6
; > (apl-sqrt '(1 100 25 16))
; (1 10 5 4)

(defn aplize [the-func]
  (fn [the-arg]
    (if (coll? the-arg)
      (map the-func the-arg)
      (the-func the-arg))))

;; 9.17  Write keep in terms of every and accumulate. 

(defn my-conj [the-coll x]
  (if (nil? x)
    the-coll
    (conj the-coll x)))

(defn my-keep [the-pred the-coll]
  (reverse 
   (reduce my-conj 
           (map (fn [x]
                  (when (the-pred x)
                    x))
                the-coll))))


