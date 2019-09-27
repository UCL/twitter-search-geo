package uk.ac.ucl.twitter.search.geo;

public class StatusData {

  private String statuses = "";
  private final Metadata metaData = new Metadata();

  public String getStatuses() {
    return statuses;
  }

  public void setStatuses(final String s) {
    statuses = s;
  }

  public Metadata getMetaData() {
    return metaData;
  }

  public class Metadata {

    private long maxId = 0L;
    private int count = 0;

    public long getMaxId() {
      return maxId;
    }

    public void setMaxId(final long l) {
      maxId = l;
    }

    public int getCount() {
      return count;
    }

    public void setCount(final int c) {
      count = c;
    }

  }

}
