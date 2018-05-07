(ns fwpd.core
  (:gen-class))

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int [str]
  "If str is an int, return the num, otherwise return nil"
  (try
    (Integer. str)
    (catch Exception e nil)))

(def conversions {:name identity :glitter-index str->int})

(defn convert [vamp-key value]
  (try 
    ((get conversions vamp-key) value)
    (catch Exception e nil)))

(defn parse 
  "Convert a CSV into rows of columns"
  [str]
  (map #(clojure.string/split %1 #",")
       (clojure.string/split str #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

; (map vector vamp-keys ["Ed" "10"])
; gives something like
; ([:name "Ed"] [:glitter-index "10"])
; so you could make key/value pairs from vectors
; IF everything is in the right place

(defn glitter-filter [minimum-glitter records]
  (filter #(>= (:glitter-index %1) minimum-glitter) records))

;; (append-suspect glit-vec {:name "John Doe" :glitter-index 3})
;; (def glit-vec (glitter-filter 4 (mapify (parse (slurp filename)))))
(defn append-suspect [sus-list new-sus]
  "This will put new suspect at the end; conj would put at the front"
  (concat sus-list (list new-sus)))

(defn keys-match [map1 map2]
  (if (= 0 (compare (vec (sort (keys map1))) (vec (sort (keys map2)))))
    true
    false))

;; (validate-suspect conversions {:name "Edward" :glitter-index "3r"})
(defn validate-suspect [key-map suspect]
  ;; key-map could be conversions above
  (if (and (keys-match key-map suspect)
             (not-any? nil? (map (fn [[key value]] (convert key value))
                                 suspect))) 
    true
    false))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

