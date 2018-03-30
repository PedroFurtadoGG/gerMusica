package germusica.peedro.com.br.germusica;


public class Genre {

    public String genreID;
    public String genreName;

    public Genre(){}

    public Genre(String genreID, String genreName){
        this.genreID = genreID;
        this.genreName = genreName;
    }

    public String getGenreID() {
        return genreID;
    }

    public String getGenreName() {
        return genreName;
    }
}
