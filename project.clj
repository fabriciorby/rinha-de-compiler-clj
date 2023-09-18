(defproject interpreter "1.0.0"
  :description "Clojure Interpreter for Rinha de Compiladores"
  :url "https://github.com/fabriciorby/rinha-de-compiler-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "2.4.0"]
                 ]
  :main ^:skip-aot interpreter.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
