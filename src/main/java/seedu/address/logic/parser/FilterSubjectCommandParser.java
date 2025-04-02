package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FilterSubjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SubjectContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterSubjectCommand object
 */
public class FilterSubjectCommandParser implements Parser<FilterSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterSubjectCommand
     * and returns a FilterSubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterSubjectCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterSubjectCommand.MESSAGE_USAGE));
        }

        String[] subjectKeywords = trimmedArgs.split("\\s+");

        // Convert keywords to lowercase for case-insensitive matching
        String[] lowercaseKeywords = Arrays.stream(subjectKeywords)
                .map(String::toLowerCase)
                .toArray(String[]::new);

        return new FilterSubjectCommand(
                new SubjectContainsKeywordsPredicate(Arrays.asList(lowercaseKeywords)),
                trimmedArgs);
    }
}
