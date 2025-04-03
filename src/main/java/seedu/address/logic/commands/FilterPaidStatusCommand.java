package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.PayStatusEqualsPaidPredicate;

/**
 * Filters and lists all persons in address book whose payment status matches the given status.
 */
public class FilterPaidStatusCommand extends Command {

    public static final String COMMAND_WORD = "filter-payment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all students whose payment status matches "
            + "the specified status and displays them as a list with index numbers.\n"
            + "Parameters: STATUS (must be either 'paid' or 'unpaid')\n"
            + "Example: " + COMMAND_WORD + " paid";

    public static final String MESSAGE_SUCCESS = "Filtered %1$d students with payment status: %2$s";

    private final PayStatusEqualsPaidPredicate predicate;
    private final String status;

    /**
     * Creates a FilterPaidStatusCommand to filter persons by the specified payment status.
     *
     * @param predicate Predicate to test if a person's payment status matches the desired status
     * @param status The status string that was used to create the predicate
     */
    public FilterPaidStatusCommand(PayStatusEqualsPaidPredicate predicate, String status) {
        this.predicate = predicate;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS,
                        model.getFilteredPersonList().size(),
                        status));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterPaidStatusCommand
                && predicate.equals(((FilterPaidStatusCommand) other).predicate)
                && status.equals(((FilterPaidStatusCommand) other).status));
    }
}
