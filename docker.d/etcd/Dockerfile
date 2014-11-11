FROM ubuntu:14.04

WORKDIR /tmp

RUN apt-get update && \
  DEBIAN_FRONTEND=noninteractive apt-get install -y wget apt-transport-https

RUN wget -nv https://github.com/coreos/etcd/releases/download/v0.4.6/etcd-v0.4.6-linux-amd64.tar.gz && \
  tar -xvf etcd-v0.4.6-linux-amd64.tar.gz && \
mv /tmp/etcd-v0.4.6-linux-amd64/etcd /usr/bin/etcd && \
    mv /tmp/etcd-v0.4.6-linux-amd64/etcdctl /usr/bin/etcdctl && \
  rm -rf /tmp/etcd-v0.4.6-linux-amd64 /tmp/etcd-v0.4.6-linux-amd64.tar.gz

EXPOSE 4001 7001
ENTRYPOINT ["/usr/bin/etcd"]
