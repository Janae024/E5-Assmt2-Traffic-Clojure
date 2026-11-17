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
     :n_r
     {:on {:next :n_ra
           :halt :stopped}}
     :n_ra
     {:on {:next :n_g
           :halt :stopped}}
     
     :n_g
     {:on {:next :n_a
           :halt :stopped}}
     :n_a
     {:on {:next :s_r
           :halt :stopped}}
     
     ;; South cycle
     :s_r
     {:on {:next :s_ra
           :halt :stopped}}
     :s_ra
     {:on {:next :s_g
           :halt :stopped}}
     :s_g
     {:on {:next :s_a
           :halt :stopped}}
     :s_a
     {:on {:next :w_r
           :halt :stopped}} 
     }}))

(defn new-service []
  (fsm/service three-way))