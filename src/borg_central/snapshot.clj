(ns borg-central.snapshot
  (:require
    [borg-central.config :as cfg]
    [borg-central.db :as db]))

(defn apply-diff
  "Returns a new calculated snapshot by applying diff on base."
  [base diff]
  (conj base diff))

(defn compute
  "Returns a new snapshot computed from base and diffs."
  [base diffs]
  (reduce apply-diff base diffs ))


(def cache (atom (db/get-latest-snapshot)))
(defn cached? []
  (< (System/currentTimeMillis) (+ (:timestamp @cache) cfg/T_CRON)))

(defn get-latest
  []
  (if (cached?)
    @cache
    (let [latest-snap (db/get-latest-snapshot)]
      (reset! cache latest-snap)
      latest-snap)))

(defn store-snapshot!
  [snap]
  (when (< (:timestamp @cache) (:timestamp snap))
    (reset! cache snap)
    (db/store-snapshot! snap)))