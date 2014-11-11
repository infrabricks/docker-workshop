package com.bee42.microservices.repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Link {

  @SuppressWarnings("unused")
  private final String Type = "Link";

  private String _id;
  private String _rev;

  private String url;
  private List<String> tags;
  private Set<String> authors;
  private Date createDate;

  private List<RevInfo> _revs_info;

  public Link() {
    super();
  }

  public Link(String _id) {
    this._id = _id;
  }

  public Link(String _id, String url) {
    this._id = _id;
    this.url = url;
  }

  public String get_id() {
    return _id;
  }

  public String get_rev() {
    return _rev;
  }

  public String getUrl() {
    return url;
  }

  public List<String> getTags() {
    return tags;
  }

  public Set<String> getAuthors() {
    return authors;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public List<RevInfo> get_revs_info() {
    return _revs_info;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public void set_rev(String _rev) {
    this._rev = _rev;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void setAutors(Set<String> authors) {
    this.authors = authors;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public void set_revs_info(List<RevInfo> _revs_info) {
    this._revs_info = _revs_info;
  }

  @Override
  public String toString() {
    return "Link [_id=" + _id + ", _rev=" + _rev + ", url=" + url + ", tags=" + tags + ", createDate="
        + createDate + ", authors=" + authors + ", _revs_info="
        + _revs_info + "]";
  }

  public static class RevInfo {
    private String rev;
    private String status;

    public String getRev() {
      return rev;
    }

    public void setRev(String rev) {
      this.rev = rev;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    @Override
    public String toString() {
      return "RevInfo [rev=" + rev + ", status=" + status + "]";
    }
  }

}
