package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's next lesson date in the address book.
 */
public class NextLesson {
    public final String value;

    /**
     * Constructs a {@code NextLesson}.
     *
     * @param nextLesson A valid upcoming lesson date.
     */
    public NextLesson(String nextLesson) {
        requireNonNull(nextLesson);
        value = nextLesson;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NextLesson // instanceof handles nulls
            && value.equals(((NextLesson) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
