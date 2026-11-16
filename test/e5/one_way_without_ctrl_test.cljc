(ns e5.one-way-without-ctrl-test
  (:require
   [clojure.test :refer [deftest is]]
   [statecharts.core :as fsm]
   [one-way-without-ctrl :as traffic]))

;;Test 1: check if r is initial state
(deftest test-initial-state
  (let [service (traffic/new-service)]
    (fsm/start service)
    (is (= :r
           (fsm/value service)))))

;;Test 2: check if r state goes to ra
(deftest test-r-to-ra
  (let [service (traffic/new-service)]
    (fsm/start service)
    (fsm/send service :next)
    (is (= :ra
           (fsm/value service)))))

;;Test 3: check if ra goes to g
(deftest test-ra-to-g
  (let [service (traffic/new-service)]
    (fsm/start service)
    (fsm/send service :next)
    (is (= :g
           (fsm/value service)))))

;;Test 4: check if 