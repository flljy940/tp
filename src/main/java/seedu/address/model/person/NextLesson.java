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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMM");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("Hmm");

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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return String.format("%s %s-%s",
                date.format(DATE_FORMATTER),
                startTime.format(TIME_FORMATTER),
                endTime.format(TIME_FORMATTER));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true; // short circuit if same object
        if (!(other instanceof NextLesson)) return false; // instanceof handles nulls
        NextLesson that = (NextLesson) other;
        return Objects.equals(date, that.date)
                    && Objects.equals(startTime, that.startTime)
                    && Objects.equals(endTime, that.endTime); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, startTime, endTime);
    }
}
