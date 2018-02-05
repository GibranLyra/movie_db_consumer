package gibran.com.br.moviedbservice.model;

import java.util.List;

public class Configuration {
    private Images images;
    private List<String> changeKeys;

    public Images getImages() {
        return this.images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "images=" + images +
                ", changeKeys=" + changeKeys +
                '}';
    }
}
