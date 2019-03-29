(ns simply-clojure.helper
  (:require [clojure.string :as string]))

(defn queue
  ([] (clojure.lang.PersistentQueue/EMPTY))
  ([coll]
    (reduce conj clojure.lang.PersistentQueue/EMPTY coll)))

(defn square [x]
  (* x x))

(defn butlast-q [my-queue]
  (reverse (pop (reverse my-queue))))

(defn string-is-word [the-string]
  (if (nil? (string/index-of the-string " "))
    true
    false))

(defn vowel? [the-string]
  (cond (not (string? the-string)) false
        (contains? #{"A", "E", "I", "O", "U", "a", "e", "i", "o", "u"} the-string) true
        :else false))

(defn butfirst-word [the-string]
  (if (string-is-word the-string)
    (subs the-string 1)
    the-string))

(defn butfirst-string [the-string]
  (if (string-is-word the-string)
    ""
    (subs the-string (inc (string/index-of the-string " ")))))

(defn first-string [the-string]
  (if (string-is-word the-string)
    the-string
    (subs the-string 0 (string/index-of the-string " "))))

(defn first-word [the-string]
  (if (string-is-word the-string)
    (subs the-string 0 1)
    the-string))

(defn last-string [the-string]
  (if (string-is-word the-string)
    the-string
    (string/reverse (first-string (string/reverse the-string)))))

(defn last-word [the-string]
  (if (string-is-word the-string)
    (first-word (string/reverse the-string))
    the-string))

(defn butlast-string [the-string]
  (string/reverse (butfirst-string (string/reverse the-string))))

(defn butlast-word [the-string]
  (string/reverse (butfirst-word (string/reverse the-string))))

(defn second-string [the-string]
  (cond (not (string? the-string)) the-string 
        (string-is-word the-string) the-string
        (string-is-word (butfirst-string the-string)) (butfirst-string the-string)
        :else (first-string (butfirst-string the-string))))

;; I call butfirst on butfirst a few times
(def butfirst-two-string (comp butfirst-string butfirst-string))

(def butfirst-two-word (comp butfirst-word butfirst-word))

;; see if this exists somewhere
(defn is-string-number? [s-num]
  (try
    (Double/parseDouble s-num)
    true
    (catch Exception Ex
      false)))

(defn string-to-double [s-num]
  (if is-string-number? s-num
      (try
        (Double/parseDouble s-num)
        (catch Exception Ex))))

(defn string-to-int [s-num]
  (if (is-string-number? s-num)
      (do
        (try
          (Integer/parseInt s-num)
          (catch Exception Ex)))
      0))

(defn remove-ending-space-from-string [str-work]
  (if (string/ends-with? str-work " ")
    (subs str-work 0 (dec (.length str-work)))
    str-work))

(defn remove-starting-space-from-string [str-work]
  (if (string/starts-with? str-work " ")
    (subs str-work 1 (.length str-work))
    str-work))

(def remove-surrounding-spaces-from-string (comp remove-starting-space-from-string remove-ending-space-from-string))

(defn safe-subs
  ([the-str begin]
   (if (> begin (.length the-str))
     ""
     (subs the-str begin)))
  ([the-str begin end]
   (cond (> begin (.length the-str)) ""
         (> end (.length the-str)) the-str
         :else (subs the-str begin end))))

(defn number-tween-inclusive [number lower upper]
  (and (>= number lower) (<= number upper)))

;; that slick string splitter from https://clojuredocs.org/clojure.string/split
(defn split-word-to-letters [the-word]
  (string/split the-word #""))

(defn split-string-to-words [the-sent]
  (string/split the-sent #" "))

(defn join-with-spaces [the-str]
  (string/join " " the-str))

(defn butlast-vec [the-vec]
  (subvec the-vec 0 (dec (count the-vec))))

(defn phone-letter [letter]
  (let [lletter (string/lower-case letter)]
      (cond (contains? #{"a" "b" "c"}     lletter) 2
            (contains? #{"d" "e" "f"}     lletter) 3
            (contains? #{"g" "h" "i"}     lletter) 4
            (contains? #{"j" "k" "l"}     lletter) 5
            (contains? #{"m" "n" "o"}     lletter) 6
            (contains? #{"p" "q" "r" "s"} lletter) 7
            (contains? #{"t" "u" "v"}     lletter) 8
            (contains? #{"w" "x" "y" "z"} lletter) 9
            :else 0)))

(def rest-vec (comp vec rest))

;; from http://clojure-doc.org/articles/language/functions.html
;; Is this in any library? It seems pretty useful.
(defn round
  "Round down a double to the given precision (number of significant digits)"
  [^double d ^long precision]
  {:pre [(not (nil? d)) (not (nil? precision))]}
  (let [factor (Math/pow 10 precision)]
    (/ (Math/floor (* d factor)) factor)))




