package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTLESSON;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NextLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NextLesson;

/**
 * Parses input arguments and creates a new NextLessonCommand object
 */
public class NextLessonCommandParser implements Parser<NextLessonCommand> {
    /**
     * Parses the given {@code String} of arguments in the ocntext of the {@code NextLessonCommand}
     * and returns a {@code NextLessonCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NextLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NEXTLESSON);

        Index index;
        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE), ive);
        }

        String date = argumentMultimap.getValue(PREFIX_NEXTLESSON).orElse("");

        return new NextLessonCommand(index, new NextLesson(date));
    }
}
