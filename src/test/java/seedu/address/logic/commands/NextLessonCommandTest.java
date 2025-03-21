package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXTLESSON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXTLESSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NextLesson;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit test for NextLessonCommand.
 */
public class NextLessonCommandTest {

    private static final String NEXTLESSON_STUB = "15/04/2025 1900-2100";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNextLessonUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNextLesson(NEXTLESSON_STUB).build();

        NextLessonCommand nextLessonCommand = new NextLessonCommand(
                INDEX_FIRST_PERSON, new NextLesson(NEXTLESSON_STUB));

        String expectedMessage = String.format(
                NextLessonCommand.MESSAGE_ADD_NEXTLESSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(nextLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNextLessonUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNextLesson("").build();

        NextLessonCommand nextLessonCommand = new NextLessonCommand(
                INDEX_FIRST_PERSON, new NextLesson(editedPerson.getNextLesson().getValue()));

        String expectedMessage = String.format(
                NextLessonCommand.MESSAGE_DELETE_NEXTLESSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(nextLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNextLesson(NEXTLESSON_STUB).build();

        NextLessonCommand nextLessonCommand = new NextLessonCommand(
                INDEX_FIRST_PERSON, new NextLesson(editedPerson.getNextLesson().getValue()));

        String expectedMessage = String.format(
                NextLessonCommand.MESSAGE_ADD_NEXTLESSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(nextLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        NextLessonCommand nextLessonCommand = new NextLessonCommand(
                outOfBoundIndex, new NextLesson(VALID_NEXTLESSON_BOB));

        assertCommandFailure(nextLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        //ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NextLessonCommand nextLessonCommand = new NextLessonCommand(
                outOfBoundIndex, new NextLesson(VALID_NEXTLESSON_BOB));

        assertCommandFailure(nextLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final NextLessonCommand standardCommand = new NextLessonCommand(INDEX_FIRST_PERSON,
                new NextLesson(VALID_NEXTLESSON_AMY));

        // same values => returns true
        NextLessonCommand commandWithSameValues = new NextLessonCommand(INDEX_FIRST_PERSON,
                new NextLesson(VALID_NEXTLESSON_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> return false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NextLessonCommand(INDEX_SECOND_PERSON,
                new NextLesson(VALID_NEXTLESSON_BOB))));

        // different date -> returns false
        assertFalse(standardCommand.equals(new NextLessonCommand(INDEX_FIRST_PERSON,
                new NextLesson(VALID_NEXTLESSON_BOB))));
    }
}
