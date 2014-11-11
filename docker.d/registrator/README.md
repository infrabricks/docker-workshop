# registrator

Start registrator at localhost ectd

```bash
docker run -d \
    -v /var/run/docker.sock:/tmp/docker.sock \
    -h $HOSTNAME rossbachp/registrator etcd:///tomcat8
```

Start registrator at docker0 bridge endpoint...

```bash
ETCD_ENDPOINT="$(ifconfig docker0 | awk '/\<inet\>/ { print $2}'):4001"
docker run -d \
    -v /var/run/docker.sock:/tmp/docker.sock \
    -h $HOSTNAME rossbachp/registrator etcd://$ETCD_ENDPOINT/tomcat8
```

## Links
  - [Registrator](https://github.com/progrium/registrator)
