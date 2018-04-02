(ns twitter-retriever.work
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.java.io :as io])
  (:require [environ.core :refer [env]])
  (:require [clojure.pprint :as pp])
  (:require [twitter-retriever.rdbms :as rdbms])
  (:require [twitter-retriever.actions :as actions])
  (:require [twitter.oauth :refer [make-oauth-creds]])
  (:require [clj-time.coerce :as timec])
  (:require [clj-time.local :as timel])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-a" "--action ACTION" "An action to be performed: --create-user or --retrieve-tweets"
    :id :action
    ; :parse-fn #(Integer/parseInt %)
    ; :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]
    ]
   ["-u" "--user USER" "Name of twitter user's tweets to get"
    :id :user
    ;:validate [#(> (count %) 10) "String must be at least 10 chars (like ending with .properties)"]
    ]
   ["-o" "--oauth NAME" "Name of twitter user that has the OAuth creds you will be using"]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main [& args]
  (println "In work")
  (def arg-map (parse-opts args cli-options))
  (println "here is arg-map:", arg-map)
  (println "here is options: ", (:options arg-map))
  (println "here is action: ",  (:action (:options arg-map)))
  (def batch-time (timec/to-timestamp (timel/local-now)))
  
  (def user-name (:user (:options arg-map)))
  (println "Here is user-name: ", user-name)
  ;; put some sort of checking in here

  (def database-url (env :database-url))
 
  (def twitter-auth (rdbms/get-twitter-auth {:twitter_auth_user (:oauth (:options arg-map))}))
    
  ;; with twitter API, sometimes map keys use underscores, instead of our trusted hyphen
  (def my-creds (make-oauth-creds (:app_consumer_key twitter-auth)
                                  (:app_consumer_secret twitter-auth)
                                  (:user_access_token twitter-auth)
                                  (:user_access_token_secret twitter-auth)))
  (println "Here is my-creds", my-creds)
  (def num-user (rdbms/check-user {:screen_name user-name}))
  (println "Here is num-user: ", num-user)
  (def user-count (:count num-user))
  (if (= user-count 0)
    (do
      (println "You want to create a user")
      (actions/create-user user-name my-creds batch-time))
    (do
      (println "You want to get more tweets")
      (actions/insert-more-tweets user-name my-creds batch-time)))
  
  (println "Done"))

