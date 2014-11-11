#!/bin/bash
CWD=`pwd`
docker run -d -ti \
	-p 8003:8000 \
	-v $CWD/images:/opt/presentation/images \
	-v $CWD/build:/build \
	-v $CWD:/opt/presentation/lib/md \
	rossbachp/presentation
