import java.net.*;
import java.io.*;

public class echoserver {
    public static void main(String[] args) throws IOException {
        //Get the id of the local machine – not critical – included here just to show you how
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
        System.out.println("The address of this computer is... " + computerAddr.getHostName());
        //Now set up the server socket on port 4000-4999 on the local machine
        //Change the port number 4321 to anything in that range (unlikely there will be another process
        //running on that port)
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4321);  //Create the welcoming socket
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4321.");
            System.exit(1);
        }
        System.out.println("Echo server up and waiting");
        //Wait for client connection. When a client connects, make the link and carry on
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();  //Listen for incoming TCP requests
        } catch (IOException e) {
            System.err.println("Server socket failed.");
            System.exit(1);
        }
        // Connect the input and the output to and from the socket
        PrintWriter echoOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader echoInput =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;


        //Repeatedly loop getting input from the client and just send it back
        while ((inputLine = echoInput.readLine()) != null) {  //Wait for and read input from socket
            outputLine = inputLine;
            echoOutput.println(outputLine);
            System.out.println(outputLine);//Write output to socket
        }
    }
}
