(ns students.views
  (:require [students.db :as db]
            [clojure.string :as str]
            [hiccup.page :as page]
            [ring.util.anti-forgery :as util]))

(defn gen-page-head
  [title]
  [:head
   [:title (str "Students: " title)]
   (page/include-css "/css/styles.css")])

(def header-links
  [:div#header-links
   "[ "
   [:a {:href "/"} "Home"]
   " | "
   [:a {:href "/add-student"} "Add a Student"]
   " | "
   [:a {:href "/all-students"} "View All Students"]
   " ]"])

(defn home-page
  []
  (page/html5
   (gen-page-head "Home")
   header-links
   [:h1 "Home"]
   [:p "Webapp to store and display student information."]))

(defn add-student-page
  []
  (page/html5
   (gen-page-head "Add a Student")
   header-links
   [:h1 "Add a Student"]
   [:form {:action "/add-student" :method "POST"}
    (util/anti-forgery-field) ; prevents cross-site scripting attacks
    [:p "First name: " [:input {:type "text" :name "fname"}]]
    [:p "Surname: " [:input {:type "text" :name "sname"}]]
    [:p "Age: " [:input {:type "text" :name "age"}]]
    [:p "Grade: " [:input {:type "text" :name "grade"}]]
    [:p [:input {:type "submit" :value "submit"}]]]))

(defn add-student-results-page
  [{:keys [fname sname age grade]}]
  (let [id (db/add-student-to-db fname sname age grade)]
    (page/html5
     (gen-page-head "Added a Student")
     header-links
     [:h1 "Added a Student"]
     [:p "Added  student " [:b fname]  " "  [:b sname] " (id: " id ") to the database. "
      [:a {:href (str "/student/" id)} "Take a look"]
      "."])))

(defn student-page
  [s-id]
  (let [{w :fname x :sname y :age z :grade} (db/get-student s-id)]
    (page/html5
     (gen-page-head (str "Student " s-id))
     header-links
     [:h1 "Student"]
     [:p "id: " s-id]
     [:p "Name: " w]
     [:p "Surname: " x]
     [:p "Age: " y]
     [:p "Grade: " z])))

(defn all-students-page
  []
  (let [all-students (db/get-all-students)]
    (page/html5
     (gen-page-head "All students in the database")
     header-links
     [:h1 "All Students"]
     [:table
      [:tr [:th "id"] [:th "fname"] [:th "sname"] [:th "age"] [:th "grade"]]
      (for [s all-students]
        [:tr [:td (:id s)] [:td (:fname s)] [:td (:sname s)] [:td (:age s)] [:td (:grade s)]])])))