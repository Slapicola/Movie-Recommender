import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//Main class
public class Main {
    void main() {

        //Read from movies.json file
        try {
        
            String json = Files.readString(
                    Paths.get("Java-Repo/Java Projects/movies.json")

            );

            //Declare Gson object
            Gson gson = new Gson();
            
            //Declare Movie array (gets the movie objects from movies.json)
            ArrayList<Movie> Movies = gson.fromJson(json, new TypeToken<ArrayList<Movie>>() {
            }.getType());

            //Empty watch hitsory Array
            ArrayList<Movie> watchHistory = new ArrayList<Movie>();

            //Declare user input variable
                var userChoice = "";

            while (!userChoice.equals("6")) {
                //------Menu-----------
                System.out.println("===========================");
                System.out.println("MOVIE RECOMMENDER");
                System.out.println("===========================");
                System.out.println();
                
                System.out.println("1. Search by title");
                System.out.println("2. Search by genre");
                System.out.println("3. Search by actor");
                System.out.println("4. Recommend by Favorite Movie");
                System.out.println("5. Add Movies to watch list");
                System.out.println("6. Exit");
                
                //Get user input
                userChoice = IO.readln("Please enter choice (ie: 1, 2, 3): ");
    
                
                if (userChoice.equals("1")) {
                    var userSearch = IO.readln("Please enter a movie title: ");
                    searchByTitle(Movies, userSearch);
                } else if (userChoice.equals("2")) {
                    var userSearch = IO.readln("Please enter a movie genre: ");
                    searchByGenre(Movies, userSearch);
                } else if (userChoice.equals("3")){
                    var userSearch = IO.readln("Please enter an actor name: ");
                    searchByActor(Movies, userSearch);
                } else if (userChoice.equals("4")){
                    var userSearch = IO.readln("What is your favorite movie? ");
                    recommendByFavorite(Movies, userSearch);
                } else if (userChoice.equals(("5"))){
                    populateWatchHistory(Movies, watchHistory);
                    recommendByWatchList(Movies, watchHistory);
                } else if (userChoice.equals("6")) {
                    System.out.println("Goodbye! See you again.");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //----------Function Declarations----------------

    /* 
    This function prints out movie titles based off of the genre the user searches for.
    Loops through the data in movie.json, 
        checks if each movie's genre is equal to the genre the user searched for, 
        if it is then print out the title,
        if the genre is not in the database then print an error message. */
    static void searchByGenre(ArrayList<Movie> movieList, String genre) {
        boolean foundGenre = false;
        System.out.println("====================");
        System.out.println(genre + " Movies");
        System.out.println("====================");
        for (int i = 0; i < movieList.size(); i++) {
            Movie currentMovie = movieList.get(i);
            if (currentMovie.getGenre().equalsIgnoreCase(genre)) {
                foundGenre = true;

                System.out.println("- " + currentMovie.getTitle());
            }
        }
        if (!foundGenre) {
            System.out.println("There are no movies in that genre.");
        }
    }
    
    /*
    This function prints out movies based off the actor the user searches for.
    Loops through the data in movie.json,
        and loops through the array of actors in a movie object,
        if the actor that is in the movie is the one that the user is searching for then print out that movie
        if the actor is not in the database, print out an error message */

    static void searchByActor(ArrayList<Movie> movieList, String actorName) {
        boolean foundActor = false;
        System.out.println("====================");
        System.out.println("Movies starring " + actorName);
        System.out.println("====================");
        for (int i = 0; i < movieList.size(); i++) {
            Movie currentMovie = movieList.get(i);
            String[] actors = currentMovie.getActorsList();
            for (int j = 0; j < actors.length; j++) {
                if (actors[j].equalsIgnoreCase(actorName)) {
                    foundActor = true;
                    System.out.println("- " + currentMovie.getTitle());
                }

            }
        }
        if (!foundActor) {
            System.out.println("There are no movies with that actor.");
        }
    }

    /* 
    This function prints movie information for the movie the user searches for.
    Loops through the data in movies.json,
        checks if a movie title is the same as the one the user is searching for,
        if it is, print out the title, genre, and list of actors for that movie.
        If the movie does not exist in movie.json, print an error message. */
    
    static void searchByTitle(ArrayList<Movie> movieList, String title) {
        boolean foundMovie = false;
        System.out.println("====================");
        System.out.println("Movie Information");
        System.out.println("====================");
        for (int i = 0; i < movieList.size(); i++) {
            Movie currentMovie = movieList.get(i);
            if (currentMovie.getTitle().equalsIgnoreCase(title)) {
                foundMovie = true;
                System.out.println("Title: " + currentMovie.getTitle());
                System.out.println("Genre: " + currentMovie.getGenre());
                System.out.println("Actors:");
                String[] actors = currentMovie.getActorsList();
                for (int j = 0; j < actors.length; j++) {
                    System.out.println("- " + actors[j]);
                }
            }
        }
        if (!foundMovie) {
            System.out.println("That movie is not in the database.");
        }
    }

    /*
    This function recommend movies based off of one of the user's favorite movies.
    Loops through the movie data in movies.json,
    if the movie title is the same as the one the user typed in then loop through the movie data again
    check if any of the movies have the same genre as the favorite movie and are not the favorite movie itself.
    if yes, then print the titles of those movies. 
    If the favorite movie is not in the database an error will print. */
            
    static void recommendByFavorite(ArrayList<Movie> movieList, String title) {
        boolean foundMovie = false;
        for (int i = 0; i < movieList.size(); i++) {
            Movie currentMovie = movieList.get(i);
            if (currentMovie.getTitle().equalsIgnoreCase(title)) {
                String genre = currentMovie.getGenre();
                for (int j = 0; j < movieList.size(); j++) {
                    Movie recommendedMovie = movieList.get(j);
                    if (genre.equals(recommendedMovie.getGenre())
                            && !recommendedMovie.getTitle().equalsIgnoreCase(title)) {
                        foundMovie = true;
                        System.out
                                .println("Recommended because you liked " + title + ": " + recommendedMovie.getTitle());
                    }
                }
            }
        }
        if (!foundMovie) {
            System.out.println("Movie not found in the database.");
        }
    }

    /*
    This function adds movies to a watch history list.
    It asks the user to type three movies that they have watched. 
    It then loops through the movie data in movies.json
    checks if the movie that the user typed is a movie in the database
    and checks if the movie was already added to the watch history 
    if it was already added don't add it to the watch History
    if it wasn't, add it 
    Do this for each movie the user types in */

    static void populateWatchHistory(ArrayList<Movie> movieList, ArrayList<Movie> watchHistory) {
        watchHistory.clear();

        boolean alreadyAdded = false;

        System.out.println("Enter 3 watched movies: ");
        var askForMovies1 = IO.readln("Movie 1: ");
        var askForMovies2 = IO.readln("Movie 2: ");
        var askForMovies3 = IO.readln("Movie 3: ");

        for (Movie movie : movieList) {
            if (movie.getTitle().equalsIgnoreCase(askForMovies1)) {
                alreadyAdded = false;
                for (Movie alreadyWatched : watchHistory) {
                    if (alreadyWatched.equals(movie)) {
                        alreadyAdded = true;
                    }
                }
                if (!alreadyAdded) {
                    watchHistory.add(movie);
                } else {
                    System.out.println("Movie is already in watch history.");
                }
            }
            if (movie.getTitle().equalsIgnoreCase(askForMovies2)) {
                alreadyAdded = false;
                for (Movie alreadyWatched : watchHistory) {
                    if (alreadyWatched.equals(movie)) {
                        alreadyAdded = true;
                    }
                }
                if (!alreadyAdded) {
                    watchHistory.add(movie);
                } else {
                    System.out.println("Movie is already in watch history.");
                }
            }
            if (movie.getTitle().equalsIgnoreCase(askForMovies3)) {
                alreadyAdded = false;
                for (Movie alreadyWatched : watchHistory) {
                    if (alreadyWatched.equals(movie)) {
                        alreadyAdded = true;
                    }
                }
                if (!alreadyAdded) {
                    watchHistory.add(movie);
                } else {
                    System.out.println("Movie is already in watch history.");
                }
            }
        }
    }
    
    /* 
    This function recommends movies based of the movies in a user's watch history (what the previous function populates)
    Each genre has a counter for how many times it is in the watch history list
    Loops through the watch list and checks each movie's genre
    if a certain genre is found then its counter is updated. 
    The counters are then compared to see which one has  the highest count.
    The one with the hightest count is declared as the user's favorite genre
    The function then loops through the movies in movies.json,
    checks if the movies genre is the favorite genre,
    if it is and the movie isn't already in the watch history list then print that movie title out */

    static void recommendByWatchList(ArrayList<Movie> movieData, ArrayList<Movie> watchHistory) {
        //Declare genre counters
        int scienceFictionCount = 0;
        int crimeCount = 0; 
        int fantasyCount = 0;
        int dramaCount = 0;
        int actionCount = 0;
        int romanceCount = 0;
        int adventureCount = 0;
        int animationCount = 0;

        int highestCount = 0;

        String favoriteGenre = "";

        for (int i = 0; i < watchHistory.size(); i++) {
            Movie currentMovie = watchHistory.get(i);
            
            if (currentMovie.getGenre().equals("Science Fiction")) {
                scienceFictionCount++;
            }
            if (currentMovie.getGenre().equals("Crime")) {
                crimeCount++;
            }
            if (currentMovie.getGenre().equals("Fantasy")) {
                fantasyCount++;
            }
            if (currentMovie.getGenre().equals("Drama")) {
                dramaCount++;
            }
            if (currentMovie.getGenre().equals("Action")) {
                actionCount++;
            }
            if (currentMovie.getGenre().equals("Romance")) {
                romanceCount++;
            }
            if (currentMovie.getGenre().equals("Adventure")) {
                adventureCount++;
            }
            if (currentMovie.getGenre().equals("Animation")) {
                animationCount++;
            }
        }

        //Check what genre is seen the most in the watchlist
        if (scienceFictionCount > highestCount) {
            highestCount = scienceFictionCount;
            favoriteGenre = "Science Fiction";
        }
        if (crimeCount > highestCount) {
            highestCount = crimeCount;
            favoriteGenre = "Crime";
        }
        if (fantasyCount > highestCount) {
            highestCount = fantasyCount;
            favoriteGenre = "Fantasy";
        }
        if (dramaCount > highestCount) {
            highestCount = dramaCount;
            favoriteGenre = "Drama";
        }
        if (actionCount > highestCount) {
            highestCount = actionCount;
            favoriteGenre = "Action";
        }
        if (romanceCount > highestCount) {
            highestCount = romanceCount;
            favoriteGenre = "Romance";
        }
        if (adventureCount > highestCount) {
            highestCount = adventureCount;
            favoriteGenre = "Adventure";
        }
        if (animationCount > highestCount) {
            highestCount = animationCount;
            favoriteGenre = "Animation";
        }

        //Loop through movieData
        boolean watchedMovie = false;
        System.out.println();
        System.out.println("Based on your watch history, we recommend: ");
        for (Movie movie : movieData) {
            if (movie.getGenre().equals(favoriteGenre)) {
                watchedMovie = false;
                for (Movie watched : watchHistory) {
                    if (watched.equals(movie)) {
                        watchedMovie = true;
                    }
                }
                if (!watchedMovie) {
                    System.out.println("-" + " " + movie.getTitle());
                }
            }
        }
    }
    }