(ns twitter-retriever.work
  (:require [clj-time.coerce   :as timec]
            [clj-time.local    :as timel]
            [clojure.edn :as edn]
            [clojure.tools.cli :as cli]
            [cprop.core :refer [load-config]]
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
   ["-c" "--configfile NAME" "Name of twitter user that has the OAuth creds you will be using"]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn load-config2 [path]
  (println "loading config from" path)
  (-> path
      slurp
edn/read-string))

(defn -main [& args]
  (println "In work")
  (mount/start)
  (let [arg-map    (cli/parse-opts args cli-options)
        batch-time (timec/to-timestamp (timel/local-now))
        user-name  (:user (:options arg-map))]
    (println "here is arg-map:", arg-map)
    (def config-map (load-config :file (:configfile (:options arg-map))))
    (mount/defstate config-map2 :start (load-config2 (:configfile (:options arg-map))))
    (println "Here in config-map: " config-map)
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
  
  (println "Here is config-map2: " config-map2)
  (mount/stop)
  (println "JVM Version: "(System/getProperty "java.vm.version"))
  (println "conf: "(System/getProperty "conf"))
  (println "sysProp: "(System/getProperty "sysProp"))
  (println "browser: "(System/getProperty "browser"))
  
  (println "All done")
  (System/exit 0))

