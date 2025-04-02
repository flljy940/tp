package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHEMISTRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicatePhoneException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void containsPhone_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.containsPhone(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void containsPhone_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.containsPhone(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void containsPhone_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.containsPhone(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_MATH)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void containsPhone_personWithSamePhoneNumberInList_returnsTrue() {
        uniquePersonList.add(BOB);
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withSubjects(VALID_SUBJECT_CHEMISTRY)
                .build();
        assertTrue(uniquePersonList.containsPhone(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void add_duplicatePhone_throwsDuplicatePhoneException() {
        Person editedAmy = new PersonBuilder(AMY).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_AMY)
                .build();
        uniquePersonList.add(editedAmy);
        assertThrows(DuplicatePhoneException.class, () -> uniquePersonList.add(BOB));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_MATH)
                .build();
        uniquePersonList.setPerson(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void setPersons_listWithDuplicatePhone_throwsDuplicatePhoneException() {
        Person editedBob = new PersonBuilder(BOB).withPhone(VALID_PHONE_AMY).build();
        List<Person> listWithDuplicatePhone = Arrays.asList(AMY, editedBob);
        assertThrows(DuplicatePhoneException.class, () -> uniquePersonList.setPersons(listWithDuplicatePhone));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_returnsCorrectIterator() {
        // Test empty list
        Iterator<Person> emptyIterator = uniquePersonList.iterator();
        assertFalse(emptyIterator.hasNext());

        // Test adding and iterating through list
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);

        Iterator<Person> iterator = uniquePersonList.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(ALICE, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(BOB, iterator.next());

        // Test removal through iterator
        iterator.remove();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void sortByNextLesson_sortsCorrectly() {
        // Test empty list
        uniquePersonList.sortByNextLesson();
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);

        // Test list with one person
        uniquePersonList.add(CARL);
        uniquePersonList.sortByNextLesson();
        expectedUniquePersonList.add(CARL);
        assertEquals(expectedUniquePersonList, uniquePersonList);

        // Test list with multiple persons with sorting
        uniquePersonList.add(ALICE);
        uniquePersonList.sortByNextLesson();

        expectedUniquePersonList.remove(CARL);
        expectedUniquePersonList.add(ALICE);
        expectedUniquePersonList.add(CARL);

        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void sortByNextLesson_handlesEmptyNextLesson() {
        // Create persons with empty NextLesson
        Person editedAlice = new PersonBuilder().withName("Alice").withPhone("12345678").withNextLesson("").build();
        Person editedBob = new PersonBuilder().withName("Bob").withPhone("98765432")
                .withNextLesson(LocalDate.of(2025, 4, 10), LocalTime.of(10, 0), LocalTime.of(12, 0)).build();

        uniquePersonList.add(editedAlice);
        uniquePersonList.add(editedBob);
        uniquePersonList.sortByNextLesson();

        List<Person> sortedList = uniquePersonList.asUnmodifiableObservableList();
        assertEquals(editedBob, sortedList.get(0));
        assertEquals(editedAlice, sortedList.get(1));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(uniquePersonList.equals(uniquePersonList));

        // null -> returns false
        assertFalse(uniquePersonList.equals(null));

        // different types -> returns false
        assertFalse(uniquePersonList.equals(5));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }
}
