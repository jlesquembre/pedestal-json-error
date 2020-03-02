(ns pedestal-json-error.core-test
  (:require
    [io.pedestal.http :as http]
    [io.pedestal.test :refer [response-for]]
    [clojure.test :refer [deftest is]]
    [pedestal-json-error.core :refer [routes]]))


(def service (::http/service-fn (http/create-servlet
                                  {::http/routes routes
                                   ::http/join?  false
                                   ::http/type   :jetty
                                   ::http/port   8888})))

(deftest invalid-json
  (is (= 500 (:status
               (response-for service
                             :post "/"
                             :headers {"Content-Type" "application/json"}
                             :body "{foo: bar}"
                             :body "hello"))))
  (is (= 500 (:status
               (response-for service
                             :post "/"
                             :headers {"Content-Type" "application/json"}
                             :body "[\"hello\"")))))
