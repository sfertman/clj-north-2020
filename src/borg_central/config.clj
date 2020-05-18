(ns borg-central.config)

(def T_CRON 42000)

(def rpc {
  :ns "borg-central.snapshot"
  :transport :http
  :host "localhost"
  :port 3000
})