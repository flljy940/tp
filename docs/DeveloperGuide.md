---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TutorRec Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org/). We would like to gratefully acknowledge the following libraries and dependencies that have been used in the development of our project:<br>

**Project Foundation**

* **[AddressBook-Level3](https://se-education.org/addressbook-level3/)**: for providing the foundational structure and core functionalities upon which our project is built.

**Software Dependencies**

* **[JavaFX](https://openjfx.io/)**: for building the custom graphical user interface (GUI).

* **[Jackson](https://github.com/FasterXML/jackson)**: for JSON serialisation and deserialisation.

* **[JUnit 5](https://github.com/junit-team/junit5)**: for unit testing.

* **[Flaticon](https://www.flaticon.com/)**: for sourcing icons and images used in the UI.

* **[Gradle](https://gradle.org/)**: for automation build and packaging processes.

**Documentation Dependencies**

* **[MarkBind](https://markbind.org/)**: for generating developer and user documentation.

* **[PlantUML](https://plantuml.com/)**: for creating UML diagrams to visualise software design and workflows.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-T16-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-T16-4/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initialises the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface.

Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

**API** : [`Ui.java`](https://github.com/AY2425S2-CS2103T-T16-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S2-CS2103T-T16-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S2-CS2103T-T16-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T16-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it uses the `ParserClasses` to parse the user command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g. `AddCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to add a product).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned from `Logic`.

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g. `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g. `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a student).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g. `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g. `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g. `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T16-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="600" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g. results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user's preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-T16-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Person

##### Edit a person

The implementation of person is entirely governed by the `Person` class. The `Person` class is immutable and guarantees that all fields are valid and immutable as well.

Thus editing a person details will create a new `Person` object. The items from the original `Person` will be copied entirely into the new `Person` object.

The following sequence diagram shows how the `Edit` command works:

<puml src="diagrams/EditSequenceDiagram.puml" alt="EditSequenceDiagram" />

#### Design considerations:

**Aspect: How to design the `Person` structure:**
* **Alternative 1:** Make `Person` mutable.
    * Pros: Easy to implement.
    * Cons: Modifying an object in place can lead to unintended side effects, making debugging harder.

* **Alternative 2 (current choice):** Make `Person` immutable.
    * Pros: Removes the need for listeners for UI to track person states.
    * Cons: The cost of editing a person may be huge.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialised with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th student in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new student. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the student was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarises what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the student being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.



--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)


<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* provides private tuition
* has to manage a significant number of contacts and addresses
* has to track whether payment has been made for various students
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Provides quick lookup for student address, contact number, whether last payment
has been made, next lesson date and time, and subjects covered with student


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                      | I want to …​                                              | So that I can…​                                                                                                |
|----------|----------------------------------------------|-----------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| `* * *`  | new user                                     | see usage instructions                                    | refer to instructions when I forget how to use the App                                                         |
| `* * *`  | tutor                                        | add a new student                                         | log all my students and their details                                                                          |
| `* * *`  | tutor                                        | add a student's address                                   | easily look up their address to go over and conduct a lesson                                                   |
| `* * *`  | tutor                                        | add a student's contact number                            | easily look up their contact number if I need to reach out to them                                             |
| `* * *`  | tutor                                        | add a student's email                                     | easily look up their email if I need to reach out to them                                                      |
| `* * *`  | tutor                                        | add a student's next lesson date                          | easily look up their next lesson date to see when my next lesson with them is                                  |
| `* * *`  | tutor                                        | add a student's subject taken                             | prepare materials for their lesson                                                                             |
| `* * *`  | tutor                                        | mark if a student has paid me for the previous lesson     | keep track of whether the student has paid me for their lessons                                                |
| `* * *`  | tutor                                        | delete a student                                          | remove student entries that I no longer need                                                                   |
| `* * *`  | tutor                                        | find a student by name                                    | locate details of students without having to go through the entire list                                        |
| `* *`    | tutor                                        | reset the payment status of all students to unpaid        | reset payment status for all student on a new week or month                                                    |
| `* *`    | tutor with many students in the address book | sort students by next lesson date                         | see my upcoming lessons in chronological order                                                                 |
| `* *`    | tutor                                        | filter students with lessons on a specified date          | see my scheduled lessons on the specified date                                                                 |
| `* *`    | tutor                                        | filter students who have paid for the previous lesson     | easily look up and track the students who have paid for the previous lesson                                    |
| `* *`    | tutor                                        | filter students who have not paid for the previous lesson | easily look up the students I have to nudge regarding payment for the previous lesson                          |
| `*`      | tutor                                        | filter students who are taking a specified subject        | see my students taking the specified subject to monitor results or reach out for testimonials for that subject |

### Use Cases

(For all use cases below, the **System** is `TutorRec` and the **Actor** is the `user`, unless specified otherwise)

**Use Case U1: Add a student to the contact list**

**MSS**

1.  User requests to add student and their details
2.  TutorRec adds the student with the supplied contact details

    Use case ends.

**Extensions**

* 1a. The user supplies invalid input parameter(s).

  * 1a1. TutorRec shows an error message for the relevant parameters.

    Use case resumes at step 1.



**Use Case U2: Delete a student from the contact list**

**MSS**

1.  User requests to list students
2.  TutorRec shows a list of students
3.  User requests to delete a specific student in the list
4.  TutorRec deletes the student

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 3a. The index supplied by the user is invalid.

    * 3a1. TutorRec shows an error message.

      Use case resumes at step 2.

**Use Case U3: Mark and unmark student payments**

**MSS**

1.  User requests to list students
2.  TutorRec shows a list of students
3.  User requests to mark or unmark payment for a specific student in the list or unmark payment for all students
4.  TutorRec marks or unmarks the payment(s)

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 3a. The index supplied by the user is invalid.

    * 3a1. TutorRec shows an error message.

      Use case resumes at step 2.

**Use Case U4: Add next lesson date and time to a student**

**MSS**

1.  User requests to list students
2.  TutorRec shows a list of students
3.  User requests to add a next lesson date and time for a specific student in the list
4.  TutorRec adds specified next lesson date and time to the student

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 3a. The index supplied by the user is invalid.

    * 3a1. TutorRec shows an error message.

      Use case resumes at step 2.

* 3b. The date or time supplied by the user is invalid or before the current time and date.

    * 3b1. TutorRec shows an error message.

      Use case resumes at step 2.


**Use Case U5: Sort by next lesson date and time**

**MSS**

1.  User requests to list students
2.  TutorRec shows a list of students
3.  User requests to see students in order of nearest lesson to furthest
4.  TutorRec shows the list of students sorted from nearest lesson to furthest

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

**Use Case U6: Filter students with a lesson on a specified date**

**MSS**

1.  User requests to list students
2.  TutorRec shows a list of students
3.  User requests to show students who have a lesson on a specified date
4.  TutorRec shows a list of students with a lesson on the specified date

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 3a. The date or time supplied by the user is invalid or before the current time and date.

    * 3a1. TutorRec shows an error message.

      Use case resumes at step 2.


### Non-Functional Requirements

#### Usability Requirements
- **Cross-Platform Support**: The application should run seamlessly on any _mainstream OS_ with Java 17 or above installed.
- **File Compatibility**: The data file format should be compatible with future versions of the application to ensure backward compatibility.
- **Accessibility**: A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

#### Performance Requirements
- **Response Time**: The system should respond to user commands within 2 seconds for up to 1000 student entries.
- **Startup Time**: The application should start up within 5 seconds on a _standard machine_.
- **Memory Usage**: The application should not exceed 512MB of memory usage during normal operation with 1000 student entries.

#### Scalability Requirements
- **Data Storage**: The application should be able to handle up to 10,000 student entries without significant performance degradation.
- **File Size**: The data file storing student information should not exceed 10MB for 1000 entries.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Standard Machine**: A machine with minimally 8GB RAM and an SSD.


<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Make sure you have Java 17 or above installed in your computer

   3. Open your terminal, cd into the folder you placed the jar file, then type java -jar tutorrec.jar and press enter
       Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by typing java -jar tutorrec.jar.<br>
       Expected: The most recent window size and location is retained.

3. Shutting down the app

   1. Click the 'X' button on the window of the app or the `esc` hotkey

   2. Re-launch the app by java -jar tutorrec.jar.<br>
       Expected: The app saves the most recent set of contacts before closing and shows the GUI with the latest set of contacts.

### Deleting a student

1. Deleting a student while all student are being shown

   1. Prerequisites: List all students using the `list` command. Multiple students in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No student is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a student while the list is filtered with students shown

   1. Prerequisites: Filter students using a filter condition of your choice (eg. `filter-payment unpaid` command to filter students with `Not Paid` payment status).

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list of filtered students. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No students is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the filtered list size)<br>
      Expected: Similar to previous.

   5. `list` after deleting student(s) from the filtered list should show the full list of students in the original unfiltered list excluding the deleted student(s).

### Saving data

1. Dealing with corrupted data files

   1. Prerequisites: Simulate a corrupted data file by:
      1. Change directory to the working folder for `tutorrec.jar`
      2. If the file `data/addressbook.json` is not in the working folder, launch the app by typing java -jar tutorrec.jar. The app should start with the sample contact list.
      3. Open the `data/addressbook.json` file and delete the `name` field of the first entry.

   2. Launch the app by typing java -jar tutorrec.jar. The app should start with an empty contact list.

2. Dealing with missing data files

   1. Prerequisites: Simulate a missing data file by:
      1. Change directory to the working folder for `tutorrec.jar`
      2. If the file `data/addressbook.json` exists in the working folder, delete the `data/addressbook.json` file

   2. Launch the app by typing java -jar tutorrec.jar. The app should start with the sample contact list.

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

1. **Allowing NextLesson to span over more than one day**
    Currently, our NextLesson feature requires the next lesson to start and end on the same day. However, tutors may hold longer tuition workshops that span multiple days or end past midnight. We plan to allow for that in the future.

2. **Improving input flexibility**
Currently, our input validation for names and addresses enforces strict formatting rules. We plan to enhance this by: (1) allowing special characters (e.g., '/', '.', '-', etc.) in names, and (2) implementing intelligent duplicate detection that ignores whitespace differences (e.g. recognising 'Alex Yeoh' and 'Alex   Yeoh' as duplicates). These improvements will give tutors more flexibility when recording student names and addresses, as well as to prevent accidental duplicate entries.