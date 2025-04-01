package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
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
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnpayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_resetSinglePaidPerson_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person paidPerson = new PersonBuilder(firstPerson).withPayStatus("PAID").build();
        model.setPerson(firstPerson, paidPerson);

        UnpayCommand unpayCommand = new UnpayCommand(INDEX_FIRST_PERSON);
        Person unpaidPerson = new PersonBuilder(firstPerson).withPayStatus("").build();
        String expectedMessage = String.format(UnpayCommand.MESSAGE_UNPAY_SUCCESS, Messages.format(unpaidPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(paidPerson, unpaidPerson);

        assertCommandSuccess(unpayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_resetAll_success() {
        UnpayCommand unpayAllCommand = new UnpayCommand(true);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.getAddressBook().getPersonList().forEach(person -> {
            Person unpaidPerson = new PersonBuilder(person).withPayStatus("").build();
            expectedModel.setPerson(person, unpaidPerson);
        });

        assertCommandSuccess(unpayAllCommand, model, UnpayCommand.MESSAGE_UNPAY_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpayCommand unpayCommand = new UnpayCommand(invalidIndex);
        assertCommandFailure(unpayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnpayCommand unpayIndexOne = new UnpayCommand(INDEX_FIRST_PERSON);
        UnpayCommand unpayIndexTwo = new UnpayCommand(INDEX_SECOND_PERSON);
        UnpayCommand unpayAll = new UnpayCommand(true);
        UnpayCommand unpayAllDuplicate = new UnpayCommand(true);

        assertTrue(unpayIndexOne.equals(unpayIndexOne));

        assertTrue(unpayIndexOne.equals(new UnpayCommand(INDEX_FIRST_PERSON)));

        assertTrue(unpayAll.equals(unpayAllDuplicate));

        assertFalse(unpayIndexOne.equals(unpayIndexTwo));
        assertFalse(unpayIndexTwo.equals(unpayIndexOne));

        assertFalse(unpayIndexOne.equals(5));

        assertFalse(unpayIndexOne.equals(unpayAll));
        assertFalse(unpayAll.equals(unpayIndexTwo));

        assertFalse(unpayIndexOne.equals(null));

    }

    @Test
    public void execute_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnpayCommand((Index) null));
    }
}
