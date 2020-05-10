(ns borg-central.core
  (:require
    [borg-central.config :as cfg]
    [borg-central.db :as db]
    [borg-central.snapshot :as snap]))

(defn sync-handler-naive
  [after-t]
  (let [latest-snap (db/get-latest-snapshot)
        latest-diffs (db/get-diffs (max after-t (:timestamp latest-snap)))]
    {:latest-snapshot latest-snap
     :diffs latest-diffs}))
;; This a bit naive; to be more efficient we would want to add cache

(defn sync-handler
  "Returns latest snapshot and diffs."
  [after-t]
  (let [latest-snap (snap/get-latest)
        latest-diffs (db/get-diffs (max after-t (:timestamp latest-snap)))]
    {:latest-snapshot latest-snap
     :diffs latest-diffs}))

(defn start-diff-server! [] {:something 42})

(defn stop-diff-server! [s] (.stop s))

(def snap-scheduler (atom {:running? false}))

(defn start-snap-scheduler! []
  (reset! snap-scheduler {:running? true})
  (while (:running? @snap-scheduler)
    (Thread/sleep cfg/T_CRON)
    (snap/create-new!)))

(defn stop-snap-scheduler! [s]
  (reset! s {:running? false}))

(defn -main [& args]
  (if ())
  (start-diff-server!)
  (start-snap-scheduler!))