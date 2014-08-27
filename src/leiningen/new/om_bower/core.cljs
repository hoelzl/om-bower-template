(ns {{namespace}}
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! chan <!]]
            [om.core :as om :include-macros true]
            ;; [om.dom :as dom :include-macros true]
            [om-bootstrap.button :as btn]
            [om-bootstrap.grid :as grid]
            [om-bootstrap.input :as inp]
            [om-bootstrap.mixins :as mix]
            [om-bootstrap.nav :as nav]
            [om-bootstrap.panel :as panel]
            [om-bootstrap.progress-bar :as pb]
            [om-bootstrap.random :as rand]
            [om-bootstrap.table :refer [table]]
            [om-tools.core :refer-macros [defcomponent defcomponentk]]
            [om-tools.dom :as dom :include-macros true]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"}))

(om/root
  (fn [app owner]
    (reify om/IRender
      (render [_]
        (dom/h1 nil (:text app)))))
  app-state
  {:target (. js/document (getElementById "app"))})
