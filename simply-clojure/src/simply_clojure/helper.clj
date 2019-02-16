(ns simply-clojure.helper)

(defn queue
  ([] (clojure.lang.PersistentQueue/EMPTY))
  ([coll]
    (reduce conj clojure.lang.PersistentQueue/EMPTY coll)))

(defn butlast-q [my-queue]
  (reverse (pop (reverse my-queue))))

(defn string-is-word [the-string]
  (if (nil? (clojure.string/index-of the-string " "))
    true
    false))

(defn butfirst-string [the-string]
  (if (string-is-word the-string)
    (subs the-string 1)
    (subs the-string (inc (clojure.string/index-of the-string " ")))))

(defn first-string [the-string]
  (if (string-is-word the-string)
    (subs the-string 0 1)
    (subs the-string 0 (clojure.string/index-of the-string " "))))

(defn last-string [the-string]
  (if (string-is-word the-string)
    (first-string (clojure.string/reverse the-string))
    (clojure.string/reverse (first-string (clojure.string/reverse the-string)))))

(defn butlast-string [the-string]
  (clojure.string/reverse (butfirst-string (clojure.string/reverse the-string))))

(defn second-string [the-string]
  (cond (not (string? the-string)) the-string 
        (string-is-word the-string) the-string
        (string-is-word (butfirst-string the-string)) (butfirst-string the-string)
        :else (first-string (butfirst-string the-string))))

(defn number-tween-inclusive [number lower upper]
  (and (>= number lower) (<= number upper)))

;; from http://clojure-doc.org/articles/language/functions.html
;; Is this in any library? It seems pretty useful.
(defn round
  "Round down a double to the given precision (number of significant digits)"
  [^double d ^long precision]
  {:pre [(not (nil? d)) (not (nil? precision))]}
  (let [factor (Math/pow 10 precision)]
    (/ (Math/floor (* d factor)) factor)))




