package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * Marks a person as paid in the address book.
 */
public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";
    public static final String MESSAGE_PAY_SUCCESS = "Marked as PAID: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the person identified by the index number as paid.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    /**
     * Constructor for PayCommand.
     * @param index of the person in the filtered person list to mark as paid
     */
    public PayCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Remark updatedRemark = new Remark("PAID");

        Person paidPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getNextLesson(), updatedRemark, personToEdit.getSubjects());

        model.setPerson(personToEdit, paidPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_PAY_SUCCESS, Messages.format(paidPerson)));
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof PayCommand
                && index.equals(((PayCommand) other).index));
    }
}
