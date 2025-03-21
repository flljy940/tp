package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void equals() {
        final Remark remark = new Remark("Paid");
        final Remark sameRemark = new Remark("Paid");
        final Remark differentRemark = new Remark("Not Paid");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        assertTrue(remark.equals(sameRemark));

        // different values -> returns false
        assertFalse(remark.equals(differentRemark));

        // null object -> returns false
        assertFalse(remark.equals(null));

        // different type -> returns false
        assertFalse(remark.equals(new Remark("pmo")));

        // same hashcode -> returns true
        assertEquals(remark.hashCode(), sameRemark.hashCode());

        // different hashcode -> returns false
        assertFalse(remark.hashCode() == differentRemark.hashCode());
    }
}
