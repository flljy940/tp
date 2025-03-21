package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class SampleDataUtilTest {

    @Test
    public void getSampleAddressBook_verifyDataIntegrity() {
        ReadOnlyAddressBook firstCall = SampleDataUtil.getSampleAddressBook();
        ReadOnlyAddressBook secondCall = SampleDataUtil.getSampleAddressBook();

        // Verify same instance
        assertEquals(firstCall, secondCall);

        // Verify data immutability
        Person editedBob = new PersonBuilder(BOB).withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_MATH)
                .build();
        List<Person> persons = Arrays.asList(ALICE, editedBob);
        assertThrows(UnsupportedOperationException.class, () -> persons.add(persons.get(0)));

        // Verify specific sample data values
        Person firstPerson = persons.get(0);
        assertNotEquals(null, firstPerson.getName());
        assertNotEquals(null, firstPerson.getPhone());
        assertTrue(firstPerson.getPhone().toString().matches("\\d{8}"));
    }
}
