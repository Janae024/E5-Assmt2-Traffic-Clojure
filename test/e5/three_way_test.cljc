(ns e5.three-way-test
  (:require
   [clojure.test :refer [deftest is]]
   [statecharts.core :as fsm]
   [e5.three-way :as traffic]))

;; Test 1: Important because in R7, System starts in the all-red (stopped) state.
(deftest R7-starts-stopped
  (let [service (traffic/new-service)]
    (fsm/start service)
    
    (is (= :stopped
           (fsm/value service)))))

;; Test 2: R9 = A run event starts the traffic light cycle.
(deftest R9-run-starts-system
  (let [service (traffic/new-service)]
    (fsm/start service)
    (fsm/send service :run)
    
    (is (= :w_r
           (fsm/value service)))))

;; Test 3: State mentioned in R4 (West-red -> West-red-amber)
(deftest R4-west-r-to-ra
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)

    (is(= :w_ra
          (fsm/value service)))))

;; Test 4: State mentioned in R4 (West-red-amber -> West-green)
(deftest R4-west-ra-to-g
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)
    (fsm/send service :next)

    (is(= :w_g
          (fsm/value service)))))


;; Test 5: In R8, where West cycle eventually reaches North cycle.
(deftest R8-west-to-north
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)

    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)

    (is (= :n_r
           (fsm/value service)))))

;; Test 6: In R8, where North cycle eventually reaches South cycle.
(deftest R8-north-to-south
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)

    ;; Reach n_r
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)

    ;; Reach s_r
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)

    (is (= :s_r
           (fsm/value service)))))

;; Test 7: In R8, where West cycle eventually returns to west cycle.
(deftest R8-south-to-west
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)

    ;; Reach s_r
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)

    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)

    ;; Return to w_r
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)
    (fsm/send service :next)

    (is (= :w_r
           (fsm/value service)))))

;; Test 8: The colors should not change without a "next" event (R6)
(deftest R6-no-change-without-next
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)

    (is (= :w_r
           (fsm/value service)))))

;; Test 9: In R10, halt should return the system to stopped
(deftest R10-halt-from-red
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :halt)

    (is (= :stopped
           (fsm/value service)))))

;;Test 10: In R5, only  one direction should be green at a time
(deftest R5-prevents-conflicting-signals
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)
    (fsm/send service :next)

    ;; West reaches green
    (is (= :w_g
           (fsm/value service)))

    ;; Must pass through west amber before north begins
    (fsm/send service :next)

    (is (= :w_a
           (fsm/value service)))

    ;; Only after west amber finishes can north start
    (fsm/send service :next)

    (is (= :n_r
           (fsm/value service)))))

;; Test 11: A new safe halt from green
(deftest R10-halt-from-west-green
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)
    (fsm/send service :next)

    ;; currently :w_g
    (fsm/send service :halt)

    (is (= :w_halting_g
           (fsm/value service)))))

;; Test 12: finishes the whole cycle before stopping
(deftest R10-finish-west-cycle-before-stopping
  (let [service (traffic/new-service)]
    (fsm/start service)

    (fsm/send service :run)
    (fsm/send service :next)
    (fsm/send service :next)

    ;; halt at w_g
    (fsm/send service :halt)

    (is (= :w_halting_g
           (fsm/value service)))

    (fsm/send service :next)

    (is (= :w_halting_a
           (fsm/value service)))

    (fsm/send service :next)

    (is (= :stopped
           (fsm/value service)))))
