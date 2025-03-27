package seedu.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code NextLessonDate} matches the date specified.
 */
public class NextLessonEqualsDatePredicate implements Predicate<Person> {
    private final LocalDate nextLessonDate;

    public NextLessonEqualsDatePredicate(LocalDate nextLessonDate) {
        this.nextLessonDate = nextLessonDate;
    }

    public String getNextLessonDateString() {
        return nextLessonDate.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
    }

    @Override
    public boolean test(Person person) {
        return nextLessonDate.equals(person.getNextLesson().getDate());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NextLessonEqualsDatePredicate)) {
            return false;
        }

        NextLessonEqualsDatePredicate otherNextLessonEqualsDatePredicate = (NextLessonEqualsDatePredicate) other;
        return nextLessonDate.equals(otherNextLessonEqualsDatePredicate.nextLessonDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("nextLessonDate", nextLessonDate).toString();
    }
}
