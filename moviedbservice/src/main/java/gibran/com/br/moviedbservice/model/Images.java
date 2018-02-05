package gibran.com.br.moviedbservice.model;

import java.util.List;

public class Images {
    private List<String> posterSizes;
    private List<String> backdropSizes;
    private List<String> logoSizes;
    private List<String> stillSizes;
    private List<String> profileSizes;
    private String secureBaseUrl;
    private String baseUrl;

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdropSizes = backdropSizes;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    public void setLogoSizes(List<String> logoSizes) {
        this.logoSizes = logoSizes;
    }

    public List<String> getStillSizes() {
        return stillSizes;
    }

    public void setStillSizes(List<String> stillSizes) {
        this.stillSizes = stillSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public void setProfileSizes(List<String> profileSizes) {
        this.profileSizes = profileSizes;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "Images{" +
                "posterSizes=" + posterSizes +
                ", backdropSizes=" + backdropSizes +
                ", logoSizes=" + logoSizes +
                ", stillSizes=" + stillSizes +
                ", profileSizes=" + profileSizes +
                ", secureBaseUrl='" + secureBaseUrl + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
