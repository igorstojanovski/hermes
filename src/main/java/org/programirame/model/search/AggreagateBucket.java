package org.programirame.model.search;

public class AggreagateBucket {

    private String bucketName;
    private long bucketCount;

    public AggreagateBucket(String key, long docCount) {
        bucketName = key;
        bucketCount = docCount;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public long getBucketCount() {
        return bucketCount;
    }

    public void setBucketCount(long bucketCount) {
        this.bucketCount = bucketCount;
    }
}
