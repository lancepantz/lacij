;;; Copyright © 2010 Fraunhofer Gesellschaft
;;; Licensed under the EPL V.1.0

(ns ^{:doc "Utility functions to call layouts algorithms"}
  lacij.layouts.layout
  (:use (lacij.layouts core randomlayout naivelayout)))

(defn layout [graph rlayout & options]
  (let [options (apply hash-map options)]
    (case rlayout
          :random (layout-graph (randomlayout) graph options)
          :naive (layout-graph (naivelayout) graph options)
          (layout-graph rlayout graph options))))
