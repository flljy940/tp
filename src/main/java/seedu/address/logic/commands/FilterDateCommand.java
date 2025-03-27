package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.NextLessonEqualsDatePredicate;

/**
 * Filters all persons in the address book by a NextLesson Date.
 */
public class FilterDateCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_SUCCESS = "Filtered %1$d students with lessons on %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters students with lessons on the specified date.\n"
            + "Parameters: DATE (in d/M/yyyy format)\n"
            + "Example:\n"
            + COMMAND_WORD + " 15/4/2025";

    private final NextLessonEqualsDatePredicate predicate;

    public FilterDateCommand(NextLessonEqualsDatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterDateCommand)) {
            return false;
        }

        FilterDateCommand otherFilterDateCommand = (FilterDateCommand) other;
        return predicate.equals(otherFilterDateCommand.predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS,
                        model.getFilteredPersonList().size(),
                        predicate.getNextLessonDateString()));
    }
}
