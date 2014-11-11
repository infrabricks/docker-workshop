docker-workshop
=========================

includes

 * Puppet module garethr/puppet, manages
 * demo user (pwd: demo) in docker group w/ sudo
 * bridge utils
 * apache2 installed and running
 * nsenter
 * fig
 * docker-squash
 * docker registry
   * configured as mirror-proxy to public docker hub
   * contains images for ubuntu:latest and busybox:latest
   * demo images from rossbachp
 * serverspec
   * spec for all items above
 * etcd
 * registrator from progrium
 * ufw is stopped


Start slide container basics:
=====================

```bash
$ /data/mnt/slidefire/docker-basics
$ ./build.sh
$ ./run.sh
```

Point browser to [http://127.0.0.1:8002/](http://127.0.0.1:8002/)

Start slide container microservice:
=====================

```bash
$ /data/mnt/slidefire/docker-microservice
$ ./build.sh
$ ./run.sh
```

Point browser to [http://127.0.0.1:8003/](http://127.0.0.1:8003/)

License
=======
The MIT License (MIT)
Copyright (c) 2014 Andreas Schmidt and Peter Rossbach
