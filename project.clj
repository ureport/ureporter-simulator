(defproject ureporter-simulator "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [ring/ring-jetty-adapter "1.1.7"]
                 [compojure "1.1.5"]
                 [org.clojure/data.json "0.2.2"]
                 [org.clojure/tools.logging "0.2.6"]
                 [clj-logging-config "1.9.10"]
                 [log4j "1.2.16"]
                 ]
  :main ureporter-simulator.server
)
