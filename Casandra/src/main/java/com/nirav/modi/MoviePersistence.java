package com.nirav.modi;

import static java.lang.System.out;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.google.common.base.Optional;

/**
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
   public Optional<Movie> queryMovieByTitleAndYear(final String title, final int year)
   {
      final ResultSet movieResults = client.getSession().execute(
         "SELECT * from movies_keyspace.movies WHERE title = ? AND year = ?", title, year);
      final Row movieRow = movieResults.one();
      final Optional<Movie> movie =
           movieRow != null
         ? Optional.of(new Movie(
              movieRow.getString("title"),
              movieRow.getInt("year"),
              movieRow.getString("description"),
              movieRow.getString("mmpa_rating"),
              movieRow.getString("dustin_rating")))
         : null;
      return movie;
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



   /**
    * Close my underlying Cassandra connection.
    */
   private void close()
   {
      client.close();
   }
}
