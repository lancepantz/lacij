;;; Copyright © 2010 Fraunhofer Gesellschaft
;;; Licensed under the EPL V.1.0

(ns ^{:doc "Implementation of the GraphView protocol for SVG"}
  lacij.view.svg.graphview
  (:use clojure.pprint
        lacij.graph.core
        lacij.view.core
        (tikkba dom core))
  (:require [analemma.svg :as s]
            [tikkba.utils.dom :as dom]))

(defrecord SvgGraphView
    []
  GraphView

  (view-graph
   [this graph context]
   (let [defs (s/defs (apply concat (:defs context)))
         {:keys [doc width height]} context
         markers-def (dom/elements doc *svg-ns* defs)
         node-elements (map (fn [nodeid]
                              (let [n (node graph nodeid)]
                               (view-node (node-view n) n context)))
                            (nodes graph))
         edge-elements (map (fn [edgeid]
                              (let [e (edge graph edgeid)]
                               (view-edge (edge-view e) graph e context)))
                            (edges graph))
         doc-element (dom/document-element doc)]
     (dom/add-attrs doc-element :overflow "visible")
     (when width
       (dom/add-attrs doc-element :width width))
     (when height
       (dom/add-attrs doc-element :height height))
     (dom/append-child  doc-element markers-def)
     (dom/append-children doc-element node-elements)
     (dom/append-children doc-element edge-elements)
     doc))

  (export-graph
   [this graph filename context options]
   (apply dom/spit-xml filename (:xmldoc graph) (flatten (seq options)))
   )

)



(defn graphview
  []
  (SvgGraphView.))

