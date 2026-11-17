(ns e5.three-way
  (:require [statecharts.core :as fsm]))

(def three-way
  (fsm/machine
   {:id :three-way

    :initial :stopped

    :states
    {:stopped
     {:on {:run :w_r}}

     ;; West Cycle
     :w_r
     {:on {:next :w_ra
           :halt :stopped}}
     :w_ra
     {:on {:next :w_g
           :halt :stopped}}

     :w_g
     {:on {:next :w_a
           :halt :stopped}}
     :w_a
     {:on {:next :n_r
           :halt :stopped}}
     
     ;; North cycle
     

     }}))