package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NextLessonTest {

    @Test
    public void equals() {
        NextLesson nextLesson = new NextLesson("14/4/2025 1400-1600");

        // same object -> returns true
        assertTrue(nextLesson.equals(nextLesson));

        // same values -> returns true
        NextLesson nextLessonCopy = new NextLesson(nextLesson.getValue());
        assertTrue(nextLesson.equals(nextLessonCopy));

        // different types -> returns false
        assertFalse(nextLesson.equals(1));

        // null -> returns false
        assertFalse(nextLesson.equals(null));

        // different nextLesson -> returns false
        NextLesson differentNextLesson = new NextLesson("15/4/2025 1900-2100");
        assertFalse(nextLesson.equals(differentNextLesson));
    }

    @Test
    public void hashCodeMethod() {
        NextLesson lesson1 = new NextLesson("14/4/2025 1400-1600");
        NextLesson lesson2 = new NextLesson("14/4/2025 1400-1600");
        NextLesson lesson3 = new NextLesson("15/4/2025 1900-2100");

        // Same value -> Same hash code
        assertEquals(lesson1.hashCode(), lesson2.hashCode());

        // Different values -> Different hash codes
        assertNotEquals(lesson1.hashCode(), lesson3.hashCode());
    }
}
