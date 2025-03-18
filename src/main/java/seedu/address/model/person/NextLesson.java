package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's next lesson date in the address book.
 */
public class NextLesson {

    public final LocalDateTime value;

    /**
     * Constructs a {@code NextLesson}.
     *
     * @param nextLesson The valid upcoming lesson date.
     */
    public NextLesson(String nextLesson) {
        requireNonNull(nextLesson);

        if (nextLesson.isEmpty()) {
            this.value = null;
        } else {
            this.value = LocalDateTime.parse(nextLesson, DateTimeFormatter.ofPattern("d MMM H-H"));
        }
    }

    /**
     * Formats the lesson date and time as a string.
     */
    public String getFormattedNextLesson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM H-H");
        return value.format(formatter);
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        return getFormattedNextLesson();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
        || (other instanceof NextLesson // instanceof handles nulls
        && value.equals(((NextLesson) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }
}
