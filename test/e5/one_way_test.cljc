(ns e5.one-way-test
  (:require 
   [clojure.test :refer [deftest is]]
   [statecharts.core :as fsm ]
   [e5.one-way :as traffic]))

;;Test 1: Important because R7 requires the traffic lights to start in the all-red state.
(deftest R7-starts-stopped
  (let [service (traffic/new-service)]
    (fsm/start service)
    (is (= :stopped
           (fsm/value service)))))

;;Test 2: Important because R9 requires a :run event to start the traffic light cycle.
(deftest R9-run-starts-system
  (let [service (traffic/new-service)]
    (fsm/start service)
    (fsm/send service :run)
    (is (= :r
           (fsm/value service)))))

;;Test 3: Important because R4 requires the UK traffic-light sequence.
(deftest R4-r-to-ra
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)

    (is (= :ra
           (fsm/value service)))))

;; Test 4: Important because every state transition in the UK sequence must be correct.
(deftest R4-ra-to-g
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)
    (fsm/send service :next)

    (is (= :g
           (fsm/value service)))))

;; Test 5: Important because R10 requires the system to return to all-red when halted.
(deftest R10-halt-from-red
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :halt)

    (is (= :stopped
           (fsm/value service)))))

;; Test 6: Important because halting while running should begin a smooth stop.
(deftest R10-halt-from-green
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)
    (fsm/send service :next) 

    (fsm/send service :halt)


    (is (= :stopping
           (fsm/value service)))))

;; Test 7: Important because shutdown must eventually return to the all-red state.
(deftest R10-stopping-tostopped
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)
    (fsm/send service :next)

    (fsm/send service :halt)
    (fsm/send service :next)


    (is (= :stopped
           (fsm/value service)))))