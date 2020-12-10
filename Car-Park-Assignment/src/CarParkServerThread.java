import java.net.*;
import java.io.*;

public class CarParkServerThread extends Thread  {


    private Socket CarParkServerSocket = null;
    private CarParkServerState mySharedCarParkStateObject;
    private String myCarParkServerThreadName;

    //Setup the thread
    public CarParkServerThread(Socket CarParkServerSocket, String CarParkServerThreadName, CarParkServerState SharedObject) {


        this.CarParkServerSocket = CarParkServerSocket;
        mySharedCarParkStateObject = SharedObject;
        myCarParkServerThreadName = CarParkServerThreadName;
    }

    public void run() {
        try {
            System.out.println(myCarParkServerThreadName + "initialising.");
            PrintWriter out = new PrintWriter(CarParkServerSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(CarParkServerSocket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                // Get a lock first
                try {
                    mySharedCarParkStateObject.acquireLock();
                    outputLine = mySharedCarParkStateObject.processInput(myCarParkServerThreadName, inputLine);
                    out.println(outputLine);
                    mySharedCarParkStateObject.releaseLock();
                }
                catch(InterruptedException e) {
                    System.err.println("Failed to get lock when reading:"+e);
                }
            }

            out.close();
            in.close();
            CarParkServerSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
