#!/bin/bash

docker build -t rossbachp/registrator:latest .
docker tag rossbachp/registrator:latest rossbachp/registrator:0.4.0
