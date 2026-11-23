(ns e5.one-way
  (:require [statecharts.core :as fsm]))

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
     {:on {:next :g}}

     :halting_g
     {:on {:next :a}}

     :halting_a
     {:on {:next :stopped}}}}))

(defn new-service []
  (fsm/service one-way))