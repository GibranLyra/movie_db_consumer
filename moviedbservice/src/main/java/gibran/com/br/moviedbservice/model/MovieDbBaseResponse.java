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
    private int totalPages;
    private ArrayList<Movie> results;
    private int totalResults;

    public MovieDbBaseResponse() {
    }

    protected MovieDbBaseResponse(Parcel in) {
        this.page = in.readInt();
        this.totalPages = in.readInt();
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.totalResults = in.readInt();
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return this.totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.totalPages);
        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResults);
    }

    @Override
    public String toString() {
        return "MovieDbBaseResponse{" +
                "page=" + page +
                ", totalPages=" + totalPages +
                ", results=" + results +
                ", totalResults=" + totalResults +
                '}';
    }
}
