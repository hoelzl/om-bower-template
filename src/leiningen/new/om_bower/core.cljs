(ns {{namespace}}
  (:require-macros [cljs.core.async.macros :refer [alt! go go-loop]]
                   [plumbing.core :refer [for-map lazy-get
                                          ?>> ?> fn-> fn->> <- as->>
                                          memoized-fn
                                          letk fnk defnk]])
  (:require
   ;; Provide cljs.core.async under the `async` name (for `map`,
   ;; `merge`, `into`, and `take`...
   [cljs.core.async :as async]
   ;; ... and make the rest of the functionality available
   ;; without any prefix.
   [cljs.core.async :refer [chan timeout <! take! >! put! close!
                            buffer dropping-buffer sliding-buffer
                            unblocking-buffer?
                            do-alts alts!
                            pipe pipeline-async pipeline
                            split reduce onto-chan to-chan
                            Mux Mult mult tap untap untap-all
                            Mix mix admix unmix unmix-all
                            toggle solo-mode
                            Pub pub sub unsub unsub-all]]
   
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]
   
   [monet.canvas :as canvas]
   
   [sablono.core :as html :refer-macros [html]]
   
   
   [om-bootstrap.button :as button]
   [om-bootstrap.grid :as grid]
   [om-bootstrap.input :as input]
   [om-bootstrap.mixins :as mixins]
   [om-bootstrap.nav :as nav]
   [om-bootstrap.panel :as panel]
   [om-bootstrap.progress-bar :as progress-bar]
   [om-bootstrap.random :as random]
   [om-bootstrap.table :refer [table]]
   
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
                      :text-1  "This is a template using Om together
                      with Twitter Bootstrap and some other
                      components.  This text is in a column that
                      should take up the full width of the screen on
                      phones or if you have a narrow browser window,
                      and occupy the left column of a two-column
                      layout on other devices."
                      :text-2  "This text is in a column that should
                      take up the full width of the screen on phones
                      or if you have a narrow browser window, and
                      occupy the right column of a two-column layout
                      on other devices."}))


(defn hello [data _]
  (reify om/IRender
    (render [_]
      (html [:div
             [:h1 (:heading data)]
             [:div {:class "row"}
              [:div {:class "col-xs-12 col-sm-6"}
               [:p (:text-1 data)]]
              [:div {:class "col-xs-12 col-sm-6"}
               [:p (:text-2 data)]]]]))))

(defn canvas [_ owner]
  (reify
    om/IRender
    (render [_]
      (html [:div {:class "row"}
             [:canvas {:ref "canvas"}]]))
    om/IDidMount
    (did-mount [_]
      (.log js/console "Canvas:" (om/get-node owner "canvas")))))

(defn main-div [data _]
  (reify om/IRender
    (render [_]
      (html [:div {:class "container-fluid"}
             (om/build hello data)
             (om/build canvas data)]))))

(om/root main-div
         app-state
         {:target (.getElementById js/document "app")})
