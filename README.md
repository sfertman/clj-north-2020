# Borg sync problem

## definition
- each drone needs to know everything that all other drones know
- each drone sends information to backend
- each drone wants to sync the global state whenever it chooses

## monolith solution
- information storage
  - receives POST requests
  - stores diffs in central db
- information retrieval
  - computes snapshots at regular intervals
  - receives sync request with "begin" timestamp
  - queries db for latest snapshot and diffs from "begin" timestamp till "now"
  - sends all of the above back to drone to compute own snapshot

## microservices
- we'd want to delegate some of the heavy lifting to other machines
- for example
  - snapshot computation can be done on remote worker(s)
  - snapshot caching can be in Redis instead of atom
