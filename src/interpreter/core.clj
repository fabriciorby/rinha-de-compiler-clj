(ns interpreter.core
  (:require [clojure.data.json :as json])
  (:require [clojure.java.io :as io])
  (:gen-class))

(declare read-file parser)

(defn -main
  [& args]
  (parser ((read-file (first args)) :expression)))

(defn read-file [filename]
  (json/read (io/reader (io/resource (str "files/" filename ".json"))) :key-fn keyword))

(defn parser
  ([ast] (parser ast {}))
  ([ast env]
   (cond
     (= (ast :kind) "Str") (ast :value)
     (= (ast :kind) "Int") (ast :value)
     (= (ast :kind) "Bool") (ast :value)
     (= (ast :kind) "Var") (env (keyword (ast :text)))
     (= (ast :kind) "Let") (let [new-env (assoc env (keyword ((ast :name) :text)) (parser (ast :value) env))]
                             (parser (ast :next) new-env))
     (= (ast :kind) "If") (if (parser (ast :condition) env) (parser (ast :then) env) (parser (ast :otherwise) env))
     (= (ast :kind) "Function") (let [params (map (fn [param] (keyword (param :text))) (ast :parameters))]
                                  (fn [env] (parser (ast :value) (dissoc (conj env (zipmap params (env :arguments))) :arguments))))
     (= (ast :kind) "Call") (let [new-env (assoc env :arguments (map (fn [arg] (parser arg env)) (ast :arguments)))]
                              ((parser (ast :callee) new-env) new-env))
     (= (ast :kind) "Tuple") (list (parser (ast :first) env) (parser (ast :second) env))
     (= (ast :kind) "First") (parser ((ast :value) :first) env)
     (= (ast :kind) "Second") (parser ((ast :value) :second) env)
     (= (ast :kind) "Print") (let [result (parser (ast :value) env)]
                               (if (list? result) (println (str "(" (first result) ", " (second result) ")"))
                                                  (println result)) result)
     (= (ast :kind) "Binary")
        (cond
            (= (ast :op) "Add") (+    (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Sub") (-    (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Mul") (*    (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Div") (/    (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Rem") (mod  (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Eq")  (=    (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Neq") (not= (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Lt")  (<    (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Gt")  (>    (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Lte") (<=   (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Gte") (>=   (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "And") (and  (parser (ast :lhs) env) (parser (ast :rhs) env))
            (= (ast :op) "Or")  (or   (parser (ast :lhs) env) (parser (ast :rhs) env)))
     )
   )


  )