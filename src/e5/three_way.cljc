(ns e5.three-way
  (:require [statecharts.core :as fsm]))

;; Only one signal is active in each state. This prevents conflicting green lights (R5).

;; Halting states allow the active signal to finish its cycle before returning to the all-red state.

;; Halting from w_r stops immediately because all directions are already effectively red.

(def three-way
  (fsm/machine
   {:id :three-way

    :initial :stopped

    :states
    {:stopped
     {:on {:run :w_r}}

     ;; West cycle
     :w_r
     {:on {:next :w_ra
           :halt :stopped}}

     :w_ra
     {:on {:next :w_g
           :halt :w_halting_ra}}

     :w_g
     {:on {:next :w_a
           :halt :w_halting_g}}

     :w_a
     {:on {:next :n_r
           :halt :w_halting_a}}

     :w_halting_ra
     {:on {:next :w_halting_g}}

     :w_halting_g
     {:on {:next :w_halting_a}}

     :w_halting_a
     {:on {:next :stopped}}

     ;; North cycle
     :n_r
     {:on {:next :n_ra
           :halt :stopped}}

     :n_ra
     {:on {:next :n_g
           :halt :n_halting_ra}}

     :n_g
     {:on {:next :n_a
           :halt :n_halting_g}}

     :n_a
     {:on {:next :s_r
           :halt :n_halting_a}}

     :n_halting_ra
     {:on {:next :n_halting_g}}

     :n_halting_g
     {:on {:next :n_halting_a}}

     :n_halting_a
     {:on {:next :stopped}}

     ;; South cycle
     :s_r
     {:on {:next :s_ra
           :halt :stopped}}

     :s_ra
     {:on {:next :s_g
           :halt :s_halting_ra}}

     :s_g
     {:on {:next :s_a
           :halt :s_halting_g}}

     :s_a
     {:on {:next :w_r
           :halt :s_halting_a}}

     :s_halting_ra
     {:on {:next :s_halting_g}}

     :s_halting_g
     {:on {:next :s_halting_a}}

     :s_halting_a
     {:on {:next :stopped}}}}))

(defn new-service []
  (fsm/service three-way))

(comment
 ;; Exercise the three-way specification

  (def service(new-service))
  (fsm/start service)
  ;; => stopped

  (fsm/send service :run)
  ; => :w_r

  (fsm/send service :next)
  ;; => :w_ra

  (fsm/send service :next)
  ;; => :w_g

  (fsm/send service :halt)
  ;; => :w_halting_g

  (fsm/send service :next)
  ;; => :w_halting_a

 (fsm/send service :next)
  ;; => stopped)
  )