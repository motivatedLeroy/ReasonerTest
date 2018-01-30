package domain;


public class LiveData {

    public Long id;

    public String sourceName;

    public String tags;

    public double rating;

    public String dateOfUpload;

    public long numberOfEntries;

    public String provider;

    public boolean liveData;

    public Long getId() {
        return id;
    }

    public LiveData(){

    }

    public LiveData(String sourceName, String tags, double rating, String dateOfUpload, long numberOfEntries, String provider,boolean liveData, String type, String url){
        this.sourceName = sourceName;
        this.tags = tags;
        this.rating = rating;
        this.dateOfUpload = dateOfUpload;
        this.numberOfEntries = numberOfEntries;
        this.provider = provider;
        this.url = url;
        this.liveData = liveData;
        this.type = type;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDateOfUpload() {
        return dateOfUpload;
    }

    public void setDateOfUpload(String dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    public long getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setNumberOfEntries(long numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String type;

    public String url;

}
