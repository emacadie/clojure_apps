(ns twitter-retriever.work
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io]
            [environ.core :refer [env]]
            [clojure.pprint :as pp]
            [twitter-retriever.rdbms :as rdbms]
            [twitter-retriever.actions :as actions]
            [twitter-retriever.process :as process]
            [twitter.oauth :refer [make-oauth-creds]]
            [clj-time.coerce :as timec]
            [clj-time.local :as timel])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-u" "--user USER" "Name of twitter user's tweets to get"
    :id :user
    ;:validate [#(> (count %) 10) "String must be at least 10 chars (like ending with .properties)"]
    ]
   ["-o" "--oauth NAME" "Name of twitter user that has the OAuth creds you will be using"]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main [& args]
  (println "In work")
  
  (let [arg-map (parse-opts args cli-options)
        batch-time (timec/to-timestamp (timel/local-now))
        user-name (:user (:options arg-map))]
    (println "here is arg-map:", arg-map)
    (println "here is options: ", (:options arg-map))
    ; (def )
  
    ; (def user-name (:user (:options arg-map)))
    ;; put some sort of checking in here

    (def database-url (env :database-url))
 
    (def twitter-auth (rdbms/get-twitter-auth {:twitter_auth_user (:oauth (:options arg-map))}))
    
    ;; with twitter API, sometimes map keys use underscores, instead of our trusted hyphen
    (def my-creds (make-oauth-creds (:app_consumer_key    twitter-auth)
                                    (:app_consumer_secret twitter-auth)
                                    (:user_access_token   twitter-auth)
                                    (:user_access_token_secret twitter-auth)))
                                        ; (println "Here is my-creds", my-creds)
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
    (process/create-processed-file user-name batch-time)
    )
  (println "All done"))

