package com.nirav.modi;

import java.util.Date;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

/**
 * @Reference from : http://java.dzone.com/articles/connecting-cassandra-java
 * Class used for connecting to Cassandra database.
 */
public class CassandraConnector
{
   /** Cassandra Cluster. */
   private Cluster cluster;

   /** Cassandra Session. */
   private Session session;
   
   
   /**
    * Main function for demonstrating connecting to Cassandra with host and port.
    *
    * @param args Command-line arguments; first argument, if provided, is the
    *    host and second argument, if provided, is the port.
    */
   public static void main(final String[] args)
   {
//      final CassandraConnector client = new CassandraConnector();
      final String ipAddress = args.length > 0 ? args[0] : "localhost";
      final int port = args.length > 1 ? Integer.parseInt(args[1]) : 9042;
      System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");
//      client.connect(ipAddress, port);
      MoviePersistence persistence = new MoviePersistence(ipAddress, port);
      System.out.println("Start Time :"+new Date());
      /*for(int i=0;i<100000;i++){
    	  persistence.createMovieList(i);
      }*/
      List<Movie> movies = persistence.queryMovieByTitleAndYear("Veer-Zaara-", 1991);
      movies.forEach(System.out::println);
      System.out.println("End Time :"+new Date());
//      client.close();
   }

   

   /**
    * Connect to Cassandra Cluster specified by provided node IP
    * address and port number.
    *
    * @param node Cluster node IP address.
    * @param port Port of cluster host.
    */
   public void connect(final String node, final int port)
   {
      this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
      final Metadata metadata = cluster.getMetadata();
      System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
      for (final Host host : metadata.getAllHosts())
      {
    	  System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
            host.getDatacenter(), host.getAddress(), host.getRack());
      }
      session = cluster.connect();
   }

   /**
    * Provide my Session.
    *
    * @return My session.
    */
   public Session getSession()
   {
      return this.session;
   }

   /** Close cluster. */
   public void close()
   {
      cluster.close();
   }
}
