package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PayStatusTest {

    @Test
    public void equals() {
        final PayStatus payStatus = new PayStatus(PayStatus.PAID);
        final PayStatus samePayStatus = new PayStatus(PayStatus.PAID);
        final PayStatus differentPayStatus = new PayStatus(PayStatus.UNPAID);

        // same object -> returns true
        assertTrue(payStatus.equals(payStatus));

        // same values -> returns true
        assertTrue(payStatus.equals(samePayStatus));

        // different values -> returns false
        assertFalse(payStatus.equals(differentPayStatus));

        // null object -> returns false
        assertFalse(payStatus.equals(null));

        // different type -> returns false
        assertFalse(payStatus.equals(new PayStatus("pmo")));

        // same hashcode -> returns true
        assertEquals(payStatus.hashCode(), samePayStatus.hashCode());

        // different hashcode -> returns false
        assertFalse(payStatus.hashCode() == differentPayStatus.hashCode());
    }
}
