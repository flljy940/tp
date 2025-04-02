package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.SubjectContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterSubjectCommand}.
 */
public class FilterSubjectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SubjectContainsKeywordsPredicate firstPredicate =
                new SubjectContainsKeywordsPredicate(Collections.singletonList("first"));
        SubjectContainsKeywordsPredicate secondPredicate =
                new SubjectContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterSubjectCommand filterFirstCommand = new FilterSubjectCommand(firstPredicate, "first");
        FilterSubjectCommand filterSecondCommand = new FilterSubjectCommand(secondPredicate, "second");

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterSubjectCommand filterFirstCommandCopy = new FilterSubjectCommand(firstPredicate, "first");
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(FilterSubjectCommand.MESSAGE_NO_MATCHES, "biology",
                "chemistry, math, physics");
        SubjectContainsKeywordsPredicate predicate = preparePredicate("biology");
        FilterSubjectCommand command = new FilterSubjectCommand(predicate, "biology");
        expectedModel.updateFilteredPersonList(predicate);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(FilterSubjectCommand.MESSAGE_SUCCESS, "math chemistry");
        SubjectContainsKeywordsPredicate predicate = preparePredicate("math", "chemistry");
        FilterSubjectCommand command = new FilterSubjectCommand(predicate, "math chemistry");
        expectedModel.updateFilteredPersonList(predicate);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code SubjectContainsKeywordsPredicate}.
     */
    private SubjectContainsKeywordsPredicate preparePredicate(String... userInput) {
        return new SubjectContainsKeywordsPredicate(Arrays.asList(userInput));
    }
} 