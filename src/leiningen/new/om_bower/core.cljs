(ns {{namespace}}
  (:require-macros [cljs.core.async.macros :refer [go]]
                   [plumbing.core :refer [for-map lazy-get
                                          ?>> ?> fn-> fn->> <- as->>
                                          memoized-fn
                                          letk fnk defnk]])
  (:require [cljs.core.async :refer [chan <! >! put! take!]]
            [om.core :as om :include-macros true]
            ;; Comment-out the line [om-tools.dom :as dom ...] and
            ;; comment-in this line to use the vanilla version of
            ;; om.dom instad of the Prismatic one.
            ;; [om.dom :as dom :include-macros true]

            [om-bootstrap.button :as button]
            [om-bootstrap.grid :as grid]
            [om-bootstrap.input :as input]
            [om-bootstrap.mixins :as mixins]
            [om-bootstrap.nav :as nav]
            [om-bootstrap.panel :as panel]
            [om-bootstrap.progress-bar :as progress-bar]
            [om-bootstrap.random :as random]
            [om-bootstrap.table :refer [table]]

            [om-tools.core :refer-macros [defcomponent defcomponentk]]
            [om-tools.dom :as dom :include-macros true]
            [plumbing.core :refer [update map-vals map-keys map-from-keys
                                   map-from-vals dissoc-in keywordize-map
                                   safe-get safe-get-in
                                   assoc-when update-in-when grouped-map
                                   aconcat unchunk sum singleton
                                   indexed positions distinct-by
                                   interleave-all count-when conj-when
                                   cons-when rsort-by
                                   swap-pair! get-and-set! millis
                                   mapply]]))

(enable-console-print!)

(def app-state (atom {:heading "Hello world!"
                      :text-1 "This is a template using Om together
                      with Twitter Bootstrap and some other
                      components.  This text is in a column that
                      should take up the full width of the screen on
                      phones or if you have a narrow browser window,
                      and occupy the left column of a two-column
                      layout on other devices."
                      :text-2 "This text is in a column that should
                      take up the full width of the screen on phones
                      or if you have a narrow browser window, and
                      occupy the right column of a two-column layout
                      on other devices."}))

(defcomponent hello [data owner]
  (render [_]
    (dom/div {:class "container-fluid"}
      (dom/h1 (:heading data))
      (dom/div {:class "row"}
        (dom/div {:class "col-xs-12 col-md-6"}
          (dom/p (:text-1 data)))
        (dom/div {:class "col-xs-12 col-md-6"}
          (dom/p (:text-2 data)))))))

(om/root hello
         app-state
         {:target (. js/document (getElementById "app"))})
