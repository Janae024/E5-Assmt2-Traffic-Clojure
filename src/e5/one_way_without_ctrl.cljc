(ns one-way-without-ctrl
  (:require [statecharts.core :as fsm]))

(def one-way-without-ctrl
  (fsm/machine
  {:initial :r
   
   :transitions
   {[:r :next] :ra
    [:ra :next] :g
    [:g :next] :a
    [:a :next] :r}}))
