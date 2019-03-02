(ns simply-clojure.helper
  (:require [clojure.string :as str])
)

(defn queue
  ([] (clojure.lang.PersistentQueue/EMPTY))
  ([coll]
    (reduce conj clojure.lang.PersistentQueue/EMPTY coll)))

(defn square [x]
  (* x x))

(defn butlast-q [my-queue]
  (reverse (pop (reverse my-queue))))

(defn string-is-word [the-string]
  (if (nil? (str/index-of the-string " "))
    true
    false))

(defn vowel? [the-string]
  (cond (not (string? the-string)) false
        (contains? #{"A", "E", "I", "O", "U", "a", "e", "i", "o", "u"} the-string) true
        :else false))

(defn butfirst-string [the-string]
  (if (string-is-word the-string)
    (subs the-string 1)
    (subs the-string (inc (str/index-of the-string " ")))))

(defn first-string [the-string]
  (if (string-is-word the-string)
    (subs the-string 0 1)
    (subs the-string 0 (str/index-of the-string " "))))

(defn last-string [the-string]
  (if (string-is-word the-string)
    (first-string (str/reverse the-string))
    (str/reverse (first-string (str/reverse the-string)))))

(defn butlast-string [the-string]
  (str/reverse (butfirst-string (str/reverse the-string))))

(defn second-string [the-string]
  (cond (not (string? the-string)) the-string 
        (string-is-word the-string) the-string
        (string-is-word (butfirst-string the-string)) (butfirst-string the-string)
        :else (first-string (butfirst-string the-string))))

(defn is-string-number? [s-num]
  (try
    (Double/parseDouble s-num)
    true
    (catch Exception Ex
      false)))

(defn number-tween-inclusive [number lower upper]
  (and (>= number lower) (<= number upper)))

;; that slick string splitter from https://clojuredocs.org/clojure.string/split
(defn split-word-to-letters [the-word]
  (str/split the-word #""))

(defn split-string-to-words [the-word]
  (str/split the-word #" "))

;; from http://clojure-doc.org/articles/language/functions.html
;; Is this in any library? It seems pretty useful.
(defn round
  "Round down a double to the given precision (number of significant digits)"
  [^double d ^long precision]
  {:pre [(not (nil? d)) (not (nil? precision))]}
  (let [factor (Math/pow 10 precision)]
    (/ (Math/floor (* d factor)) factor)))




