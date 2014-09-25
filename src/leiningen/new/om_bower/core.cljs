(ns {{namespace}}
  
  (:require-macros [cljs.core.async.macros :refer [go]]
                   [plumbing.core :refer [for-map lazy-get
                                          ?>> ?> fn-> fn->> <- as->>
                                          memoized-fn
                                          letk fnk defnk]])
  (:require [cljs.core.async :refer [chan <! >! put! take!]]

            [monet.canvas :as canvas]
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
                                   mapply]]
            [sablono.core :as html :refer-macros [html]]))

(enable-console-print!)

;;; Utilities
;;; =========

(defn box [thing]
  [thing])

(defn unbox [box]
  (first box))

(defn maybe-deref [thing]
  (if (om/rendering?)
    thing
    @thing))

;;; Command channel for debugging purposes
(def cc (atom nil))

;;; Application State
;;; =================

(def text-1
  (str "This is a template using Om together with Twitter "
       "Bootstrap and some other components.  This text is "
       "in a column that should take up the full width of "
       "the screen on phones or if you have a narrow browser "
       "window, and occupy the left column of a two-column "
       "layout on other devices."))

(def text-2
  (str "This text is in a column that should take up the "
       "full width of the screen on phones or if you have a "
       "narrow browser window, and occupy the right column "
       "of a two-column layout on other devices."))

(def app-state (atom {}))

(reset! app-state
        {:title "{{raw-name}}"
         :text-1 (box text-1)
         :text-2 (box text-2)
         :kv-table-1 {:key-1 "Value 1" :key-2 "Value 2"}
         :kv-table-2 {:another-key "another value"
                      :yet-another-key "and yet another value"}
         :canvas-1 {:background {:x 0 :y 0 :w 600 :h 400 :color "#a91d21"}
                    :rect-1 {:x 0 :y 0 :w 20 :h 20 :color "#ffff00"}
                    :rect-2 {:x 200 :y 150 :w 20 :h 20 :color "#0000ff"}}})

;;; Commands
;;; ========

(defn update-app-state [command-chan cursor & {:keys [new-value update-fn]}]
  "Put a command to update the application state at `cursor` onto
  `command-chan`."
  (when command-chan
    (put! command-chan
          {:command :update-app-state
           :cursor cursor
           :new-value new-value
           :update-fn update-fn})))

(defmulti execute-command (fn [command data]
                            (:command command)))

(defmethod execute-command :update-app-state [command data]
  (cond (:update-fn command)
        (om/transact! data (:cursor command) (:update-fn command))
        (:new-value command)
        (om/update! data (:cursor command) (:new-value command))
        :default
        (js/Error. "Either :update-fn or :new-value has to be
        specified for :update-app-state")))

#_
(defmethod execute-command :default [command data]
  (println "execute-command: default implementation"))

;;; Components
;;; ==========

(defcomponent paragraph* [text owner]
  (render [_]
    (html
     [:div {:class ["col-xs-6"]}
      [:p (unbox text)]])))

(defn paragraph [text]
  (om/build paragraph* text))

(defcomponent text-box* [text owner {:keys [label]}]
  (render-state [_ {:keys [command-chan]}]
    (html
     [:form {:class ["col-xs-6"]}
      (input/input {:type "textarea"
                    :label label
                    :rows 5
                    :value (unbox text)
                    :on-change (fn [e]
                                 (update-app-state command-chan (om/path text)
                                                   :new-value (box (.. e -target -value))))})])))

(defn text-box [text opts]
  (om/build text-box* text opts))

(defcomponent navbar* [data owner]
  (render-state [_ {:keys [command-chan]}]
    (nav/navbar {:fluid true
                 :inverse? true
                 :brand (dom/a {:href "#"}
                               (:title data))}
      (nav/nav {:collapsible? true}
        ;; TODO: Inserting a dropdown throws lots of exceptions.
        ;; This is probably due to a bug in Om Bootstrap.
        ;; (button/dropdown {:key "file" :title "File"} ...)
        (button/menu-item {:key :empty-text-1
                           :on-select #(update-app-state command-chan :text-1
                                                         :new-value (box ""))}
          "Clear Text 1")
        (button/menu-item {:key :reset-text-1
                           :on-select #(update-app-state command-chan :text-1
                                                         :new-value (box text-1))}
          "Reset Text 1")
        ;; (button/menu-item {:divider? true})
        (button/menu-item {:key :empty-text-2
                           :on-select #(update-app-state command-chan :text-2
                                                         :new-value (box ""))}
          "Clear Text 2")
        (button/menu-item {:key :reset-text-2
                           :on-select #(update-app-state command-chan :text-2
                                                         :new-value (box text-2))}
          "Reset Text 2")))))

(defn navbar [data opts]
  (om/build navbar* data opts))

(defcomponent kv-table* [data owner {:keys [table-key]}]
  (render [_]
    (html
     [:div {:class "col-xs-6"}
      [:table {:class ["table"]}
       [:tbody
        (for [[k v] (seq (table-key data))]
          (dom/tr {:key k} (dom/th (name k)) (dom/td (name v))))]]])))

(defn kv-table [data opts]
  (om/build kv-table* data opts))

(defn paint-rectangle [monet-canvas key data]
  (let [rect (key data)]
    (let [entity (canvas/entity {:x (:x rect 0) :y (:y rect 0) :w (:w rect 20) :h (:h rect 20)}
                                nil
                                (fn [ctx val]
                                  (-> ctx
                                      (canvas/fill-style (:color (maybe-deref rect)))
                                      (canvas/fill-rect val))))]
      (canvas/add-entity monet-canvas key entity))))

(defn paint-canvas [monet-canvas data]
  (paint-rectangle monet-canvas :background data)
  (paint-rectangle monet-canvas :rect-1 data)
  (paint-rectangle monet-canvas :rect-2 data))

(defn move-rectangle-diagonally [r]
  (let [x (:x r) y (:y r)]
    (if (or (>= x 580) (>= y 380))
      (assoc r :x 0 :y 0)
      (assoc r :x (+ (:x r) 1)
             :y (+ (:y r) 0.67)))))

(defcomponent canvas* [data owner]
  (init-state [_]
    {:monet-canvas :no-canvas-yet
     :timer-1 :no-timer-yet
     :timer-2 :no-timer-yet})
  (did-mount [_]
    (let [canvas-dom (om/get-node owner)
          monet-canvas (canvas/init canvas-dom "2d")]
      (if monet-canvas
        (do
          (om/set-state! owner :monet-canvas monet-canvas)
          (paint-canvas monet-canvas data)
          (let [command-chan (om/get-state owner :command-chan)
                timer-1 (js/setInterval
                         (fn []
                           (update-app-state command-chan (conj (om/path data) :rect-1)
                                             :update-fn move-rectangle-diagonally))
                         33)
                timer-2 (js/setInterval
                         (fn []
                           (update-app-state command-chan (conj (om/path data) :rect-2)
                                             :update-fn move-rectangle-diagonally))
                         67)]
            (om/set-state! owner :timer-1 timer-1)
            (om/set-state! owner :timer-2 timer-2)))
        (js/Error. "Found no canvas."))))
  (will-unmount [_]
    (let [timer-1 (om/get-state owner :timer-1)
          timer-2 (om/get-state owner :timer-2)]
      (when timer-1
        (js/clearInterval timer-1))
      (when timer-2
        (js/clearInterval timer-2))))
  (did-update [_ prev-props prev-state]
    (let [monet-canvas (om/get-state owner :monet-canvas)]
      (assert monet-canvas "Canvas was not defined before `did-update` was called.")
      (paint-canvas monet-canvas data)))
  (render [_]
    (html
     [:canvas {:id "canvas" :ref "canvas"
               :class "col-xs-6" :width 600 :height 400}])))

(defn canvas [data opts]
  (om/build canvas* data opts))

(defcomponent page [data owner]
  (init-state [_]
    {:command-chan (chan)})
  (will-mount [_]
    (let [command-chan (om/get-state owner :command-chan)]
      (reset! cc command-chan)
      (go (loop []
            (let [command (<! command-chan)]
              (execute-command command data)
              (recur))))))
  (render-state [_ state]
    (html
     [:div
      (navbar data {:init-state state})
      [:div {:class "container-fluid"}
       [:div {:class "row"}
        (text-box (:text-1 data)
                  {:opts {:label "Text 1"}
                   :init-state state})
        (text-box (:text-2 data)
                  {:opts {:label "Text 2"}
                   :init-state state})]
       (kv-table data {:opts {:table-key :kv-table-1}})
       (kv-table data {:opts {:table-key :kv-table-2}})
       [:div {:class "row"}
        (paragraph (:text-1 data))
        (paragraph (:text-2 data))]
       [:div {:class "row"}
        (canvas (:canvas-1 data)
                {:init-state state})]]])))

(om/root page
         app-state
         {:target (. js/document (getElementById "app"))})


