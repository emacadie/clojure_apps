(ns info.shelfunit.funcjava.chap01)

(defn the-long-way []
  (println "in info.shelfunit.funcjava.chap01.the-long-way")
  (def prices [ 10, 30, 17, 20, 15, 18, 45, 12 ])
  (def over-20 (filter #(> %1 20) prices))
  (def reduced-prices (map #(* %1 0.9) over-20))
  (reduce + reduced-prices))

(defn the-one-liner []
  (println "in info.shelfunit.funcjava.chap01.the-one-liner")
  (def prices [ 10, 30, 17, 20, 15, 18, 45, 12 ])
  (reduce + (map #(* %1 0.9) (filter #(> %1 20) prices))))

(defn the-threaded-way []
  (println "in info.shelfunit.funcjava.chap01.the-threaded-way")
  (def prices [ 10, 30, 17, 20, 15, 18, 45, 12 ])
  (->> prices
       (filter #(> %1 20))
       (map #(* %1 0.9))
           (reduce +)))
  

(defn -main [& args]
  (println "in info.shelfunit.funcjava.chap01.-main")
  (println the-threaded-way))
