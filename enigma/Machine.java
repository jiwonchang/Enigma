// This is a SUGGESTED skeleton file.  Throw it away if you don't use it.
package enigma;

//import java.lang.reflect.Array;
//import java.util.ArrayList;

import java.util.Objects;

/** Class that represents a complete enigma machine.
 *  @author
 */
class Machine {

    // This needs other methods or constructors.
    Reflector rotor1;
    FixedRotor rotor2;
    Rotor rotor3;
    Rotor rotor4;
    Rotor rotor5;
    Rotor[] rotors = {rotor1, rotor2, rotor3, rotor4, rotor5};

    /** Set my rotors to (from left to right) ROTORS.  Initially, the rotor
     *  settings are all 'A'. */
    void replaceRotors(Rotor[] rotorInput) {
        this.rotors = rotorInput;
        // FIXME
    }

    /** Set my rotors according to SETTING, which must be a string of four
     *  upper-case letters. The first letter refers to the leftmost
     *  rotor setting.  */
    void setRotors(String setting) {
        rotors[1].set(Rotor.toIndex(setting.charAt(0)));
        rotors[2].set(Rotor.toIndex(setting.charAt(1)));
        rotors[3].set(Rotor.toIndex(setting.charAt(2)));
        rotors[4].set(Rotor.toIndex(setting.charAt(3)));
        // FIXME
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {

        if (Objects.equals(rotors[2].getName(), rotors[3].getName())
                || Objects.equals(rotors[2].getName(), rotors[4].getName())
                || Objects.equals(rotors[3].getName(), rotors[4].getName())) {
            throw new EnigmaException("There are repeating rotors!");
        } else if (!Objects.equals(rotors[0].getName(), "B")
                && !Objects.equals(rotors[0].getName(), "C")) {
            throw new EnigmaException("The reflectors are not actually reflectors!");
        } else if (!Objects.equals(rotors[1].getName(), "BETA")
                && !Objects.equals(rotors[1].getName(), "GAMMA")) {
            throw new EnigmaException("The fixed rotators are not actually fixed rotators!");
        }

        String newMsg = "";
        for (int index = 0; index < msg.length(); index++) {
            int letterConvert;
            if (rotors[4].atNotch() && rotors[3].atNotch()) {
                rotors[4].advance();
                rotors[3].advance();
                rotors[2].advance();
            } else if (rotors[4].atNotch()) {
                rotors[4].advance();
                rotors[3].advance();
            } else if (rotors[3].atNotch()) {
                rotors[4].advance();
                rotors[3].advance();
                rotors[2].advance();
            } else {
                rotors[4].advance();
            }
            letterConvert = rotors[4].convertForward(Rotor.toIndex(msg.charAt(index)));
            letterConvert = rotors[3].convertForward(letterConvert);
            letterConvert = rotors[2].convertForward(letterConvert);
            letterConvert = rotors[1].convertForward(letterConvert);
            letterConvert = rotors[0].convertForward(letterConvert);

            letterConvert = rotors[1].convertBackward(letterConvert);
            letterConvert = rotors[2].convertBackward(letterConvert);
            letterConvert = rotors[3].convertBackward(letterConvert);
            letterConvert = rotors[4].convertBackward(letterConvert);

            newMsg = newMsg + Rotor.toLetter(letterConvert);
        }
        return newMsg;
        //return null;
        // FIXME (FIXED NOW?)
    }
}
