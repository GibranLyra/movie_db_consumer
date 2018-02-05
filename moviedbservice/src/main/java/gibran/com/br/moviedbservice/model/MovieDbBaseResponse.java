package gibran.com.br.moviedbservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieDbBaseResponse implements Parcelable {
    public static final Creator<MovieDbBaseResponse> CREATOR = new Creator<MovieDbBaseResponse>() {
        @Override
        public MovieDbBaseResponse createFromParcel(Parcel source) {
            return new MovieDbBaseResponse(source);
        }

        @Override
        public MovieDbBaseResponse[] newArray(int size) {
            return new MovieDbBaseResponse[size];
        }
    };
    private int page;
    private int total_pages;
    private ArrayList<Movie> results;
    private int total_results;

    public MovieDbBaseResponse() {
    }

    protected MovieDbBaseResponse(Parcel in) {
        this.page = in.readInt();
        this.total_pages = in.readInt();
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.total_results = in.readInt();
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return this.total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return this.total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.total_pages);
        dest.writeTypedList(this.results);
        dest.writeInt(this.total_results);
    }
}
