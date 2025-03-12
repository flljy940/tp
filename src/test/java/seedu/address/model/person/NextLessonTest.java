package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NextLessonTest {

    @Test
    public void equals() {
        NextLesson nextLesson = new NextLesson("yesterday");

        // same object -> returns true
        assertTrue(nextLesson.equals(nextLesson));

        // same values -> returns true
        NextLesson nextLessonCopy = new NextLesson(nextLesson.value);
        assertTrue(nextLesson.equals(nextLessonCopy));

        // different types -> returns false
        assertFalse(nextLesson.equals(1));

        // null -> returns false
        assertFalse(nextLesson.equals(null));

        // different nextLesson -> returns false
        NextLesson differentNextLesson = new NextLesson("tomorrow");
        assertFalse(nextLesson.equals(differentNextLesson));
    }
}
