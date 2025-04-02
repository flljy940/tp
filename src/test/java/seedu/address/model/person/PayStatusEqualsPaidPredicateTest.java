package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PayStatusEqualsPaidPredicateTest {

    @Test
    public void equals() {
        PayStatusEqualsPaidPredicate firstPredicate = new PayStatusEqualsPaidPredicate(true);
        PayStatusEqualsPaidPredicate secondPredicate = new PayStatusEqualsPaidPredicate(true);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_isPaid_returnsTrue() {
        PayStatusEqualsPaidPredicate predicate = new PayStatusEqualsPaidPredicate(true);

        // paid status
        assertTrue(predicate.test(new PersonBuilder().withPayStatus(PayStatus.PAID).build()));
    }

    @Test
    public void test_isUnpaid_returnsTrue() {
        PayStatusEqualsPaidPredicate predicate = new PayStatusEqualsPaidPredicate(false);

        // unpaid status
        assertTrue(predicate.test(new PersonBuilder().withPayStatus(PayStatus.NOT_PAID).build()));
    }

    @Test
    public void test_isUnpaid_returnsFalse() {
        PayStatusEqualsPaidPredicate predicate = new PayStatusEqualsPaidPredicate(true);

        // unpaid status
        assertFalse(predicate.test(new PersonBuilder().withPayStatus(PayStatus.NOT_PAID).build()));
    }

    @Test
    public void test_isPaid_returnsFalse() {
        PayStatusEqualsPaidPredicate predicate = new PayStatusEqualsPaidPredicate(false);

        // paid status
        assertFalse(predicate.test(new PersonBuilder().withPayStatus(PayStatus.PAID).build()));
    }
} 