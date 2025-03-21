package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's next lesson date in the address book.
 */
public class NextLesson {

    public final LocalDateTime startDateTime;
    public final LocalDateTime endDateTime;

    /**
     * Constructs a {@code NextLesson}.
     *
     * @param startDateTime The start date and time of the lesson.
     * @param endDateTime The end date and time of the lesson.
     */
    public NextLesson(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        requireNonNull(startDateTime);
        requireNonNull(endDateTime);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Construct a {@code NextLesson}.
     *
     * @param nextLesson The string representation of next lesson date.
     */
    public NextLesson(String nextLesson) {
        requireNonNull(nextLesson, "Next lesson string cannot be null");

        if (nextLesson.isEmpty()) {
            this.startDateTime = null;
            this.endDateTime = null;
            return;
        }

        try {
            // For format like "15/04/2023 1430-1600"
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

            this.startDateTime = LocalDateTime.of(date, startTime);
            this.endDateTime = LocalDateTime.of(date, endTime);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid format: " + e.getMessage(), e);
        }
    }

    /**
     * Constructs an empty {@code NextLesson}.
     */
    public NextLesson() {
        this.startDateTime = null;
        this.endDateTime = null;
    }

    /**
     * Formats the lesson date and time as a string.
     */
    public String getFormattedNextLesson() {
        if (startDateTime == null || endDateTime == null) {
            return "";
        }

        String dateStr = startDateTime.getDayOfMonth()
                + " " + getMonthAbbreviation(startDateTime.getMonthValue())
                + " " + startDateTime.getYear();

        int startHour = startDateTime.getHour();
        int endHour = endDateTime.getHour();

        return dateStr + " " + startHour + "-" + endHour;
    }

    @Override
    public String toString() {
        return getFormattedNextLesson();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NextLesson)) {
            return false;
        }

        NextLesson otherNextLesson = (NextLesson) other;

        if (this.startDateTime == null && otherNextLesson.startDateTime == null) {
            return true;
        } else if (this.startDateTime == null || otherNextLesson.startDateTime == null) {
            return false;
        }

        return startDateTime.equals(otherNextLesson.startDateTime)
                && endDateTime.equals(otherNextLesson.endDateTime);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
        result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
        return result;
    }

    /**
     * Returns the next lesson information as a string in the format "dd/MM/yyyy HHMM-HHMM"
     */
    public String getValue() {
        if (startDateTime == null || endDateTime == null) {
            return "";
        }

        // Format the date from the startDateTime
        String dateStr = startDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Format the start and end times
        String startTimeStr = startDateTime.format(DateTimeFormatter.ofPattern("HHmm"));
        String endTimeStr = endDateTime.format(DateTimeFormatter.ofPattern("HHmm"));

        // Combine into the final format
        return String.format("%s %s-%s", dateStr, startTimeStr, endTimeStr);
    }

    private String getMonthAbbreviation(int month) {
        switch (month) {
        case 1: return "Jan";
        case 2: return "Feb";
        case 3: return "Mar";
        case 4: return "Apr";
        case 5: return "May";
        case 6: return "Jun";
        case 7: return "Jul";
        case 8: return "Aug";
        case 9: return "Sep";
        case 10: return "Oct";
        case 11: return "Nov";
        case 12: return "Dec";
        default: return "";
        }
    }
}
