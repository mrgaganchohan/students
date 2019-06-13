(ns students.handler
  (:require [students.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/"
       []
       (views/home-page))
  (GET "/add-student"
       []
       (views/add-student-page))
  (POST "/add-student"
        {params :params}
        (views/add-student-results-page params))
  (GET "/student/:loc-id"
       [loc-id]
       (views/student-page loc-id))
  (GET "/all-students"
       []
       (views/all-students-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
