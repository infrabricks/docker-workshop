package com.bee42.microservices.repository;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LinkRepository {

  /** client max connections */
  private static final int MAX_CONNECTIONS = 20;

  private CouchDbClient dbClient;

  public CouchDbClient init() {

    CouchDbProperties properties = new CouchDbProperties()
    .setDbName(getConfigParameter("COUCHDB_NAME","links"))
    .setCreateDbIfNotExist(true)
    .setProtocol("http")
    .setHost(getConfigParameter("COUCHDB_HOST", "127.0.0.1"))
    .setPort(Integer.parseInt(getConfigParameter("COUCHDB_PORT", "5984")))
    .setMaxConnections(MAX_CONNECTIONS);

    dbClient = new CouchDbClient(properties);
    return dbClient;
  }

  public void destroy() {
    dbClient.shutdown();
  }

  public CouchDbClient getClient() {
    return dbClient;
  }

  public Response save(Link link) {
    return dbClient.save(link);
  }

  public void deleteById(String id) {
     dbClient.remove(id);
  }

  public Link findById(String id) {
    return dbClient.find(Link.class, id);
  }

  public JsonObject findAny() {
    String uri = dbClient.getBaseUri() + "links/_design/all/_view/all";
    JsonObject jsonObject = dbClient.findAny(JsonObject.class, uri);
    return jsonObject;
  }

  public JsonObject findJsonObjectById(String id) {
    return dbClient.find(JsonObject.class, id);
  }

  private String getConfigParameter(String key, String defaultValue) {
    String value = System.getenv(key);
    if(value == null || "".equals(value)) {
       value = System.getProperty(key);
       if(value == null || "".equals(value))
         value=defaultValue;
    }
    return value;
  }
}
