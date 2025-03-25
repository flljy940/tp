package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NextLessonEqualsDatePredicate;
import seedu.address.model.person.NextLessonEqualsDatePredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FilterDateCommand.
 */
public class FilterDateCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        LocalDate firstDate = LocalDate.of(2025, 4, 20);
        LocalDate secondDate = LocalDate.of(2025, 4, 25);

        NextLessonEqualsDatePredicate firstPredicate = new NextLessonEqualsDatePredicate(firstDate);
        NextLessonEqualsDatePredicate secondPredicate = new NextLessonEqualsDatePredicate(secondDate);

        FilterDateCommand firstCommand = new FilterDateCommand(firstPredicate);
        FilterDateCommand secondCommand = new FilterDateCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        FilterDateCommand findFirstCommandCopy = new FilterDateCommand(firstPredicate);
        assertTrue(firstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_validDate_success() {
        String testDate = "30/4/2025";
        String expectedMessage = String.format(FilterDateCommand.MESSAGE_SUCCESS, 2, testDate);

        NextLessonEqualsDatePredicate predicate = preparePredicate(testDate);
        FilterDateCommand filterDateCommand = new FilterDateCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(filterDateCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL), model.getFilteredPersonList());
    }

    /**
     * Parses {@code dateInput} into a {@code NextLessonEqualsDatePredicate}.
     */
    private NextLessonEqualsDatePredicate preparePredicate(String dateInput) {
        LocalDate filterDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("d/M/yyyy"));
        return new NextLessonEqualsDatePredicate(filterDate);
    }
}
