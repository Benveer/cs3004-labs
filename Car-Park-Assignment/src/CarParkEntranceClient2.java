import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CarParkEntranceClient2 {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket CarParkClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int CarParkSocketNumber = 4545;
        String CarParkServerName = "localhost";
        String CarParkClientID = "CarParkEntranceClient2";

        try {
            CarParkClientSocket = new Socket(CarParkServerName, CarParkSocketNumber);
            out = new PrintWriter(CarParkClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(CarParkClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ CarParkSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + CarParkClientID + " client and IO connections");

        while (true) {

            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(CarParkClientID + " sending " + fromUser + " to CarParkServer");
                out.println(CarParkClientID+" "+fromUser);
            }
            fromServer = in.readLine();
            System.out.println(CarParkClientID + " received " + fromServer + " from CarParkServer");
        }

    }
}
