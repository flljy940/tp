package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSampleAddressBook_returnsAddressBookWithSamplePersons() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        assertEquals(samplePersons.length, sampleAb.getPersonList().size());

        for (Person person : samplePersons) {
            assertTrue(sampleAb.getPersonList().contains(person));
        }
    }
}
