(ns twitter-retriever.practice.cline
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojurewerkz.propertied.properties :as p])
  (:require [clojure.java.io :as io])
  (:require [environ.core :refer [env]])
  (:require [clojure.pprint :as pp])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-f" "--file-path FILE_PATH" "File path"
    :id :file-path
    :validate [#(> (count %) 10) "String must be at least 10 chars (like ending with .properties)"]]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main [& args]
  (println "In cline")
  (def arg-map (parse-opts args cli-options))
  (println "here is arg-map:", arg-map)
  (println "here is options: ", (:options arg-map))
  (println "here is file-path: ",  (:file-path (:options arg-map)))
  (println "here is file-path: ",  (class (:file-path (:options arg-map))))
  (def prop-path (:file-path (:options arg-map)))
  (println "Here is prop-path: ", prop-path)
  ;; put some sort of checking in here
  (def the-prop (p/load-from (io/file prop-path)))
  (println "Here is the prop: ", the-prop)
  (println "the-prop is a ", (class the-prop))
  (println "what happens if we conver to a map?", (class (p/properties->map the-prop true)))
  ;; perhaps the threading macro can help
  (println "Here it is as a map: ", (p/properties->map the-prop true))
  (def database-url (env :database-url))
  (println "here is database-url: ", database-url)
  (def client-token (env :client-token))
  (println "Here is client-token: ", client-token)
  (println "env is a", (class env))
  (println "does env have a key named client-token: ", (contains? env :client-token))
  (println "Here are the keys for env: ", (keys env))
  ; (def system-map (System/getenv))
  ; (println "Here is system-map: ",  (pp/pprint system-map))
  ; (println "Here it is again")
  ; (doall (for [[k v] system-map] (println k, ": ", v)))
)

