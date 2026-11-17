(ns e5.three-way
  (:require [statecharts.core :as fsm]))

(def three-way
  (fsm/machine
   {:id :three-way
    
    :initial :stopped
    
    :states
    {:stopped
     {:on {:run :west}}
     
     :west 
     {:on {:next :north}}
     }}))