
//Class that uses Movie data
public class Movie {
    String title;
    String genre;
    String[] actors;

    //Empty constructor for Gson Library
    public Movie() {

    }
    
    //Constructor (creates a movie object)
    public Movie(String title, String genre, String[] actors) {
        this.title = title;
        this.genre = genre;
        this.actors = actors;
    }


    //Get movie title
    public String getTitle() {
        return title;
    }

    //Get movie genre
    public String getGenre() {
        return genre;
    }

    //Get list of actors
    public String[] getActorsList() {
        return actors;
    }
}

