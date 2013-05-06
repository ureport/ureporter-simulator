(ns ureporter-simulator.server
  (:use compojure.core compojure.route ring.adapter.jetty ring.util.codec
        clojure.tools.logging clj-logging-config.log4j)
  (:require
    [compojure.handler :as handler]
    [compojure.route :as route]
    [clojure.data.json :as json]
    [clojure.string :as string])
  (:gen-class))


;; TODO : Use cheshire https://github.com/dakrone/cheshire/blob/master/project.clj
(defn json-response [status data]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (str (json/write-str data) "\n")})

(defn request-uri [request]
  (let [{:keys [request-method scheme uri query-string]} request]
    (format "%s %s:/%s?%s HTTP/1.1" (string/upper-case (name request-method)) (name scheme) uri query-string)))

(defn default-handler [request]
  (let [uri  (request-uri request)]
    (info uri)    
    (json-response 200 {:is ["echo"] 
                        :request uri                        
                        :message "Thanks for pinging me!" })))

(defroutes app
  (GET "/*" [:as request] (default-handler request)))

(set-logger! :pattern "%d [%-15t] %-6p %-40c{1} - %m%n" :level :info)
(defn -main [& m]
  (let [mode (keyword (or (first m) :dev ))
        port (Integer. (get (System/getenv) "PORT" "8084"))]            

    (info (format "Starting ureporter-simulator on port [%s]" port))    
    (run-jetty (handler/site app) {:port port})))
