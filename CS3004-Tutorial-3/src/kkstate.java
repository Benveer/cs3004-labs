import java.net.*;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Taken from The Java Tutorial (Campione and Walrath)
/* Further modifications made to accommodate lab and home running
          Simon Taylor October 2011 */

public class kkstate{

    //These correspond to the states of KKServer

    private static final int WAITING = 0;
    private static final int QUESTION = 1;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    /* Variable declarations */


    private int state = WAITING;



    /* The processInput method */

    public String processInput(String theInput) {
        String theOutput = null;
        switch (state){
            case WAITING:
                //Get the joke going

                state = QUESTION;
                break;
            case QUESTION:
                //Check to see if it is the right message
                if (theInput.equalsIgnoreCase("What time is it?")) {
                    // if it is then send the joke
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());


                    theOutput = sdf.format(timestamp);

                    state = QUESTION;

                } else {  // if it is not then ask for a resend
                    theOutput = "You're supposed to ask \"What time is it??\" ";
                }
                break;



        }
        //Once we have the output message, pass it back to kkserver
        return theOutput;
    }
}