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
           :halt :stopped}}

     :g
     {:on {:next :a
           :halt :stopping}}

     :a
     {:on {:next :r
           :halt :stopping}}

     :stopping
     {:on {:next :stopped}}}}))

(defn new-service []
  (fsm/service one-way))