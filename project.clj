(defproject borg-central "0.1.0-SNAPSHOT"
  :description ""
  :license {
    :name "MIT"
    :url "https://opensource.org/licenses/mit-license.php"}
  :dependencies [
    [easy-rpc "0.2.0"]
    [redis-atom "1.1.1"]
    [org.clojure/clojure "1.10.0"]]
  :repl-options {:init-ns borg-central.core}
  :main borg-central.core)
