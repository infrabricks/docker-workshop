FROM ubuntu:14.04
MAINTAINER Peter Rossbach <peter.rossbach@bee42.com>

ADD https://github.com/progrium/registrator/releases/download/v0.4.0/registrator_0.4.0_linux_x86_64.tgz /tmp/registrator.tgz
RUN cd /bin && gzip -dc /tmp/registrator.tgz | tar -xf - && rm /tmp/registrator.tgz

ENV DOCKER_HOST unix:///tmp/docker.sock
ENTRYPOINT ["/bin/registrator"]
