(ns twitter-retriever.work
  (:require [clj-time.coerce   :as timec]
            [clj-time.local    :as timel]
            [clojure.tools.cli :as cli]
            [environ.core      :as environ]
            [mount.core        :as mount]
            [twitter-retriever.actions :as actions]
            [twitter-retriever.process :as process]
            [twitter-retriever.rdbms   :as rdbms]
            [twitter.oauth   :as twitter-oauth])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-u" "--user USER" "Name of twitter user's tweets to get"
    :id :user
    ]
   ["-o" "--oauth NAME" "Name of twitter user that has the OAuth creds you will be using"]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main [& args]
  (println "In work")
  (mount/start)
  (let [arg-map    (cli/parse-opts args cli-options)
        batch-time (timec/to-timestamp (timel/local-now))
        user-name  (:user (:options arg-map))]
    (println "here is arg-map:", arg-map)
    (println "here is options: ", (:options arg-map))
    
    ;; put some sort of checking in here
    (def twitter-auth (rdbms/get-twitter-auth {:twitter_auth_user (:oauth (:options arg-map))}))
    (println "Here is twitter-auth: ", twitter-auth)
    ;; with twitter API, sometimes map keys use underscores, instead of our trusted hyphen
    (def my-creds (twitter-oauth/make-oauth-creds (:app_consumer_key    twitter-auth)
                                                  (:app_consumer_secret twitter-auth)
                                                  (:user_access_token   twitter-auth)
                                                  (:user_access_token_secret twitter-auth)))
    
    ;; does the user exist in DB?
    (if (= 0 (:count (rdbms/check-for-user-count {:screen_name user-name})))
      (do
        (println "You want to create a user")
        (actions/create-user user-name my-creds batch-time))
      (do
        (println "You want to get more tweets")
        (actions/insert-more-tweets user-name my-creds batch-time)))
    (process/create-processed-file user-name batch-time))
  (mount/stop)
  (println "All done")
  (System/exit 0))

