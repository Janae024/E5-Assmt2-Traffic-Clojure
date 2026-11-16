(ns e5.one-way-without-ctrl-test
  (:require
   [clojure.test :refer [deftest is]]
   [statecharts.core :as fsm]
   [one-way-without-ctrl :as traffic]))

;;Test 1: check if r is initial state (testing: R7 The signals start at all-red.)
(deftest R7-initial-state
  (let [service (traffic/new-service)]
    (fsm/start service)
    (is (= :r
           (fsm/value service)))))

;;Test 2: check if r state goes to ra (testing: R4 Each signal’s colour must follow the UK display cycle )
(deftest R4-r-to-ra
  (let [service (traffic/new-service)]
    (fsm/start service)
    (fsm/send service :next)
    (is (= :ra
           (fsm/value service)))))

;;Test 3: check if ra goes to g (testing: R4 Each signal’s colour must follow the UK display cycle )
(deftest R4-ra-to-g
  (let [service (traffic/new-service)]
    (fsm/start service)
    (fsm/send service :next)
    (is (= :g
           (fsm/value service)))))

;;Test 4: check if g goes to a (testing: R4 Each signal’s colour must follow the UK display cycle )
(deftest R4-g-to-a
  (let [service (traffic/new-service)]
    (fsm/start service)
    (fsm/send service :next)
    (is (= :a
           (fsm/value service)))))

;;Test 4: check if a goes to r
(deftest R4-a-to-r
  (let [service (traffic/new-service)]
    (fsm/start service)
    (fsm/send service :next)
    (is (= :r
           (fsm/value service)))))

;;Test 5: check if the full cycle returns to r (red)
(deftest R3-full-cycle
  (let [service (traffic/new-service)]
    (fsm/start service)
    
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)
    
    (is (= :r 
           (fsm/value service)))))