package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code PayStatus} matches the given status.
 */
public class PayStatusEqualsPaidPredicate implements Predicate<Person> {
    private final boolean isPaid;

    /**
     * Creates a PayStatusEqualsPaidPredicate to test if a person's payment status matches the given status.
     *
     * @param isPaid true if checking for paid status, false if checking for unpaid status
     */
    public PayStatusEqualsPaidPredicate(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public boolean test(Person person) {
        return person.getPayStatus().value.equalsIgnoreCase(isPaid ? PayStatus.PAID : PayStatus.NOT_PAID);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PayStatusEqualsPaidPredicate // instanceof handles nulls
                && isPaid == ((PayStatusEqualsPaidPredicate) other).isPaid); // state check
    }
}
