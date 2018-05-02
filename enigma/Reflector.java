// This is a SUGGESTED skeleton file.  Throw it away if you don't use it.
package enigma;

import java.util.Objects;

/** Class that represents a reflector in the enigma.
 *  @author
 */
class Reflector extends Rotor {

    // This needs other methods or constructors.

    //public Reflector(String reflectorName) {
    //    this.name = reflectorName;
    //}

    private String name;

    Reflector(String rotorName) {
        super(rotorName);
    }

    public String getPermutation() {
        for (int index = 10; index < PermutationData.ROTOR_SPECS.length; index++) {
            if (Objects.equals(PermutationData.ROTOR_SPECS[index][0], this.getName())) {
                return PermutationData.ROTOR_SPECS[index][1];
            }
        }
        return "Nope: This name is not a reflector. Look at Reflector for reference";
    }

    int convertForward(int p) {
        int reflected = toIndex(this.getPermutation().charAt(p));
        return reflected; //returns the ALPHABETICAL INDEX of the converted letter. IS THAT OKAY???
        // FIXME (Fixed Now?)
    }


    @Override
    boolean hasInverse() {
        return false;
    }

    /** Returns a useless value; should never be called. */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }

}
