(ns twitter-retriever.work
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojurewerkz.propertied.properties :as p])
  (:require [clojure.java.io :as io])
  (:require [environ.core :refer [env]])
  (:require [clojure.pprint :as pp])
  (:require [twitter-retriever.rdbms :as rdbms])
  (:require [twitter-retriever.actions :as actions])
  (:require [twitter.oauth :refer [make-oauth-creds]])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-a" "--action ACTION" "An action to be performed: --create-user or --retrieve-tweets"
    :id :action
    ; :parse-fn #(Integer/parseInt %)
    ; :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]
    ]
   ["-u" "--user USER" "Name of twitter user"
    :id :user
    ;:validate [#(> (count %) 10) "String must be at least 10 chars (like ending with .properties)"]
    ]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main [& args]
  (println "In work")
  (def arg-map (parse-opts args cli-options))
  (println "here is arg-map:", arg-map)
  (println "here is options: ", (:options arg-map))
  (println "here is action: ",  (:action (:options arg-map)))
  
  (def user-name (:user (:options arg-map)))
  (println "Here is user-name: ", user-name)
  ;; put some sort of checking in here

  (def database-url (env :database-url))
  (println "here is database-url: ", database-url)
  (def client-token (env :client-token))
  (println "Here is client-token: ", client-token)
  (println "env is a", (class env))
  (println "does env have a key named client-token: ", (contains? env :client-token))
  ; (println "Here are the keys for env: ", (keys env))
  (def my-creds (make-oauth-creds (env :app-consumer-key)
                                (env :app-consumer-secret)
                                (env :user-access-token)
                                (env :user-access-token-secret)))
  (println "Here is my-creds", my-creds)
  (let [action (:action (:options arg-map))]
    (case action
      "create-user" (do
                      (println "You want to create a user")
                      (actions/create-user user-name my-creds))
    "retrieve-tweets" (do
                        (println "You want to retrieve tweets"))
    (do
      (println "Action ", action, " not specified.")
      (println "Possible actions: create-user retrieve-tweets"))))
  (println "Done")
)

