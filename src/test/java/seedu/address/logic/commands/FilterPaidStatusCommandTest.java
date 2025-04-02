package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PayStatus;
import seedu.address.model.person.PayStatusEqualsPaidPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterPaidStatusCommand}.
 */
public class FilterPaidStatusCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PayStatusEqualsPaidPredicate firstPredicate = new PayStatusEqualsPaidPredicate(true);
        PayStatusEqualsPaidPredicate secondPredicate = new PayStatusEqualsPaidPredicate(false);

        FilterPaidStatusCommand firstCommand = new FilterPaidStatusCommand(firstPredicate, PayStatus.PAID);
        FilterPaidStatusCommand secondCommand = new FilterPaidStatusCommand(secondPredicate, PayStatus.NOT_PAID);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        FilterPaidStatusCommand firstCommandCopy = new FilterPaidStatusCommand(firstPredicate, PayStatus.PAID);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_filterPaid_success() {
        PayStatusEqualsPaidPredicate predicate = new PayStatusEqualsPaidPredicate(true);
        FilterPaidStatusCommand command = new FilterPaidStatusCommand(predicate, PayStatus.PAID);
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(FilterPaidStatusCommand.MESSAGE_SUCCESS,
                expectedModel.getFilteredPersonList().size(),
                PayStatus.PAID);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filterUnpaid_success() {
        PayStatusEqualsPaidPredicate predicate = new PayStatusEqualsPaidPredicate(false);
        FilterPaidStatusCommand command = new FilterPaidStatusCommand(predicate, PayStatus.NOT_PAID);
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(FilterPaidStatusCommand.MESSAGE_SUCCESS,
                expectedModel.getFilteredPersonList().size(),
                PayStatus.NOT_PAID);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_returnsNoStudentsMessage() {
        // Clear the address book to ensure no matching results
        model.setAddressBook(new ModelManager().getAddressBook());
        expectedModel.setAddressBook(new ModelManager().getAddressBook());

        PayStatusEqualsPaidPredicate predicate = new PayStatusEqualsPaidPredicate(true);
        FilterPaidStatusCommand command = new FilterPaidStatusCommand(predicate, PayStatus.PAID);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(FilterPaidStatusCommand.MESSAGE_SUCCESS,
                0, PayStatus.PAID);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        FilterPaidStatusCommand command = new FilterPaidStatusCommand(
                new PayStatusEqualsPaidPredicate(true), PayStatus.PAID);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    /**
     * Executes the command and confirms that
     * - the returned {@link CommandResult} matches {@code expectedCommandResult}
     * - the {@code actualModel} matches {@code expectedModel}
     */
    private void assertCommandSuccess(FilterPaidStatusCommand command, Model actualModel,
            String expectedMessage, Model expectedModel) {
        CommandResult result = command.execute(actualModel);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), actualModel.getFilteredPersonList());
    }
}
