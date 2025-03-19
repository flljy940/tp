package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs differentUserPrefs = new UserPrefs();

        // same object -> returns true
        assertTrue(userPrefs.equals(userPrefs));

        // different type -> returns false
        assertFalse(userPrefs.equals(5));

        // null object -> returns false
        assertFalse(userPrefs.equals(null));

        // different UserPrefs -> returns false
        differentUserPrefs.setGuiSettings(new GuiSettings(1, 1, 0, 0));
        assertFalse(userPrefs.equals(differentUserPrefs));

        // same hashcode -> returns true
        assertEquals(userPrefs.hashCode(), userPrefs.hashCode());

        // different hashcode -> returns false
        assertNotEquals(userPrefs.hashCode(), differentUserPrefs.hashCode());
    }

    @Test
    public void getUserPrefsFilePath_returnsCorrectPath() {
        UserPrefs userPrefs = new UserPrefs();
        assertEquals(java.nio.file.Paths.get("data", "addressbook.json"), userPrefs.getAddressBookFilePath());
    }

}
