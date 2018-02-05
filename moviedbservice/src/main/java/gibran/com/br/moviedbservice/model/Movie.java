package gibran.com.br.moviedbservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable {
    private String original_language;
    private String imdbId;
    private boolean video;
    private String title;
    private String backdropPath;
    private int revenue;
    private ArrayList<Genre> genres;
    private double popularity;
    private ArrayList<Country> productionCountries;
    private int id;
    private int voteCount;
    private int budget;
    private String overview;
    private String originalTitle;
    private int runtime;
    private String posterPath;
    private ArrayList<Language> spokenLanguages;
    private ArrayList<Company> productionCompanies;
    private String releaseDate;
    private double voteAverage;
    private String tagline;
    private boolean adult;
    private String homepage;
    private String status;

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public ArrayList<Country> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(ArrayList<Country> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public ArrayList<Language> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(ArrayList<Language> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public ArrayList<Company> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(ArrayList<Company> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "original_language='" + original_language + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", video=" + video +
                ", title='" + title + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", revenue=" + revenue +
                ", genres=" + genres +
                ", popularity=" + popularity +
                ", productionCountries=" + productionCountries +
                ", id=" + id +
                ", voteCount=" + voteCount +
                ", budget=" + budget +
                ", overview='" + overview + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", runtime=" + runtime +
                ", posterPath=" + posterPath +
                ", spokenLanguages=" + spokenLanguages +
                ", productionCompanies=" + productionCompanies +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAverage=" + voteAverage +
                ", tagline='" + tagline + '\'' +
                ", adult=" + adult +
                ", homepage='" + homepage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.original_language);
        dest.writeString(this.imdbId);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeInt(this.revenue);
        dest.writeList(this.genres);
        dest.writeDouble(this.popularity);
        dest.writeList(this.productionCountries);
        dest.writeInt(this.id);
        dest.writeInt(this.voteCount);
        dest.writeInt(this.budget);
        dest.writeString(this.overview);
        dest.writeString(this.originalTitle);
        dest.writeInt(this.runtime);
        dest.writeString(this.posterPath);
        dest.writeList(this.spokenLanguages);
        dest.writeList(this.productionCompanies);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.tagline);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.homepage);
        dest.writeString(this.status);
    }

    protected Movie(Parcel in) {
        this.original_language = in.readString();
        this.imdbId = in.readString();
        this.video = in.readByte() != 0;
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.revenue = in.readInt();
        this.genres = new ArrayList<Genre>();
        in.readList(this.genres, Genre.class.getClassLoader());
        this.popularity = in.readDouble();
        this.productionCountries = new ArrayList<Country>();
        in.readList(this.productionCountries, Country.class.getClassLoader());
        this.id = in.readInt();
        this.voteCount = in.readInt();
        this.budget = in.readInt();
        this.overview = in.readString();
        this.originalTitle = in.readString();
        this.runtime = in.readInt();
        this.posterPath = in.readString();
        this.spokenLanguages = new ArrayList<Language>();
        in.readList(this.spokenLanguages, Language.class.getClassLoader());
        this.productionCompanies = new ArrayList<Company>();
        in.readList(this.productionCompanies, Company.class.getClassLoader());
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.tagline = in.readString();
        this.adult = in.readByte() != 0;
        this.homepage = in.readString();
        this.status = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
