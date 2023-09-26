(ns interpreter.core-test
  (:require [clojure.test :refer :all]
            [interpreter.core :refer :all]))

(deftest sum-test
  (testing "sum.json"
    (is (= 15 (-main "resources/files/sum.json"))))
  )

(deftest fib-test
  (testing "fib.json"
    (is (= 55 (-main "resources/files/fib.json"))))
  )

(deftest let-test
  (testing "let.json"
    (is (= 5 (-main "resources/files/let.json"))))
  )

(deftest combination-test
  (testing "combination.json"
    (is (= 45 (-main "resources/files/combination.json"))))
  )

(deftest tuple-test
  (testing "tuple.json"
    (is (= 5 (-main "resources/files/tuple.json"))))
  )

(deftest print-tuple-test
  (testing "print-tuple.json"
    (is (= (list 1 2) (-main "resources/files/print-tuple.json"))))
  )

(deftest print-test
  (testing "print.json"
    (is (= "Hello World" (-main "resources/files/print.json"))))
  )