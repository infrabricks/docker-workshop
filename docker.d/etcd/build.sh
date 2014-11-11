#!/bin/bash

docker build -t rossbachp/etcd:latest .
docker tag rossbachp/etcd:latest rossbachp/etcd:0.4.6
