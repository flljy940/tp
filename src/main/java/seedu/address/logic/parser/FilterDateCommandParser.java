package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FilterDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NextLessonEqualsDatePredicate;

/**
 * Parses input arguments and creates a new FilterDateCommand object
 */
public class FilterDateCommandParser implements Parser<FilterDateCommand> {

    public static final String MESSAGE_INVALID_DATE = "Invalid date format. Expected: 'd/M/yyyy' (e.g., 15/4/2025)";
    public static final String MESSAGE_INVALID_PAST_DATE = "Invalid date specified. Filter date cannot be in the past.";
    private static final String DATE_REGEX = "(\\d{1,2})/(\\d{1,2})/(\\d{4})";
    private static final LocalDate CURRENT_DATE = LocalDate.now();

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

        return new FilterDateCommand(parseFilterNextLessonDate(trimmedArgs));
    }

    private NextLessonEqualsDatePredicate parseFilterNextLessonDate(String dateString) throws ParseException {
        Pattern datePattern = Pattern.compile(DATE_REGEX);
        Matcher dateMatcher = datePattern.matcher(dateString);

        if (!dateMatcher.matches()) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }

        try {
            // Parse date components
            int day = Integer.parseInt(dateMatcher.group(1));
            int month = Integer.parseInt(dateMatcher.group(2));
            int year = Integer.parseInt(dateMatcher.group(3));

            LocalDate filterNextLessonDate = LocalDate.of(year, month, day);

            if (filterNextLessonDate.isBefore(CURRENT_DATE)) {
                throw new ParseException(MESSAGE_INVALID_PAST_DATE, new IllegalArgumentException());
            }

            return new NextLessonEqualsDatePredicate(filterNextLessonDate);
        } catch (DateTimeException e) {
            throw new ParseException(MESSAGE_INVALID_DATE, e);
        }
    }
}
