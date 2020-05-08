(ns borg-central.db)

(defn get-latest-snapshot
  "Queries db for latest state snapshot"
  [] 42)

(defn store-snapshot!
  "Stores snapshot of state in db. Returns nil."
  [snap]
  nil)

(defn get-diffs
  "Quesries the db for all diffs older than after-t"
  [after-t]
  [42 42 42])

(defn store-diffs!
  "Stores all input diffs in db. Returns nil."
  [diffs]
  nil)