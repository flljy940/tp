package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTLESSON;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NextLesson;
import seedu.address.model.person.Person;

/**
 * Changes the next lesson date of an existing student in the address book.
 */
public class NextLessonCommand extends Command {

    public static final String COMMAND_WORD = "nextlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the date of next lesson of the student identified "
            + "by the index number used in the last person listing. "
            + "Existing date will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NEXTLESSON + "[DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NEXTLESSON + "16 Mar 9-12.";

    public static final String MESSAGE_ADD_NEXTLESSON_SUCCESS = "Added Next Lesson to Person: %1$s\"";
    public static final String MESSAGE_DELETE_NEXTLESSON_SUCCESS = "Removed Next Lesson to Person: %1$s\"";

    private final Index index;
    private final NextLesson nextLesson;

    /**
     * @param index of the student in the list to update the date of the next lesson
     * @param nextLesson date of the student to be updated to
     */
    public NextLessonCommand(Index index, NextLesson nextLesson) {
        requireAllNonNull(index, nextLesson);

        this.index = index;
        this.nextLesson = nextLesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), nextLesson, personToEdit.getRemark(), personToEdit.getSubjects()
        );
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the next lesson date is added to or removed from
     * {@code personToEdit}
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !nextLesson.toString().isEmpty()
                ? MESSAGE_ADD_NEXTLESSON_SUCCESS
                : MESSAGE_DELETE_NEXTLESSON_SUCCESS;

        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NextLessonCommand)) {
            return false;
        }

        // state check
        NextLessonCommand otherCommand = (NextLessonCommand) other;
        return index.equals(otherCommand.index)
                && nextLesson.equals(otherCommand.nextLesson);
    }

    /**
     * Parses a string input like "16 Mar 9-11" into NextLesson object
     * @param input the string to parse
     * @return the NextLesson object
     */
    public static NextLesson parseNextLesson(String input) {
        String[] dateTimeParts = input.split(" ");
        if (dateTimeParts.length != 2) {
            throw new IllegalArgumentException("Invalid date and time format. ");
        }

        // Parse date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM");
        LocalDate date = LocalDate.parse(dateTimeParts[0], dateFormatter);

        // Parse time range (e.g., 930-1130)
        String[] timeParts = dateTimeParts[1].split("-");
        if (timeParts.length != 2) {
            throw new IllegalArgumentException("Invalid time format. ");
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("Hmm");
        LocalTime startTime = LocalTime.parse(timeParts[0], timeFormatter);
        LocalTime endTime = LocalTime.parse(timeParts[1], timeFormatter);

        return new NextLesson(date, startTime, endTime);
    }
}
