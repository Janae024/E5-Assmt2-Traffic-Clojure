(ns e5.one-way
  (:require [statecharts.core :as fsm]))

;; :stopped means the system is not operating.
;; :r means the system is running and currently displaying red.

;; Halting states allow a halt request without breaking
;; the normal UK traffic-light sequence.

;; Halting from :r goes directly to :stopped because
;; red already represents a safe all-red condition.

(def one-way
  (fsm/machine
   {:id :one-way

    :initial :stopped

    :states
    {:stopped
     {:on {:run :r}}

     :r
     {:on {:next :ra
           :halt :stopped}}

     :ra
     {:on {:next :g
           :halt :halting_ra}}

     :g
     {:on {:next :a
           :halt :halting_g}}

     :a
     {:on {:next :r
           :halt :halting_a}}

     ;; halting states

     :halting_ra
     {:on {:next :halting_g}}

     :halting_g
     {:on {:next :halting_a}}

     :halting_a
     {:on {:next :stopped}}}}))

(defn new-service []
  (fsm/service one-way))


(comment
  (def service (new-service))

  (fsm/start service)
  ;; => :stopped

  (fsm/send service :run)
  ;; => :r

  (fsm/send service :next)
  ;; => :ra

  (fsm/send service :halt)
  ;; => :halting_ra

  (fsm/send service :next)
  ;; => :halting_g

  (fsm/send service :next)
  ;; => :halting_a

  (fsm/send service :next)
  ;; => :stopped
  )

