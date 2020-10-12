import java.net.*;
import java.io.*;

/* Taken from The Java Tutorial (Campione and Walrath)
/* Further modifications made to accommodate lab and home running
          Simon Taylor October 2011 */


public class kkmsserver{
    public static void main(String[] args) throws IOException {

        //Get the id of the local machine
        //Declare an object to store your computer's name
        InetAddress computerAddr = null;
        //Now store the local computer's name
        try{
            computerAddr = InetAddress.getLocalHost();
        }
        catch(UnknownHostException e){
            System.out.println(e);
        }
        //Now print it out to the screen
        //You will need to use this number in your client program
        //Anoying but necessary

        System.out.println("The address of this computer is... " + computerAddr.getHostName());

        // Now set up the server socket on port 4444 on the local machine
        // Change the port so that someone else does not connect to it
        //Set up the local variables

        ServerSocket serverSocket = null;
        boolean listening = true;

        // Make the server socket

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        System.out.println("Server started");

  /*Whenever a client attempts to connect on the
  serversocket setup above, setup a new connection.
  Do this forever

  The new connections are made by launching a new
  execution thread kkmsthread.  The argument
  serverSocket.accept means that the thread will
  connect to whichever client demanded the connection
  The start command just tells the kkmsthread object
  to begin execution.

  Every thread object has a run() method instead of a
  main() method.  Start() just tells the object to use
  the run() method to begin execution.  These threads
  will be timesliced by the operating system

  */

        while (listening){
            new kkmsthread(serverSocket.accept()).start();
            System.out.println("New server thread started");
        }
        serverSocket.close();
    }
}
