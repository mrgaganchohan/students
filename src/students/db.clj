(ns students.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec {:dbtype "h2" :dbname "./students"})

(defn add-student-to-db
  [fname sname age grade]
  (let [results (jdbc/insert! db-spec :students {:fname fname :sname sname :age age :grade grade})]
    (assert (= (count results) 1))
    (first (vals (first results)))))

(defn get-student
  [s-id]
  (let [results (jdbc/query db-spec
                            ["select * from students where id = ?" s-id])]
    (assert (= (count results) 1))
    (first results)))

(defn get-all-students
  []
  (jdbc/query db-spec "select id, fname, sname, age, grade from students"))