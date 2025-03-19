package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("yesterday");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different nextLesson -> returns false
        NextLesson differentRemark = new NextLesson("tomorrow");
        assertFalse(nextLesson.equals(differentNextLesson));
    }

    @Test
    public void hashCodeMethod() {
        NextLesson lesson1 = new NextLesson("today");
        NextLesson lesson2 = new NextLesson("today");
        NextLesson lesson3 = new NextLesson("tomorrow");

        // Same value -> Same hash code
        assertEquals(lesson1.hashCode(), lesson2.hashCode());

        // Different values -> Different hash codes
        assertNotEquals(lesson1.hashCode(), lesson3.hashCode());
    }
}
