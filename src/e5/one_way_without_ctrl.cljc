(ns one-way-without-ctrl
  (:require [statecharts.core :as fsm]))

(def one-way-without-ctrl
  (fsm/machine
   {:initial :r

    :states
    {:r
     {:on {::next :ra
           :ra
           {:on {:next :g
                 :g
                 {:on {:next :a}}
                 :a
                 {:on {:next :r}}}}}}}}))

(defn new-service []
  (fsm/service one-way-without-ctrl))