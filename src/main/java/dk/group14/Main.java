package dk.group14;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example of log file, which is comma separated:
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

    private static final String REGEX_PATTERN = "([\\W]+\\w+[\\W])\\W\\s(\\d+)\\W\\s(\\d+)\\W\\s(\\d+)\\W\\s(\\d+)";
    private static final File LOGFILE = new File("Finite_log.txt");
    private static final File STATEFILE = new File("states.txt");

    public static void main(String[] args) throws IOException {

        state();
    }


    public static HashMap<Integer, Integer> state() throws IOException {

        HashMap<Integer, Integer> states = new HashMap<>();

        FileInputStream fileInputStream = new FileInputStream(LOGFILE);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream));
        BufferedWriter writer = new BufferedWriter(new FileWriter(STATEFILE));

        StringBuilder sb = new StringBuilder();
        String line;

        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher;

        while (( line = buffer.readLine() ) != null) {
            sb.append(line);
            sb.append("\n");
        }

        String[] getLines = sb.toString().split("\\n");
        for(String s : getLines) {

            matcher = pattern.matcher(s);

            if (!matcher.matches()) {
                System.err.println("Bad log entry (or problem with Regular Expression?):");
                System.err.println(sb.toString());
            }
            states.put(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(4)));

            writer.write(matcher.group(2) + " : " + matcher.group(4) + "\n");

        }
        writer.close();



        System.out.println(states);

        return states;
    }
}
