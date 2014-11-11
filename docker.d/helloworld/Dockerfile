FROM rossbachp/java:jre-7
MAINTAINER Peter Rossbach <peter.rossbach@bee42.com>

RUN mkdir /opt/micro
COPY target/helloworld.jar /opt/micro/helloworld.jar
COPY micro.sh /opt/micro/micro.sh
COPY logging.properties /opt/micro/logging.properties

WORKDIR /opt/micro
EXPOSE 8080

CMD [ "/bin/bash", "-c", "/opt/micro/micro.sh" ]
