package seedu.address.model.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubjectTest {
    private static final String TEST_SUBJECT_1 = "math";
    private static final String TEST_SUBJECT_2 = "chemistry";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubjectName_throwsIllegalArgumentException() {
        String invalidSubjectName = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubjectName));
    }

    @Test
    public void isValidSubjectName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Subject.isValidSubjectName(null));
    }

    @Test
    public void equals() {
        Subject subject1 = new Subject(TEST_SUBJECT_1);

        // same object -> returns true
        assertTrue(subject1.equals(subject1));

        Subject subject1Copy = new Subject(TEST_SUBJECT_1);

        // same subjectName -> returns true
        assertTrue(subject1.equals(subject1Copy));

        // different type -> returns false
        assertFalse(subject1.equals("math"));

        // null -> returns false
        assertFalse(subject1.equals(null));

        Subject subject2 = new Subject(TEST_SUBJECT_2);

        // different subjectName -> returns false
        assertFalse(subject1.equals(subject2));
    }

    @Test
    public void hashCodeTest() {
        Subject subject1 = new Subject(TEST_SUBJECT_1);
        Subject subject2 = new Subject(TEST_SUBJECT_1);
        Subject subject3 = new Subject(TEST_SUBJECT_2);

        // same values -> same hash code
        assertEquals(subject1.hashCode(), subject2.hashCode());

        // different values -> different hash codes
        assertNotEquals(subject1.hashCode(), subject3.hashCode());
    }

    @Test
    public void toStringTest() {
        Subject subject1 = new Subject(TEST_SUBJECT_1);
        Subject subject2 = new Subject(TEST_SUBJECT_1);
        Subject subject3 = new Subject(TEST_SUBJECT_2);

        // same values -> same string representation
        assertEquals(subject1.toString(), subject2.toString());

        // different values -> different string representation
        assertNotEquals(subject1.toString(), subject3.toString());
    }
}
