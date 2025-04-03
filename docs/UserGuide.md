---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TutorRec User Guide

TutorRec is a **desktop application designed to help tutors effectively manage their student records**.
While it features a clean and intuitive Graphical User Interface (GUI), TutorRec is **optimised for fast keyboard-based interaction via a Command Line Interface (CLI)**.
This makes it ideal for tutors who prefer speed and precision when handling tasks like tracking student contacts, subjects, payment statuses, next lessons and more.
Whether you're managing a few students or a large tutoring roster, TutorRec helps you stay organised faster than most traditional GUI-based apps.

<!-- * Table of Contents -->
<page-nav-print />

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-T16-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for TutorRec.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar tutorrec.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   
    <img src="images/Ui.png" alt="Ui" width="600px" />

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a student named `John Doe` to the contact list.

   * `delete 3` : Deletes the 3rd student shown in the current contact list.

   * `unpay all` : Resets all students to NOT PAID to indicate that payment has not been made by all students.

   * `nextlesson 3 d/15/4/2025 0900-1030` : Adds the next lesson date for the 3rd student as 15/4/2025 0900-1030.

   * `filter-subject math physics` : Shows all students taking math or physics, or both.

   * `clear` : Deletes all students.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [s/SUBJECT]` can be used as `n/John Doe s/math` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[s/SUBJECT]…​` can be used as ` ` (i.e. 0 times), `s/math`, `s/math s/chemistry` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME`

* Date format for next lesson must be in `d/M/yyyy HHmm-HHmm` format. <br>
  e.g. `nextlesson 3 d/15/4/2025 1800-2000`

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

<img src="images/helpMessage.png" alt="help message" width="600px" />

Format: `help`

<box type="tip" seamless>

**Tip:** F1 can be used to open/close the help window.
</box>

### Adding a student: `add`

Adds a student to the contact list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [s/SUBJECT]…​`

<box type="tip" seamless>

**Tip:** A student can have any number of subjects (including 0).
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe s/math e/betsycrowe@example.com a/Newgate p/1234567 s/physics`

### Listing all students : `list`

Shows a list of all students in the contact list.

Format: `list`

### Editing a student : `edit`

Edits an existing student in the contact list.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SUBJECT]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing subjects, the existing subjects of the student will be removed i.e. adding of subjects is not cumulative.
* You can remove all the student's subjects by typing `s/` without
    specifying any subjects after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower s/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing subjects.

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name of the student is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Students matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>

<img src="images/findAlexDavidResult.png" alt="result for 'find alex david'" width="600px" />

### Deleting a student : `delete`

Deletes the specified student from the contact list.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the contact list.
* `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

### Adding next lesson date for a student : `nextlesson`

Adds a date for an upcoming lesson for an existing student in the contact list.

Format: `nextlesson INDEX d/DATE`

* Adds the next lesson date for the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* Existing lesson dates will be updated to the input lesson dates.
* You can remove a student's next lesson by typing `d/` without any date after it.
* `DATE` must be in `d/M/yyyy HHmm-HHmm` format (e.g. `15/4/2025 1800-2000`)

Examples:
*  `nextlesson 2 d/` Removes the next lesson date for the 2nd student.
*  `nextlesson 1 d/15/4/2025 1800-2000` Adds/Updates the next lesson date of the 1st student to be `15/4/2025 1800-2000`.

<img src="images/nextlessonResult.png" alt="result for 'next lesson'" width="600px" />

### Sorting of all students by lesson date : `sort`

Sorts the list of students by their next lesson date and time, with the earliest lesson shown first.

Format: `sort`

* Those without lesson dates will be shifted to the end of the list, after all contacts with valid lesson dates.

### Marking that a student made payment : `pay`

Marks the specified student as PAID to indicate that payment has been made.

Format: `pay INDEX`

* Marks the student at the specific `INDEX` as PAID to indicate that payment has been made by the student.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `find David` followed by `pay 1` marks the 1st student in the results of the `find` command.
* `list` followed by `pay 2` marks the 2nd student as PAID in the contact list.

<img src="images/listPayResult.png" alt="result for 'list pay 2'" height="400px" />

### Resetting the payment statement of one student or all students : `unpay`

Resets the specified student's payment status to NOT PAID, or resets payment statuses for all students.

Format: `unpay INDEX` or `unpay all`

* Resets the payment status of the student at the specified `INDEX` to NOT PAID.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​
* Alternatively, using `unpay all` will reset the payment statuses of all students in the displayed list to NOT PAID.

Examples:
* `list` followed by `unpay 2` resets the payment status of the 2nd student in the contact list to NOT PAID.
* `find David` followed by `unpay 1` resets the payment status of the 1st student in the results of the `find` command.
* `unpay all` resets payment statuses for everyone in the current contact list.

### Filtering students by lesson date : `filter-date`

Filters and shows a list of all students whose next lesson date matches the specified date.

Format: `filter-date DATE`

* The DATE must be in `d/M/yyyy` format (e.g. `15/4/2025`)
* Shows a list of all students with lessons on the specified date
* The index numbers shown are used to identify the students

Example:
* `filter-date 15/4/2025` filters and shows you all students whose next lesson is on `15/4/2025`.

### Filtering students by payment status : `filter-payment`

Filters and shows a list of all students whose payment status matches the specified status.

Format: `filter-payment STATUS`

* The STATUS must be either `paid` or `unpaid` (case-insensitive)
* Shows a list of all students with the matching payment status
* The index numbers shown are used to identify the students

Examples:
* `filter-payment paid` shows a list of all students who have paid
* `filter-payment UNPAID` shows a list of all students who have not paid
* `filter-payment Paid` shows a list of all students who have paid (case-insensitive)

### Filtering students by subject : `filter-subject`

Filters and shows a list of all students whose subjects contain any of the specified subjects.

Format: `filter-subject SUBJECT [MORE_SUBJECTS]`

* The search is case-insensitive. e.g. `math` will match `Math`
* Multiple subjects can be specified to find students taking any of those subjects
* The order of the subjects you specify doesn't affect the search results
* Only complete subject names will be matched (e.g. "mat" will not match "math")

Examples:
* `filter-subject math` shows a list of all students taking Math
* `filter-subject math physics` shows a list of all students taking either Math or Physics or both
* `filter-subject MATH` shows a list of all students taking Math (case-insensitive)

### Clearing all entries : `clear`

Clears all entries from the contact list.

<box type="warning" seamless>

**Caution:** 
This action permanently removes all student records from TutorRec and cannot be undone. Make sure to back up your data before using this command if needed.
</box>

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

<box type="tip" seamless>

**Tip:** Escape key can be used to exit the program. All changes are saved automatically.
</box>

### Saving the data

TutorRec data will be saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TutorRec data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json` and `[JAR file location]/preferences.json`.

* `[JAR file location]/data/addressbook.json` stores data related to `student`.
* `[JAR file location]/preferences.json` stores data related to position and the size of the program.

Advanced users are welcome to update data directly by editing that data file.


<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, TutorRec will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause TutorRec to behave in unexpected ways (e.g. if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TutorRec home folder.

**Q**: Can I use TutorRec on multiple devices simultaneously?
**A**: TutorRec is designed as a desktop application for individual use. Using the same data file across multiple devices simultaneously may cause data conflicts or corruption.

**Q**: How do I back up my data?
**A**: You can create a backup by simply copying the `data/addressbook.json` file from your TutorRec home folder to another location periodically.

**Q**: What should I do if the application cannot start?
**A**: Ensure you have Java 17 or above installed correctly. If the issue persists, try deleting the `[JAR file location]/preferences.json` file and restarting the application.

**Q**: Can I import or export student data to other formats?
**A**: Currently, TutorRec does not support direct import/export functionality to other formats. You can manually edit the JSON data file if needed, but exercise caution as described in the [Editing the data file](#editing-the-data-file) section.
--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimise the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimised, and no new Help Window will appear. The remedy is to manually restore the minimised Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action         | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [s/SUBJECT]…​` <br> e.g. `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 s/math s/english`
**Clear**  | `clear`
**Pay**    | `pay INDEX`<br> e.g. `pay 3`
**Unpay**  | `unpay INDEX`<br> e.g. `unpay 3` or `unpay all`
**Delete** | `delete INDEX`<br> e.g. `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [s/SUBJECT]…​`<br> e.g.`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g. `find James Jake`
**NextLesson** | `nextlesson INDEX d/[DATE]`<br> e.g. `nextlesson 3 d/15/4/2025 1800-2000`
**List**   | `list`
**Sort**  | `sort`
**Filter NextLesson**    | `filter-date DATE`<br> e.g. `filter-date 15/4/2025`
**Filter PaymentStatus** | `filter-payment STATUS`<br> e.g. `filter-payment PAID`
**Filter Subject** | `filter-subject SUBJECT [MORE_SUBJECTS]`<br> e.g. `filter-subject MATH`
**Help**   | `help`
