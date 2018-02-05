package gibran.com.br.moviedbservice.model;

import java.util.ArrayList;

public class GenreResponse {
    private ArrayList<Genre> genres;

    public ArrayList<Genre> getGenres() {
        return this.genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
