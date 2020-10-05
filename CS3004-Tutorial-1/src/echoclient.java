import java.io.*;
import java.net.*;

public class echoclient {
    public static void main(String[] args) throws IOException {
        //Set up the socket, in and out variables
        Socket echoSocket = null;
        PrintWriter echoClientOutput = null;
        BufferedReader echoClientInput = null;
        //Localhost is the generic name of the computer that your echoserver is running on
        //Change port 4321 to the port that your server is connected to
        //The code then connects the input and output

        try {
            echoSocket = new Socket("192.168.0.57", 4321);  //Make the connection to the server socket
            //Create the Output and Input
            echoClientOutput = new PrintWriter(echoSocket.getOutputStream(), true);
            echoClientInput = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Can't find host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 4321.");
            System.exit(1);
        }
        //Connect the user input
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromUser;
        String fromServer;
        //Type some input, press return and see what comes back
        System.out.println("echoclient up and running");
        while (true) {
            fromUser = stdIn.readLine();  //Read from the user
            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                echoClientOutput.println(fromUser); //Write to the server
            }
            fromServer = echoClientInput.readLine(); //Wait and read from the server
            System.out.println("Server: " + fromServer);
        }
    }
}
