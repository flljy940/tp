package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Phone Numbers (Phone numbers are considered duplicates if
 * the phone number already exists in the address book).
 */
public class DuplicatePhoneException extends RuntimeException {
    public DuplicatePhoneException() {
        super("Operation would result in duplicate phone numbers");
    }
}
