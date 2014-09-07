package com.nirav.modi;

/**
 * @Reference from : http://java.dzone.com/articles/connecting-cassandra-java
 * @author sanam
 *
 */
public class Movie {
	String title;
	int year;
	String description;
	String mmpaRating;
	String dustinRating;
	public Movie(String title, int year, String description, String mmpaRating,
			String dustinRating) {
		this.title =title;
		this.year=year;
		this.description=description;
		this.mmpaRating=mmpaRating;
		this.dustinRating=dustinRating;
	}
	@Override
	public String toString() {
		return "Movie [title=" + title + ", year=" + year + ", description="
				+ description + ", mmpaRating=" + mmpaRating
				+ ", dustinRating=" + dustinRating + "]";
	}
}
