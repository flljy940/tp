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

    public static final String MESSAGE_CONSTRAINTS = "Next lesson string cannot be null";
    public final LocalDate date;
    public final LocalTime startTime;
    public final LocalTime endTime;

    /**
     * Constructs a {@code NextLesson}.
     *
     * @param date      The date of next lesson.
     * @param startTime The start time of the lesson.
     * @param endTime   The end time of the lesson.
     */
    public NextLesson(LocalDate date, LocalTime startTime, LocalTime endTime) {
        requireNonNull(date);
        requireNonNull(startTime);
        requireNonNull(endTime);

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Construct a {@code NextLesson}.
     *
     * @param nextLesson The string representation of next lesson date.
     */
    public NextLesson(String nextLesson) {
        requireNonNull(nextLesson, MESSAGE_CONSTRAINTS);

        if (nextLesson.isEmpty()) {
            this.date = null;
            this.startTime = null;
            this.endTime = null;
            return;
        }

        try {
            // For format like "15/4/2023 1430-1600"
            int dashIndex = nextLesson.lastIndexOf('-');
            if (dashIndex == -1) {
                throw new IllegalArgumentException("Invalid format. Expected date with time range");
            }

            String datePart = nextLesson.substring(0, nextLesson.lastIndexOf(' '));
            String startTimePart = nextLesson.substring(nextLesson.lastIndexOf(' ') + 1, dashIndex);
            String endTimePart = nextLesson.substring(dashIndex + 1);

            LocalDate date = LocalDate.parse(datePart, DateTimeFormatter.ofPattern("d/M/yyyy"));
            LocalTime startTime = LocalTime.parse(startTimePart, DateTimeFormatter.ofPattern("HHmm"));
            LocalTime endTime = LocalTime.parse(endTimePart, DateTimeFormatter.ofPattern("HHmm"));

            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid format: " + e.getMessage(), e);
        }
    }

    /**
     * Constructs an empty {@code NextLesson}.
     */
    public NextLesson() {
        this.date = null;
        this.startTime = null;
        this.endTime = null;
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

    public boolean isEmpty() {
        return date == null && startTime == null && endTime == null;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true; // Same object
        }
        if (other == null || getClass() != other.getClass()) {
            return false; // Null or different class
        }
        NextLesson otherLesson = (NextLesson) other;

        return Objects.equals(date, otherLesson.date)
                && Objects.equals(startTime, otherLesson.startTime)
                && Objects.equals(endTime, otherLesson.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, startTime, endTime);
    }

    /**
     * Returns the next lesson information as a string in the format "d/M/yyyy HHmm-HHMmm
     */
    public String getValue() {
        if (isEmpty()) {
            return "";
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

        // Parser guarantees that either date, startTime and endTime will never be null
        String dateStr = date.format(dateFormatter);
        String startStr = startTime.format(timeFormatter);
        String endStr = endTime.format(timeFormatter);

        return String.format("%s %s-%s", dateStr, startStr, endStr).trim();
    }
}
