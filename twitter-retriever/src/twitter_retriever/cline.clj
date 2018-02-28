(ns twitter-retriever.cline
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojurewerkz.propertied.properties :as p])
  (:require [clojure.java.io :as io])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ;; A non-idempotent option
   ["-v" nil "Verbosity level"
     :id :verbosity
     :default 0
     :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ["-f" "--file-path FILE_PATH" "File path"
    :id :file-path]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main [& args]
  (println "In cline")
  (def something (parse-opts args cli-options))
  (println "here is something:", something)
  (println "here is options: ", (:options something))
  (println "here is file-path: ",  (:file-path (:options something)))
  (println "here is file-path: ",  (class (:file-path (:options something))))
  (def prop-path (:file-path (:options something)))
  (println "Here is prop-path: ", prop-path)
  (def the-prop (p/load-from (io/file prop-path)))
  (println "Here is the prop: ", the-prop)
  (println "the-prop is a ", (class the-prop))
  (println "what happens if we conver to a map?", (class (p/properties->map the-prop true)))
  ;; perhaps the threading macro can help
  (println "Here it is as a map: ", (p/properties->map the-prop true)))

