(ns interpreter.core
  (:require [clojure.data.json :as json])
  (:require [clojure.java.io :as io])
  (:gen-class))

(declare read-file parser get-operation print-and-return)

(defmacro functionize [macro] `(fn [& args#] (eval (cons '~macro args#))))

(defn -main
  [& args]
  (parser ((read-file (first args)) :expression)))

(defn read-file [filepath]
  (json/read (io/reader filepath) :key-fn keyword))

(defn parser
  ([ast] (parser ast {}))
  ([ast env]
   (case (ast :kind)
     ("Str" "Int" "Bool")  (ast :value)
     "Var" (env (keyword (ast :text)))
     "Let" (let [new-env (assoc env (keyword ((ast :name) :text)) (parser (ast :value) env))]
             (parser (ast :next) new-env))
     "If" (if (parser (ast :condition) env)
            (parser (ast :then) env)
            (parser (ast :otherwise) env))
     "Function" (let [params (map #(keyword (% :text)) (ast :parameters))]
                  (fn [env] (parser (ast :value) (dissoc (conj env (zipmap params (env :arguments))) :arguments))))
     "Call" (let [new-env (assoc env :arguments (map #(parser % env) (ast :arguments)))]
              ((parser (ast :callee) new-env) new-env))
     "Tuple" (list (parser (ast :first) env) (parser (ast :second) env))
     "First" (parser ((ast :value) :first) env)
     "Second" (parser ((ast :value) :second) env)
     "Print" (print-and-return (parser (ast :value) env))
     "Binary" ((get-operation ast) (parser (ast :lhs) env) (parser (ast :rhs) env))
     )
   )
  )

(defn get-operation [ast]
  (partial
    (case (ast :op)
      "Add" + "Sub" - "Mul" * "Div" / "Rem" mod "Eq" = "Neq" not=
      "Lt" < "Gt" > "Lte" <= "Gte" >= "And" (functionize and) "Or" (functionize or)
      )
    )
  )
(defn print-and-return [value]
  (if (list? value)
    (println (str "(" (first value) ", " (second value) ")"))
    (println value))
  value
  )