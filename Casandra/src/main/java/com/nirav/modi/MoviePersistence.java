package com.nirav.modi;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

/**
 * @Reference from : http://java.dzone.com/articles/connecting-cassandra-java
 * Handles movie persistence access.
 */
public class MoviePersistence
{
   private final CassandraConnector client = new CassandraConnector();

   public MoviePersistence(final String newHost, final int newPort)
   {
      out.println("Connecting to IP Address " + newHost + ":" + newPort + "...");
      client.connect(newHost, newPort);
   }
   
   /**
    * Returns movie matching provided title and year.
    *
    * @param title Title of desired movie.
    * @param year Year of desired movie.
    * @return Desired movie if match is found; Optional.empty() if no match is found.
    */
   public List<Movie> queryMovieByTitleAndYear(final String description, final int year)
   {
	   List<Movie> movies=new ArrayList<Movie>();
	   try{
		   client.getSession().execute("CREATE CUSTOM INDEX IF NOT EXISTS description ON movies_keyspace.movies ( description ) USING 'DescriptionIndex';");
		   final ResultSet movieResults = client.getSession().execute(
			         "SELECT * from movies_keyspace.movies WHERE description = ?", description, year);
			      final List<Row> movieRows = movieResults.all();
			      for(Row movieRow : movieRows){
			    	  movies.add(
			    			  new Movie(
			    					  movieRow.getString("title"),
			    					  movieRow.getInt("year"),
			    					  movieRow.getString("description"),
			    					  movieRow.getString("mmpa_rating"),
			    					  movieRow.getString("dustin_rating")));
			    	 
			      }
		   
	   }catch(Exception e){
		   e.printStackTrace();
//		   client.getSession().execute("CREATE KEYSPACE movies_keyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };");
//		   client.getSession().execute("CREATE TABLE movies_keyspace.movies ( title varchar, year int, description varchar, mmpa_rating varchar, dustin_rating varchar, PRIMARY KEY (title, year) );");
	   }
      return movies;
   }

   
   
   /**
    * Persist provided movie information.
    *
    * @param title Title of movie to be persisted.
    * @param year Year of movie to be persisted.
    * @param description Description of movie to be persisted.
    * @param mmpaRating MMPA rating.
    * @param dustinRating Dustin's rating.
    */
   public void persistMovie(
      final String title, final int year, final String description,
      final String mmpaRating, final String dustinRating)
   {
      client.getSession().execute(
         "INSERT INTO movies_keyspace.movies (title, year, description, mmpa_rating, dustin_rating) VALUES (?, ?, ?, ?, ?)",
         title, year, description, mmpaRating, dustinRating);
   }
   
   /**
    * Deletes the movie with the provided title and release year.
    *
    * @param title Title of movie to be deleted.
    * @param year Year of release of movie to be deleted.
    */
   public void deleteMovieWithTitleAndYear(final String title, final int year)
   {
      final String deleteString = "DELETE FROM movies_keyspace.movies WHERE title = ? and year = ?";
      client.getSession().execute(deleteString, title, year);
   }
   
   public void createMovieList(int i){
	      persistMovie("Veer-Zaara-", 1991, "Love Story","1" , "2");
//	      persistMovie("Veer-Zaara-"+i, 1991 +1, "Love Story","1" , "2");
//	      persistMovie("Ravan"+i, 1991+i, "Love Story","1" , "2");
//	      persistMovie("Love"+i, 1991, "Love Story","1" , "2");
//	      persistMovie("Hum Appke"+i, 1991, "Love Story","1" , "2");
//	      persistMovie("Ek Do Teen"+i, 1991, "Love Story","1" , "2");
//	      persistMovie("Mardani"+i, 1991, "Love Story","1" , "2");
   }



   /**
    * Close my underlying Cassandra connection.
    */
   private void close()
   {
      client.close();
   }
}
