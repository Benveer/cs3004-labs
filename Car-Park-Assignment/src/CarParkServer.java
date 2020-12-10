import java.io.IOException;
import java.net.ServerSocket;

public class CarParkServer {

    public static void main(String[] args) throws IOException {

        ServerSocket CarParkServerSocket = null;
        boolean listening = true;
        String CarParkServerName = "CarParkServer";
        int CarParkServerNumber = 4545;
        double CarParkSpaces = 5;
        double CarQueue =0;
        double CarQueueTwo=0;

        //Create the shared object in the global scope

        CarParkServerState ourSharedCarParkStateObject = new CarParkServerState(CarParkSpaces, CarQueue,CarQueueTwo);

        // Make the server socket

        try {
            CarParkServerSocket = new ServerSocket(CarParkServerNumber);
        } catch (IOException e) {
            System.err.println("Could not start " + CarParkServerName + " specified port.");
            System.exit(-1);
        }
        System.out.println(CarParkServerName + " started");


        while (listening){

            new CarParkServerThread(CarParkServerSocket.accept(), "CarParkServerThread1", ourSharedCarParkStateObject).start();
            new CarParkServerThread(CarParkServerSocket.accept(), "CarParkServerThread2", ourSharedCarParkStateObject).start();
            new CarParkServerThread(CarParkServerSocket.accept(), "CarParkServerThread3", ourSharedCarParkStateObject).start();
            new CarParkServerThread(CarParkServerSocket.accept(), "CarParkServerThread4", ourSharedCarParkStateObject).start();
            System.out.println("New " + CarParkServerName + " thread started.");
        }
        CarParkServerSocket.close();
    }
}
