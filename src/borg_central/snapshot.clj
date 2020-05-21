(ns borg-central.snapshot
  (:require
    [borg-central.config :as cfg]
    [borg-central.db :as db]
    [redis-atom.core :refer [redis-atom]]))

(def cache (atom (db/get-latest-snapshot)))

(defn cached?
  []
  (< (System/currentTimeMillis) (+ (:timestamp @cache) cfg/T_CRON)))

(defn get-latest
  []
  (if (cached?)
    @cache
    (let [latest-snap (db/get-latest-snapshot)]
      (reset! cache latest-snap)
      latest-snap)))

(defn store!
  [snap]
  (when (< (:timestamp @cache) (:timestamp snap))
    (reset! cache snap)
    (db/store-snapshot! snap)))

(defn add-diff
  "Returns a new calculated snapshot by adding diff to base."
  [base diff]
  {:timestamp (:timestamp diff)
   :snapshot (conj (:snapshot base) (:snapshot diff))})

(defn compute
  "Returns a new snapshot computed from base and diffs."
  [base diffs]
  (reduce add-diff base diffs))

(defn create-new!
  []
  (when-not (cached?)
    (let [latest-snap (get-latest)
          diffs (db/get-diffs (:timestamp latest-snap))
          new-snap (compute latest-snap diffs)]
      (store! new-snap))))
