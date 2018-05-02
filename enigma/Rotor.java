// This is a SUGGESTED skeleton file.  Throw it away if you don't use it.
package enigma;

import java.util.Objects;

/** Class that represents a rotor in the enigma machine.
 *  @author
 */
class Rotor {
    // This needs other methods, fields, and constructors.
    private static char[] ALPHABET = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                                      'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                                      'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                                      'Y', 'Z'};

    public char alphIndex(int index) {
        return ALPHABET[index];
    }

    // just a name for the rotor.
    private String name;

    // Constructor for making a rotor
    Rotor(String rotorName) {
        this.name = rotorName;
    }

    // sets the name of the Rotor (e.g. Rotor I, Rotor IV, Rotor B, Rotor Beta, etc.)
    void setName(String rotorName) {
        this.name = rotorName;
    }

    // method to get the name of the rotor.
    String getName() {
        return this.name;
    }

    // Returns the permutation string of a given rotor
    public String getPermutation() {
        for (int index = 0; index < PermutationData.ROTOR_SPECS.length; index++) {
            if (Objects.equals(PermutationData.ROTOR_SPECS[index][0], this.getName())) {
                return PermutationData.ROTOR_SPECS[index][1];
            }
        }
        return "Nope: This name is not a rotor. Look at Rotors for reference";

        /*
        if (this.getName() == "I") {
            return PermutationData.ROTOR_SPECS[0][1];
        } else if (this.getName() == "II") {
            return PermutationData.ROTOR_SPECS[1][1];
        } else if (this.getName() == "III") {
            return PermutationData.ROTOR_SPECS[2][1];
        } else if (this.getName() == "IV") {
            return PermutationData.ROTOR_SPECS[3][1];
        } else if (this.getName() == "V") {
            return PermutationData.ROTOR_SPECS[4][1];
        } else if (this.getName() == "VI") {
            return PermutationData.ROTOR_SPECS[5][1];
        } else if (this.getName() == "VII") {
            return PermutationData.ROTOR_SPECS[6][1];
        } else if (this.getName() == "VIII") {
            return PermutationData.ROTOR_SPECS[7][1];
        }
        return "Nope: This name is not a ROTOR. LOOK AT ROTORS for reference";
        */
    }

    // Returns the inverse permutation string of a given rotor
    public String getInvPermutation() {
        for (int index = 0; index < PermutationData.ROTOR_SPECS.length; index++) {
            if (Objects.equals(PermutationData.ROTOR_SPECS[index][0], this.getName())) {
                return PermutationData.ROTOR_SPECS[index][2];
            }
        }
        return "Nope: This name is not a rotor. Look at Rotors for reference";
    }

    // Returns the letter after which the notch will rotate the rotor.
    public String getNotchPoint() {
        for (int index = 0; index < PermutationData.ROTOR_SPECS.length; index++) {
            if (Objects.equals(PermutationData.ROTOR_SPECS[index][0], this.getName())) {
                return PermutationData.ROTOR_SPECS[index][3];
            }
        }
        return "Nope: This name is not a rotor. Look at Rotors for reference";
    }

    /** Size of alphabet used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    static char toLetter(int p) {
        return ALPHABET[p];  // FIXME (FIXED NOW?)
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    static int toIndex(char c) {
        for (int index = 0; index < ALPHABET_SIZE; index++) {  // FIXME (FIXED NOW?)
            if (ALPHABET[index] == c) {
                return index;
            }
        }
        System.out.println("The uppercase letter is not in the Alphabet Array!"); // my creation
        return -1; //random number; can remove.
    }

    /** Returns true iff this rotor has a ratchet and can advance. */
    boolean advances() {
        return true;
    }

    /** Returns true iff this rotor has a left-to-right inverse. */
    boolean hasInverse() {
        return true;
    }

    /** Return my current rotational setting as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    int getSetting() {
        return _setting;
    }

    /** Set getSetting() to POSN.  */
    void set(int posn) {
        assert 0 <= posn && posn < ALPHABET_SIZE;
        _setting = posn;
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        int entrance = ((p + this.getSetting()) % 26);
        int converted = toIndex(this.getPermutation().charAt(entrance));
        int exit = ((converted - this.getSetting()) + 26) % 26;
        return exit; //returns the ALPHABETICAL INDEX of the converted letter. IS THAT OKAY???

        //int convert = toIndex(this.getPermutation().charAt(p));
        //return convert;

        //return toIndex(this.getPermutation().charAt(p)) + this.getSetting(); // FIXME (Fixed Now?)
        //return toIndex(this.getPermutation().charAt(p)) + this.getSetting();
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int entrance = ((e + this.getSetting()) % 26);
        int converted = toIndex(this.getInvPermutation().charAt(entrance));
        int exit = ((converted - this.getSetting()) + 26) % 26;
        return exit; //returns the ALPHABETICAL INDEX of the converted letter. IS THAT OKAY???

        //int convert = toIndex(this.getPermutation().charAt(e));
        //return convert;
        // FIXME (Fixed Now?)
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        if (this.getNotchPoint().length() == 1) {
            if (this.getSetting() == toIndex(this.getNotchPoint().charAt(0))) {
                return true;
            }
            return false;
        } else if (this.getNotchPoint().length() > 1) {
            if (this.getSetting() == toIndex(this.getNotchPoint().charAt(0))
                    || this.getSetting() == toIndex(this.getNotchPoint().charAt(1))) {
                return true;
            }
            return false;
        }
        return false; // FIXME (Fixed Now?) PROBABLY
    }

    /** Advance me one position. */
    void advance() {
        if (advances()) {
            this.set((this.getSetting() + 1) % 26); // FIXME (Fixed Now?) PROBABLY
        }
    }

    /** My current setting (index 0..25, with 0 indicating that 'A'
     *  is showing). */
    private int _setting;

}
