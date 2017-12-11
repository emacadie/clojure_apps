(defproject cheshire-cat "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.3.1"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [cljs-http "0.1.18"]
                 [org.clojure/core.async "0.2.374"]
                 [enfocus "2.1.0"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-cljsbuild "1.0.3"]]
  :ring {:handler cheshire-cat.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}}

    :repositories [["java.net" "https://download.java.net/maven/2"]
                 ["sonatype" {:url "https://oss.sonatype.org/content/repositories/releases"
                              ;; If a repository contains releases only setting
                              ;; :snapshots to false will speed up dependencies.
                              :snapshots false
                              ;; Disable signing releases deployed to this repo.
                              ;; (Not recommended.)
                              :sign-releases true
                              ;; You can also set the policies for how to handle
                              ;; :checksum failures to :fail, :warn, or :ignore.
                              ; :checksum :fail
                              ;; How often should this repository be checked for
                              ;; snapshot updates? (:daily, :always, or :never)
                              ; :update :always
                              ;; You can also apply them to releases only:
                              :releases {:checksum :fail :update :always}}]
               ]  


    :cljsbuild {
               :builds [{
                        :source-paths ["src-cljs"]
                        :compiler {
                                   :output-to "resources/public/main.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
