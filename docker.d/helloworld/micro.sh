#!/bin/bash
java -Duser.language=en\
 -Duser.country=US\
 -DCOUCHDB_HOST=192.168.59.103 \
 -Djava.util.logging.config.file=/opt/micro/logging.properties\
 -Djava.util.logging.SimpleFormatter.format='%1$tY-%1$tm-%1$tdT%1$tH:%1$tM:%1$tS.%1$tL%1$tz %2$s %4$s: %5$s%n'\
 -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager\
 -Djava.security.egd=file:/dev/./urandom\
 -jar helloworld.jar
