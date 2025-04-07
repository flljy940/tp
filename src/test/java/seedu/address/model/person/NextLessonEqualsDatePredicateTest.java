package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NextLessonEqualsDatePredicateTest {

    @Test
    public void equals() {
        LocalDate firstDate = LocalDate.of(2025, 6, 20);
        LocalDate secondDate = LocalDate.of(2025, 6, 25);

        NextLessonEqualsDatePredicate firstPredicate = new NextLessonEqualsDatePredicate(firstDate);
        NextLessonEqualsDatePredicate secondPredicate = new NextLessonEqualsDatePredicate(secondDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same dates -> returns true
        NextLessonEqualsDatePredicate firstPredicateCopy = new NextLessonEqualsDatePredicate(firstDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different dates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nextLessonEqualsDate_returnsTrue() {
        LocalDate date = LocalDate.of(2025, 6, 20);
        NextLessonEqualsDatePredicate predicate = new NextLessonEqualsDatePredicate(date);

        assertTrue(predicate.test(new PersonBuilder().withNextLesson("20/6/2025 1600-1800").build()));
    }

    @Test
    public void test_nextLessonNotEqualsDate_returnsFalse() {
        LocalDate date = LocalDate.of(2025, 6, 20);
        NextLessonEqualsDatePredicate predicate = new NextLessonEqualsDatePredicate(date);

        // empty NextLesson
        assertFalse(predicate.test(new PersonBuilder().build()));

        // different NextLesson dates
        assertFalse(predicate.test(new PersonBuilder().withNextLesson("15/6/2025 1600-1800").build()));
    }

    @Test
    public void toStringMethod() {
        LocalDate date = LocalDate.of(2025, 6, 20);
        NextLessonEqualsDatePredicate predicate = new NextLessonEqualsDatePredicate(date);

        String expected = NextLessonEqualsDatePredicate.class.getCanonicalName()
                + "{nextLessonDate=" + date + "}";
        assertEquals(expected, predicate.toString());
    }
}
