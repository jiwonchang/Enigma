// This is a SUGGESTED skeleton file.  Throw it away if you want.
package enigma;

import java.io.*;
//import java.sql.Ref;
import java.util.Objects;

/** Enigma simulator.
 *  @author
 */
public final class Main {


    static boolean checkedFirstLine = false;
    // WARNING: Not all methods that have code in them are complete!

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] args) {

        //MY TEST
        //System.out.println(standardize("Hello my name is Ji Won Chang"));

        Machine M;
        BufferedReader input = null;
        try {
            input = new BufferedReader(
                    new InputStreamReader(new FileInputStream(args[0])));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file found.");
        }

        String outputFilename;
        if (args.length >= 2) {
            outputFilename = args[1];
        } else {
            outputFilename = "output.txt";
        }

        //buildRotors();

        M = null;

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    M = new Machine();
                    configure(M, line);
                } else {
                    if (M == null) {
                        throw new EnigmaException("NULL MACHINE");
                    }
                    writeMessageLine(M.convert(standardize(line)),
                                     outputFilename);
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }


    }




    /** Return true iff LINE is an Enigma configuration line. */
    private static boolean isConfigurationLine(String line) {
        String[] configLine = line.split(" ");
        if (!configLine[0].equals("*") && !checkedFirstLine) {
            throw new EnigmaException("The configuration line is missing!");
        }
        if (configLine.length != 7 || configLine[6].length() != 4) {
            if (!checkedFirstLine) {
                throw new EnigmaException("Incorrect number of words in Configuration!");
            }
            return false;
        }
        boolean rotorCheck1 = false;
        boolean rotorCheck2 = false;
        boolean rotorCheck3 = false;
        boolean rotorCheck4 = false;
        boolean rotorCheck5 = false;
        boolean typeCheck1 = false;
        boolean typeCheck2 = false;
        boolean typeCheck3 = false;
        boolean typeCheck4 = false;
        String[] reflectorTypes = {"B", "C"};
        String[] fixedRotorTypes = {"BETA", "GAMMA"};
        String[] rotorTypes = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII"};
        if (!Objects.equals(configLine[0], "*")) {
            System.out.println(configLine[0]);
            return false;
        }
        for (int index = 0; index < reflectorTypes.length; index++) {
            if (Objects.equals(configLine[1], reflectorTypes[index])) {
                rotorCheck1 = true;
            }
        }
        for (int index = 0; index < fixedRotorTypes.length; index++) {
            if (Objects.equals(configLine[2], fixedRotorTypes[index])) {
                rotorCheck2 = true;
            }
        }
        for (int index = 0; index < rotorTypes.length; index++) {
            if (Objects.equals(configLine[3], rotorTypes[index])) {
                rotorCheck3 = true;
            }
        }
        for (int index = 0; index < rotorTypes.length; index++) {
            if (Objects.equals(configLine[4], rotorTypes[index])) {
                rotorCheck4 = true;
            }
        }
        for (int index = 0; index < rotorTypes.length; index++) {
            if (Objects.equals(configLine[5], rotorTypes[index])) {
                rotorCheck5 = true;
            }
        }
        for (int innerindex = 0; innerindex < Rotor.ALPHABET_SIZE; innerindex++) {
            if (Objects.equals(configLine[6].charAt(0), Rotor.toLetter(innerindex))) {
                typeCheck1 = true;
            }
        }
        for (int innerindex = 0; innerindex < Rotor.ALPHABET_SIZE; innerindex++) {
            if (Objects.equals(configLine[6].charAt(1), Rotor.toLetter(innerindex))) {
                typeCheck2 = true;
            }
        }
        for (int innerindex = 0; innerindex < Rotor.ALPHABET_SIZE; innerindex++) {
            if (Objects.equals(configLine[6].charAt(2), Rotor.toLetter(innerindex))) {
                typeCheck3 = true;
            }
        }
        for (int innerindex = 0; innerindex < Rotor.ALPHABET_SIZE; innerindex++) {
            if (Objects.equals(configLine[6].charAt(3), Rotor.toLetter(innerindex))) {
                typeCheck4 = true;
            }
        }

        if (rotorCheck1 && rotorCheck2 && rotorCheck3 && rotorCheck4 && rotorCheck5
                && typeCheck1 && typeCheck2 && typeCheck3 && typeCheck4) {
            checkedFirstLine = true;
            return true;
        }
        if (!checkedFirstLine) {
            throw new EnigmaException("For one reason or another, error!");
        }
        return false; // FIXME (FIXED NOW?)
    }

    /** Configure M according to the specification given on CONFIG,
     *  which must have the format specified in the assignment. */
    private static void configure(Machine M, String config) {
        String[] configLine =  config.split(" ");
        Reflector rotor1 = new Reflector(configLine[1]);
        FixedRotor rotor2 = new FixedRotor(configLine[2]);
        Rotor rotor3 = new Rotor(configLine[3]);
        Rotor rotor4 = new Rotor(configLine[4]);
        Rotor rotor5 = new Rotor(configLine[5]);
        Rotor[] rotors = {rotor1, rotor2, rotor3, rotor4, rotor5};
        M.replaceRotors(rotors);
        M.setRotors(configLine[6]);

        // FIXME (FIXED NOW?)
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks and tabs.  It is an error if LINE contains
     *  characters other than letters and blanks. */
    private static String standardize(String line) throws EnigmaException {
        String newLine = line.replaceAll(" ", "").toUpperCase();
        boolean charCheck;
        for (int index = 0; index < newLine.length(); index++) {
            charCheck = false;
            for (int innerIndex = 0; innerIndex < Rotor.ALPHABET_SIZE; innerIndex++) {
                if (newLine.charAt(index) == Rotor.toLetter(innerIndex)) {
                    charCheck = true;
                    break; // SO AS TO NOT ITERATE THROUGH THE ALPHABET IF MATCH IS FOUND.
                }
            }
            if (!charCheck) {
                throw new EnigmaException("There are non-letter characters in the message!");
            }
        }
        return newLine; // FIXME (FIXED NOW?)
    }

    /** Write MSG in groups of five to out file (except that the last
     *  group may have fewer letters). */
    private static void writeMessageLine(String msg, String filename) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            String outputString = "";
            for (int i = 0; i < msg.length(); i += 5) {
                outputString += msg.substring(i, Math.min(i + 5, msg.length()));
                if (i + 5 < msg.length()) {
                    outputString += " ";
                }
            }

            writer.write(outputString + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException when writing: " + e);
        }
    }

    //DON'T ACTUALLY NEED THIS CODE.
    /** Create all the necessary rotors. */
    //private static void buildRotors() {
        // FIXME
    //}

}

