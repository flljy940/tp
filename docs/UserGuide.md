---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TutorRec User Guide

TutorRec is a **desktop app for managing contacts, optimised for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TutorRec can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-T16-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for TutorRec.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar tutorrec.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the contact list.

   * `delete 3` : Deletes the 3rd contact shown in the current list.
   
   * `pay 3` : Marks the 3rd contact as PAID to indicate that payment has been made.
   
   * `unpay 3` : Resets the 3rd contact to NOT PAID to indicate that payment has not been made.
   
   * `unpay all` : Resets all contacts to NOT PAID to indicate that payment has not been made by all contacts.

   * `clear` : Deletes all contacts.

   * `nextlesson 3 d/15/4/2025 1800-2000` : Add the next lesson date for the 3rd contact.  

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [s/SUBJECT]` can be used as `n/John Doe s/math` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[s/SUBJECT]…​` can be used as ` ` (i.e. 0 times), `s/math`, `s/math s/chemistry` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` 

* Date format for next lesson must be in `d/M/yyyy HHmm-HHmm` format, <br>
  e.g. `nextlesson 3 d/15/4/2025 1800-2000` 

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the contact list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [s/SUBJECT]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of subjects (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe s/math e/betsycrowe@example.com a/Newgate p/1234567 s/physics`

### Listing all persons : `list`

Shows a list of all persons in the contact list.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the contact list.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SUBJECT]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing subjects, the existing subjects of the person will be removed i.e adding of subjects is not cumulative.
* You can remove all the person’s subjects by typing `s/` without
    specifying any subjects after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower s/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing subjects.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the contact list.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the contact list.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding next lesson date for a person : `nextlesson`

Adds date for upcoming lesson for an existing person in the contact list.

Format: `nextlesson INDEX d/NEXT_LESSON`

* Adds the date of next lesson for the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* Existing values will be updated to the input values.
* When adding dates, the existing dates of the person will be appended i.e adding of tags is cumulative.
* You can remove all the person’s next lesson dates by typing `d/` without
  specifying any tags after it.

Examples:
*  `nextlesson 1 d/15/4/2025 1800-2000` Adds/Updates the date of next lesson of the 1st person to be `15/4/2025 1800-2000`.
*  `nextlesson 2 d/` Removes the next lesson date for the 2nd person.

### Marking that a person made payment : `pay`

Marks the specified person as PAID to indicate that payment has been made.

Format: `pay INDEX`

* Marks the person at the specific `INDEX` as PAID to indicate that payment has been made by the person.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `pay 2` marks the 2nd person as PAID in the contact list.
* `find David` followed by `pay 1` marks the 1st person in the results of the `find` command.

### Resetting the payment statement of one person or all persons : `unpay`

Resets the specified person’s payment status to NOT PAID, or resets payment statuses for all persons.

Format: `unpay INDEX` or `unpay all`

* Resets the payment status of the person at the specified `INDEX` to NOT PAID.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Alternatively, using `unpay all` will reset the payment statuses of all persons in the displayed list to NOT PAID.

Examples:
* `list` followed by `unpay 2` resets the payment status of the 2nd person in the contact list to NOT PAID.
* `find David` followed by `unpay 1` resets the payment status of the 1st person in the results of the `find` command.
* `unpay all` resets payment statuses for everyone in the current contact list.

### Filtering by next lesson date : `filter`

Filters and shows all persons whose next lesson date matches the specified input date.

Format: `filter DATE`

* The date refers to the date you wish to filter all persons by.
* `DATE` must be in `d/M/yyyy` format (eg. `15/4/2025` or `3/5/2025`)

Example:
* `filter 15/4/2025` filters and shows you all persons whose next lesson is on `15/4/2025`.

### Clearing all entries : `clear`

Clears all entries from the contact list.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TutorRec data will be saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TutorRec data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, TutorRec will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause TutorRec to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TutorRec home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimise the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimised, and no new Help Window will appear. The remedy is to manually restore the minimised Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [s/SUBJECT]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 s/math s/english`
**Clear**  | `clear`
**Pay**    | `pay INDEX`<br> e.g., `pay 3`
**Unpay**  | `unpay INDEX`<br> e.g., `unpay 3` or `unpay all`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [s/SUBJECT]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Nextlesson** | `nextlesson INDEX d/[NEXT_LESSON]`<br> e.g., `nextlesson 3 d/15/4/2025 1800-2000`
**List**   | `list`
**Help**   | `help`
