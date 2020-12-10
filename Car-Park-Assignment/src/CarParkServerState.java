public class CarParkServerState {

    private CarParkServerState mySharedObj;
    private String myThreadName;
    private double CAR_PARK_SPACES;
    private double CAR_QUEUE;
    private double CAR_QUEUE_TWO;

    private boolean accessing=false; // true a thread has a lock, false otherwise
    private int threadsWaiting=0; // number of waiting writers

// Constructor

    CarParkServerState(double CarParkSpace,double CarQueue,double CarQueueTwo) {
        CAR_PARK_SPACES = CarParkSpace;
        CAR_QUEUE=CarQueue;
        CAR_QUEUE_TWO=CarQueueTwo;
    }

//Attempt to aquire a lock

    public synchronized void acquireLock() throws InterruptedException{
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName()+" is attempting to acquire a lock!");
        ++threadsWaiting;
        while (accessing) {  // while someone else is accessing or threadsWaiting > 0
            System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
            //wait for the lock to be released - see releaseLock() below
            wait();
        }
        --threadsWaiting;
        accessing = true;
        System.out.println(me.getName()+" got a lock!");
    }

    // Releases a lock to when a thread is finished

    public synchronized void releaseLock() {
        //release the lock and tell everyone
        accessing = false;
        notifyAll();
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName()+" released a lock! ");
    }


    /* The processInput method */


    public synchronized String processInput(String myThreadName, String theInput) {
        System.out.println(myThreadName + " received "+ theInput);
        String theOutput = null;

        if (theInput.equals("CarParkEntranceClient1 Car entering") || theInput.equals("CarParkEntranceClient2 Car entering"))
        {
            if ( CAR_PARK_SPACES>0)
            {
                CAR_PARK_SPACES--;
                System.out.println(myThreadName + " Car has entered. " + CAR_PARK_SPACES+ " Spaces left. ");
                theOutput = " Car has entered the car park. " + CAR_PARK_SPACES+ " Spaces left. ";

            }
            else if (theInput.equals("CarParkEntranceClient1 Car entering") && CAR_PARK_SPACES ==0)
            {
                CAR_QUEUE++;
                System.out.println(myThreadName + " No spaces available. ");
                theOutput = "No space in car park, car added to queue. "  + CAR_QUEUE + " in queue. ";
            }
            else if (theInput.equals("CarParkEntranceClient2 Car entering") && CAR_PARK_SPACES==0)
            {
                CAR_QUEUE_TWO++;
                System.out.println(myThreadName + " No spaces available. ");
                theOutput = "No space in car park, car added to queue. " + CAR_QUEUE_TWO + " in queue. ";
            }
        }


        else if (theInput.matches("CarParkEntranceClient1(.*)") || theInput.matches("CarParkEntranceClient2(.*)"))
        {
            theOutput = myThreadName + " received incorrect request - only understand \"Car entering\" ";

        }


        else if (theInput.equals("CarParkExitClient1 Car exiting") || theInput.equals("CarParkExitClient2 Car exiting") )
        {
            if (CAR_PARK_SPACES<5 )
            {

                CAR_PARK_SPACES++;
                System.out.println(myThreadName + " Car exiting " + CAR_PARK_SPACES);
                theOutput = "Car has left the car park.  Space is now available = " + CAR_PARK_SPACES;

                if (CAR_QUEUE>0)
                {
                    CAR_QUEUE--;
                    CAR_PARK_SPACES--;
                    System.out.println(myThreadName + " Queued car entering from entrance 1. " + CAR_PARK_SPACES + " Space(s) available ");
                    theOutput = "Space is now available.  Queued car entering from entrance 1.  " + CAR_PARK_SPACES + " Space(s) available ";
                }
                else if (CAR_QUEUE_TWO>0)
                {

                    CAR_QUEUE_TWO--;
                    CAR_PARK_SPACES--;
                    System.out.println(myThreadName + " Queued car entering from entrance 2. " + CAR_PARK_SPACES + " Space(s) available ");
                    theOutput = "Space is now available.  Queued car entering from entrance 2. " + CAR_PARK_SPACES + " Space(s) available ";
                }


            }
            else
            {
                System.out.println(myThreadName + " There are no cars parked  " + CAR_PARK_SPACES + " Spaces left ");
                theOutput = "There are no cars parked! " + CAR_PARK_SPACES + " Spaces left. ";

            }

        }



        else if (theInput.matches("CarParkExitClient1(.*)") || theInput.matches("CarParkExitClient2(.*)"))
        {
            theOutput = myThreadName + " received incorrect request - only understand \"Car exiting\"";

        }

        System.out.println(theOutput);
        return theOutput;
    }

}
