package gibran.com.br.moviedbservice.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Language implements Parcelable {
    private String name;
    private String iso6391;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso6391() {
        return this.iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                ", iso6391='" + iso6391 + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.iso6391);
    }

    public Language() {
    }

    protected Language(Parcel in) {
        this.name = in.readString();
        this.iso6391 = in.readString();
    }

    public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {
        @Override
        public Language createFromParcel(Parcel source) {
            return new Language(source);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };
}
