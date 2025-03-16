package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTLESSON;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NextLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NextLesson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses input arguments and creates a new NextLessonCommand object
 */
public class NextLessonCommandParser implements Parser<NextLessonCommand> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("Hmm");

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

        String dateTimeRange = argumentMultimap.getValue(PREFIX_NEXTLESSON).orElse("");

        try {
            String[] dateTimeParts = dateTimeRange.split("-");
            if (dateTimeParts.length != 2) {
                throw new ParseException("Invalid time range format. Expected 'startTime-endTime'.");
            }

            String dateString = dateTimeParts[0].trim();
            String timeString = dateTimeParts[1].trim();

            // Parse date (e.g., 16 Mar)
            LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);

            // Parse time range (e.g., 9-11)
            String[] times = timeString.split("-");
            if (times.length != 2) {
                throw new ParseException("Invalid time range format. Expected 'startTime-endTime'.");
            }

            LocalTime startTime = LocalTime.parse(times[0], TIME_FORMATTER);
            LocalTime endTime = LocalTime.parse(times[1], TIME_FORMATTER);

            // Create the NextLesson object with parsed date and time range
            NextLesson nextLesson = new NextLesson(date, startTime, endTime);
            return new NextLessonCommand(index, nextLesson);

        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date or time format.", e);
        }
    }
}
