import java.net.*;
import java.io.*;

/* Taken from The Java Tutorial (Campione and Walrath)
/* Further modifications made to accommodate lab and home running
          Simon Taylor October 2011 */

public class kkstate{

    //These correspond to the states of KKServer

    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;

    /* Variable declarations */

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    private String[] answers = { "Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?" };

    /* The processInput method */

    public String processInput(String theInput) {
        String theOutput = null;
        switch (state){
            case WAITING:
                //Get the joke going
                theOutput = "Knock! Knock!";
                state = SENTKNOCKKNOCK;
                break;
            case SENTKNOCKKNOCK:
                //Check to see if it is the right message
                if (theInput.equalsIgnoreCase("Who's there?")) {
                    // if it is then send the joke
                    theOutput = clues[currentJoke];
                    state = SENTCLUE;

                } else {  // if it is not then ask for a resend
                    theOutput = "You're supposed to say \"Who's there?\" - Try again. Knock! Knock!";
                }
                break;
            case SENTCLUE:
                // check to see if it the expected message
                if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                    // if it is then send the punch line and ask if they want another
                    theOutput = answers[currentJoke] + " Want another? (y/n)";
                    state = ANOTHER;
                } else { //if it is then start again by changing state SENTKNOCKKNOCK
                    theOutput = "You're supposed to say " + clues[currentJoke] + " who? Try again. Knock! Knock!";
                    state = SENTKNOCKKNOCK;
                }
                break;
            case ANOTHER:
                // if they want another then change joke and restart by changing state
                if (theInput.equalsIgnoreCase("y")) {
                    theOutput = "Knock! Knock!";
                    if (currentJoke == (NUMJOKES - 1))
                        currentJoke = 0;
                    else
                        currentJoke++;
                    state = SENTKNOCKKNOCK;
                } else {// If it is not then send Bye and finish
                    theOutput = "Bye.";
                }
                break;
        }
        //Once we have the output message, pass it back to kkserver
        return theOutput;
    }
}