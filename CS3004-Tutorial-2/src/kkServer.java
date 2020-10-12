import java.net.*;
import java.io.*;

/* Taken from The Java Tutorial (Campione and Walrath)
	 http://java.sun.com/docs/books/tutorial/index.html
	 and modified by Simon Taylor 18-10-98 */
/* Further modifications made to accommodate lab and home running
          Simon Taylor 29-10-02 */

public class kkServer {
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

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }
        System.out.println("Server up and waiting");
        // Wait for client connection
        // When a client connects, make the link and carry on
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        // Connect the input and the output to and from the socket
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        // Make the server internal state object
        kkstate kks = new kkstate();
        // Get the first message
        outputLine = kks.processInput(null);
        // And send it to the client
        out.println(outputLine);
        // Repeatedly loop getting input from the client
        // check it with the state object for the response
        // and send it to the client until the client
        // says Bye.
        while ((inputLine = in.readLine()) != null) {
            outputLine = kks.processInput(inputLine);
            out.println(outputLine);
            if (outputLine.equals("Bye."))
                break;
        }
        // Tidy up
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}