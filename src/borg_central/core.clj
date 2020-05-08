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

(defn start-http-server! []
  {:something 42})

(defn stop-http-server! [s]
  (gracefully-stop! s))

(defn start-snapshot-cron! []
  (with-interval cfg/T_CRON
    (let [latest-snap (snap/get-latest)
          latest-diffs (db/get-diffs (:timestamp latest-snap))
          new-snap (snap/compute latest-snap latest-diffs)]
      (snap/store-snapshot! new-snap))))

(defn stop-snapshot-cron! [s]
  (gracefully-stop! s))

(defn -main [& args]
  (start-http-server!)
  (start-snapshot-cron!))