package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * Resets the payment status of a student (or all students) to NOT PAID.
 */
public class UnpayCommand extends Command {
    public static final String COMMAND_WORD = "unpay";
    public static final String MESSAGE_UNPAY_SUCCESS = "Reset payment for: %1$s";
    public static final String MESSAGE_UNPAY_ALL_SUCCESS = "Reset payment status for all students.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resets the payment status of the student identified by the index, or all students.\n"
            + "Parameters: INDEX (must be a positive integer) or 'all'\n"
            + "Examples:\n"
            + COMMAND_WORD + " 1\n"
            + COMMAND_WORD + " all";

    private final Index index;
    private final boolean isResetAll;

    public UnpayCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.isResetAll = false;
    }

    public UnpayCommand(boolean isResetAll) {
        this.index = null;
        this.isResetAll = isResetAll;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (isResetAll) {
            List<Person> lastShownList = model.getFilteredPersonList();
            for (Person person : lastShownList) {
                Person unpaidPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                        person.getAddress(), person.getNextLesson(), new Remark(""), person.getSubjects());
                model.setPerson(person, unpaidPerson);
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_UNPAY_ALL_SUCCESS);
        } else {
            List<Person> lastShownList = model.getFilteredPersonList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToEdit = lastShownList.get(index.getZeroBased());
            Person unpaidPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getNextLesson(), new Remark(""), personToEdit.getSubjects());

            model.setPerson(personToEdit, unpaidPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_UNPAY_SUCCESS, Messages.format(unpaidPerson)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UnpayCommand)) {
            return false;
        }
        UnpayCommand otherCommand = (UnpayCommand) other;
        return (isResetAll == otherCommand.isResetAll)
                && ((index == null && otherCommand.index == null)
                || (index != null && index.equals(otherCommand.index)));
    }
}
