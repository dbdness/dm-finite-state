package dk.group14;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DISCRETE MATHEMATICS ASSIGNMENT #4.
 *
 * Example of static log file, which is comma separated:
 * [INFO], 2, 1, 100, 1509015262
 * It is split into:
 *      1 : Level
 *      2 : System ID
 *      3 : Instance ID
 *      4 : Action ID - Like: Login, create item, comment
 *      5 : Timestamp in UNIX format
 *
 * ACTIONS
 * -------
 * 100  =   LOGIN
 * 50   =   LIST ITEMS
 * 500  =   EDIT ITEMS
 * 200  =   LOGOUT
 *
 *
 */
public class Main {

    private static final String REGEX_PATTERN = "([\\W]\\w+[\\W])\\W\\s(\\d*)\\W\\s(\\d*)\\W\\s(\\d*)\\W\\s(\\d*)";
    private static final File LOGFILE = new File("Finite_log.txt");
    private static final File STATE_FILE = new File("states.txt");
    private static Log log;

    /**
     * Run program to show pure skills of Discrete Mathematics
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        state();
    }


    /**
     * The method maps the USERID with every information else contained from the regular expression.
     * It opens a stream to read the content of the log file, groups the content, so it can be
     * added to the Log-object.
     * Lastly it map a userID with its current log information and adds it to a new file.
     *
     * A HashMap is used to avoid duplicates, so it always overrides the User-/SystemID
     * @throws IOException
     */
    private static void state() throws IOException {


        HashMap<Integer, Log> states = new HashMap<>();


        FileInputStream fileInputStream = new FileInputStream(LOGFILE);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream));
        BufferedWriter writer = new BufferedWriter(new FileWriter(STATE_FILE));

        StringBuilder sb = new StringBuilder();
        String line;

        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher;

        while ((line = buffer.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        String[] getLines = sb.toString().split("\\n");
        for (String s : getLines) {

            matcher = pattern.matcher(s);

            if (!matcher.matches()) {
                System.err.println("Bad log entry (or problem with Regular Expression?):");
                System.err.println(sb.toString());
            }

            log = new Log(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)), Long.parseLong(matcher.group(5)));

            states.put(Integer.parseInt(matcher.group(2)), log);
        }

        for (Map.Entry<Integer, Log> timeEntry : states.entrySet()) {
            long timestamp = ((System.currentTimeMillis() / 1000L) - timeEntry.getValue().getTimestamp()) / 60;
            System.out.println("USER: " + timeEntry.getKey() + " has been in state: " + timeEntry.getValue().getActionID() + " for: "
                    + timestamp + " minutes");

            writer.write("User: " + timeEntry.getKey() + ". State: " + timeEntry.getValue().getActionID()
                    + ". For: " + timestamp + " minutes\n" );
        }

        writer.close();
    }
}
