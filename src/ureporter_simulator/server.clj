(ns ureporter-simulator.server
  (:use compojure.core compojure.route ring.adapter.jetty ring.util.codec)
  (:require
    [compojure.handler :as handler]
    [compojure.route :as route]
    [clojure.data.json :as json]
    [clojure.string :as string])
  (:gen-class))



(defn json-response [status data]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (str (json/write-str data) "\n")})

(defn request-uri [request]
  (let [{:keys [request-method scheme uri query-string]} request]
    (format "%s %s:/%s?%s HTTP/1.1" (string/upper-case (name request-method)) (name scheme) uri query-string)))

(defn default-handler [request]
  (let [uri  (request-uri request)]
    (println uri)    
    (json-response 200 {:is ["generic-response"] 
                        :request uri                        
                        :message "Thanks for pinging me!" })))

(defroutes app
  (GET "/*" [:as request] (default-handler request)))

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev ))
        port (Integer. (get (System/getenv) "PORT" "8084"))]    
    (println (format "Starting ureporter-simulator on port [%s]" port))    
    (run-jetty (handler/site app) {:port port})))
