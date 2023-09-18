(ns interpreter.core-test
  (:require [clojure.test :refer :all]
            [interpreter.core :refer :all]))

(deftest sum-test
  (testing "sum.json"
    (is (= 15 (-main "sum"))))
  )

(deftest fib-test
  (testing "fib.json"
    (is (= 55 (-main "fib"))))
  )

(deftest let-test
  (testing "let.json"
    (is (= 5 (-main "let"))))
  )

(deftest combination-test
  (testing "combination.json"
    (is (= 45 (-main "combination"))))
  )

(deftest tuple-test
  (testing "tuple.json"
    (is (= 5 (-main "tuple"))))
  )

(deftest print-tuple-test
  (testing "print-tuple.json"
    (is (= (list 1 2) (-main "print-tuple"))))
  )

(deftest print-test
  (testing "print.json"
    (is (= "Hello World" (-main "print"))))
  )