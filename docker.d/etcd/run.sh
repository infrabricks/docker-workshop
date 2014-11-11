#!/bin/bash

DID=$(docker run \
	--detach 	\
	-p 127.0.0.1:4001:4001 \
  -p 127.0.0.1:7001:7001 \
	--name etcd \
	rossbachp/etcd:0.4.6
)

echo started etcd in $CID

docker port $CID
