(ns brave_clojure.chapter05)

;; sum with recur
(defn sum 
  ([vals]
   (sum vals 0))
  ([vals accumulating-total]
   (println "sum w/recur and vals: ", vals, " and accumulating-total: ", accumulating-total)
   (if (empty? vals)
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))

(sum [11 5 2])

;; sum with loop-recur
(defn sum-loop 
  ([vals]
   (sum-loop vals 0))
  ([vals accumulating-total]
   (println "sum-loop w/recur and vals: ", vals, " and accumulating-total: ", accumulating-total)
   (loop [loop-vals  vals
          loop-accum accumulating-total]
     (println "In loop with loop-vals: ", loop-vals, " and loop-accum: ", loop-accum)
       (if (empty? loop-vals)
         loop-accum
         (recur (rest loop-vals) (+ (first loop-vals) loop-accum))))))

