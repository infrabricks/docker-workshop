#!/bin/bash
VERSION=8.0.14
java -cp target/lib/ecj-4.4.jar\
:target/lib/jersey-server-2.13.jar\
:target/lib/hk2-api-2.3.0-b10.jar\
:target/lib/hk2-locator-2.3.0-b10.jar\
:target/lib/osgi-resource-locator-1.0.1.jar\
:target/lib/hk2-utils-2.3.0-b10.jar\
:target/lib/javassist-3.18.1-GA.jar\
:target/lib/javax.annotation-api-1.2.jar\
:target/lib/javax.inject-2.3.0-b10.jar\
:target/lib/javax.ws.rs-api-2.0.1.jar\
:target/lib/jersey-client-2.13.jar\
:target/lib/jersey-common-2.13.jar\
:target/lib/jersey-container-servlet-2.13.jar\
:target/lib/validation-api-1.1.0.Final.jar\
:target/lib/jersey-container-servlet-core-2.13.jar\
:target/lib/tomcat-dbcp-$VERSION.jar\
:target/lib/tomcat-embed-core-$VERSION.jar\
:target/lib/tomcat-embed-el-$VERSION.jar\
:target/lib/tomcat-embed-jasper-$VERSION.jar\
:target/lib/tomcat-embed-logging-juli-$VERSION.jar\
:target/lib/tomcat-embed-websocket-$VERSION.jar\
:target/helloworld.jar \
com.bee42.microservices.Main
