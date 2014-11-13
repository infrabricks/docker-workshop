# microservices

## build project

```bash
$ mvn package
$ fig build
```

## Start project

```bash
$ fig up
```

start at other shell
```bash
$ curl http://192.168.59.103:8090/hello/rest/status
$ curl http://192.168.59.103:8090/hello/rest/link
{"total_rows":1,"offset":0,"rows":[{"id":"d99550ed9afe2a9660a5702c78000707","key":null,"value":{"_id":"d99550ed9afe2a9660a5702c78000707","_rev":"1-f00327bb6683912305cfa181a2fc956a","url":"http://www.infrabricks.de/","tags":"docker infrabricks microservices devops serverspec"}}]}
```

see fig logs...
```
couchdb_1 | [info] [<0.121.0>] 172.17.0.32 - - GET /links 200
web_1     | 2014-11-11T21:54:13.386+0000 org.lightcouch.CouchDbClient process INFO: < Status: 200
web_1     | 2014-11-11T21:54:13.395+0000 org.lightcouch.CouchDbClient process INFO: > GET/links/_design/all/_view/all
couchdb_1 | [info] [<0.121.0>] 172.17.0.32 - - GET /links/_design/all/_view/all 200
web_1     | 2014-11-11T21:54:13.403+0000 org.lightcouch.CouchDbClient process INFO: < Status: 200
web_1     | 2014-11-11T21:54:12:507+0000 com.bee42.catalina.valves.AccessLogStdoutValve access INFO: 192.168.59.3 1415742852507 "GET /hello/rest/link HTTP/1.1" "-" "curl/7.30.0" 200 279 &quot;http-nio-8080-exec-1&quot; 1062 -
```
You must setup a couchdb view at link records

schema
```
url website link
tags list of your tags
authors set of authors
createDate the create date normally set from server is crud service ist ready!
```

## Hack couchdb

  * `http://192.168.59.103:5984/_utils`
  * Add link test document
```
"url":"http://www.infrabricks.de/"
"tags":"docker infrabricks microservices devops serverspec"
```

  * `http://192.168.59.103:5984/_utils/database.html?links/_design/all/_view/all`

Save this default document view with futon...

  * default all
```javascript
function(doc) {
  emit(null, doc);
}
```
