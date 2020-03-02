(ns pedestal-json-error.core
  (:require
    [io.pedestal.http :as http]
    [io.pedestal.http.body-params :as body-params]
    [io.pedestal.http.route :as route]
    [signal.handler :refer [with-handler]])
  (:import
    (com.fasterxml.jackson.core JsonProcessingException))
  (:gen-class))


(defn handler [request]
  {:status 200 :body {:msg "Hello, world!"}})

(def routes
  (route/expand-routes
   #{["/" :post
      [http/json-body (body-params/body-params) handler]
      :route-name :my-route]}))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/join?  false
    ::http/type   :jetty
    ::http/port   8888}))

(defn start []
  (http/start (create-server)))

(defn -main
  [& args]
  (println "Starting web server at http://localhost:8888")

  (let [server (start)]
    (with-handler :int
      (println "caught SIGINT, quitting.")
      (shutdown-agents)
      (flush)
      (http/stop server))))
