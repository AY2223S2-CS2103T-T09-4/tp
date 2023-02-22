package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a remark associated with a person in an address book.
 * The remark is guaranteed to be immutable and valid.
 */
public class Remark {

    /**
     * The value of the remark.
     */
    public final String value;

    /**
     * Constructs a Remark object with the specified value.
     *
     * @param remark the value of the remark
     * @throws NullPointerException if the specified value is null
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    /**
     * Returns the value of the remark.
     *
     * @return the value of the remark
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if the specified object is equal to this Remark object.
     * Two Remark objects are considered equal if they have the same value.
     *
     * @param other the object to compare to this Remark object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    /**
     * Returns a hash code for this Remark object.
     * The hash code is based on the value of the remark.
     *
     * @return a hash code value for this Remark object
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
