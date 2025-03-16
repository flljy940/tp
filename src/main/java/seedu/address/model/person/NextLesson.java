package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a Person's next lesson date in the address book.
 */
public class NextLesson {

    public final LocalDate date;
    public final LocalTime startTime;
    public final LocalTime endTime;

    /**
     * Constructs a {@code NextLesson}.
     *
     * @param date The date of the next lesson.
     * @param startTime The start time of the lesson.
     * @param endTime The end time of the lesson.
     */
    public NextLesson(LocalDate date, LocalTime startTime, LocalTime endTime) {
        requireNonNull(date);
        requireNonNull(startTime);
        requireNonNull(endTime);

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("d MMM")) + " " +
                startTime.format(DateTimeFormatter.ofPattern("Hmm")) + " " +
                endTime.format(DateTimeFormatter.ofPattern("Hmm"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof NextLesson)) {
            return false; // instanceof handles nulls
        }

        NextLesson that = (NextLesson) other;
        return date.equals(that.date)
                    && startTime.equals(that.startTime)
                    && endTime.equals(that.endTime); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, startTime, endTime);
    }
}
