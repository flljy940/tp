package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.FilterDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NextLessonEqualsDatePredicate;

/**
 * Parses input arguments and creates a new FilterDateCommand object
 */
public class FilterDateCommandParser implements Parser<FilterDateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterDateCommand
     * and returns a FilterDateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterDateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterDateCommand.MESSAGE_USAGE));
        }

        return new FilterDateCommand(parseNextLessonDate(trimmedArgs));
    }

    private NextLessonEqualsDatePredicate parseNextLessonDate(String dateString) throws ParseException {
        try {
            LocalDate nextLessonDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("d/M/yyyy"));

            return new NextLessonEqualsDatePredicate(nextLessonDate);
        } catch (DateTimeException e) {
            throw new ParseException("Invalid date: " + e.getMessage(), e);
        }
    }
}
