import java.io.*;
import java.net.*;

/* Taken from The Java Tutorial (Campione and Walrath)
/* Further modifications made to accommodate lab and home running
          Simon Taylor October 2011 */

public class kkclient {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        // Change XXXX to the name of the computer that your kkserver is running
        // Change port 4444 to another number so you dont run into someone elses!
        // The code then connects the input and output

        //192.168.0.57
        try {
            kkSocket = new Socket("192.168.0.57", 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 4444.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised client and IO connections");

        /* Print out what the server says
         * Take the client's response and send it to the server */

        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("Bye."))
                break;

            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
            }
        }
        // Tidy up
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
}
