
## Hack couchdb

  * `http://192.168.59.103:5984/_utils`

  * `http://192.168.59.103:5984/_utils/database.html?links/_design/all/_view/all`
  * get all
```javascript
function(doc) {
  emit(null, doc);
}
```
  
