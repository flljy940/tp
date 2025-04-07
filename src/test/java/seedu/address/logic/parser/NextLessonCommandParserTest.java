package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTLESSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.NextLessonCommandParser.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.logic.parser.NextLessonCommandParser.MESSAGE_INVALID_PAST_LESSON;
import static seedu.address.logic.parser.NextLessonCommandParser.MESSAGE_INVALID_START_BEFORE_END;
import static seedu.address.logic.parser.NextLessonCommandParser.MESSAGE_INVALID_START_END_TIME;
import static seedu.address.logic.parser.NextLessonCommandParser.MESSAGE_INVALID_TIME;
import static seedu.address.logic.parser.NextLessonCommandParser.MESSAGE_INVALID_YEAR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NextLessonCommand;
import seedu.address.model.person.NextLesson;

public class NextLessonCommandParserTest {

    // Test data to test date and time parsing
    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private static final LocalTime CURRENT_TIME = LocalTime.now();
    private final NextLessonCommandParser parser = new NextLessonCommandParser();
    private final String validLessonDateTime =
            CURRENT_DATE.format(java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy"))
            + " " + CURRENT_TIME.plusMinutes(1).format(java.time.format.DateTimeFormatter.ofPattern("HHmm"))
            + "-" + CURRENT_TIME.plusMinutes(3).format(java.time.format.DateTimeFormatter.ofPattern("HHmm"));
    private final String invalidLessonDateTime =
            CURRENT_DATE.minusDays(1).format(java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy"))
            + " " + CURRENT_TIME.minusMinutes(1).format(java.time.format.DateTimeFormatter.ofPattern("HHmm"))
            + "-" + CURRENT_TIME.plusMinutes(1).format(java.time.format.DateTimeFormatter.ofPattern("HHmm"));

    @Test
    public void parse_indexSpecified_success() {
        // have date of next lesson
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NEXTLESSON + validLessonDateTime;
        NextLessonCommand expectedCommand =
                new NextLessonCommand(INDEX_FIRST_PERSON, new NextLesson(validLessonDateTime));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no date of next lesson
        userInput = targetIndex.getOneBased() + " " + PREFIX_NEXTLESSON;
        expectedCommand = new NextLessonCommand(INDEX_FIRST_PERSON, new NextLesson());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, NextLessonCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, NextLessonCommand.COMMAND_WORD + " " + validLessonDateTime, expectedMessage);

        // no field
        assertParseFailure(parser, "1", expectedMessage);
    }

    @Test
    void parseNextLesson_invalidDateFormat_throwsParseException() {
        String[] invalidInputs = {
            "15-4-2025 0900-1100", // Wrong separator in date
            "15/4/2025 9:00-11:00", // Wrong time format
            "2025/4/15 0900-1100", // Wrong date format order
            "15/4/2025 0900", // Missing end time
            "15/4/2025-1100", // Missing start time
            "15/4/2025" // Only date, no time range
        };

        for (String invalidInput : invalidInputs) {
            assertParseFailure(parser, invalidInput,
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE));
        }
    }

    @Test
    void constructor_invalidTimeValues_throwsException() {
        String[] invalidTimes = {
            "15/4/2025 2500-1100", // Invalid start hour (25:00)
            "15/4/2025 0900-6060", // Invalid end minute (60:60)
            "15/4/2025 2399-1200" // Invalid start minute (23:99)
        };

        for (String input : invalidTimes) {
            assertParseFailure(parser, input,
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE));
        }
    }

    @Test
    void constructor_startTimeNotBeforeEndTime_throwsException() {
        String[] invalidStartEndTimes = {
            "15/6/2025 1200-1200", // Start time == End time
            "15/6/2025 1500-1400" // Start time > End time
        };

        for (String input : invalidStartEndTimes) {
            assertParseFailure(parser, input,
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE));
        }
    }

    @Test
    void constructor_pastDate_throwsParseException() {
        String pastDate = CURRENT_DATE.minusDays(1).format(java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy"))
            + " 0900-1100";

        assertParseFailure(parser, pastDate,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInput_success() {
        String validTimeRangeInput = "1 " + PREFIX_NEXTLESSON + "15/6/2025 1900-2200";
        assertParseSuccess(parser, validTimeRangeInput,
                new NextLessonCommand(INDEX_FIRST_PERSON, new NextLesson("15/6/2025 1900-2200")));

        String validDateTimeInput = "1 " + PREFIX_NEXTLESSON + validLessonDateTime;
        assertParseSuccess(parser, validDateTimeInput,
                new NextLessonCommand(INDEX_FIRST_PERSON, new NextLesson(validLessonDateTime)));
    }

    @Test
    public void parse_invalidInput_throwsParseException() {
        // Invalid index
        String invalidIndexInput = "a " + PREFIX_NEXTLESSON + "15/6/2025 1900-2100";
        assertParseFailure(parser, invalidIndexInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE));

        // Invalid date format
        String invalidDateFormatInput = "1 " + PREFIX_NEXTLESSON + "15-4-2025 1900-2100";
        assertParseFailure(parser, invalidDateFormatInput,
                String.format(NextLessonCommandParser.MESSAGE_CONSTRAINTS));

        // Invalid time format
        String invalidTimeFormatInput = "1 " + PREFIX_NEXTLESSON + "15/6/2025 19:00-21:00";
        assertParseFailure(parser, invalidTimeFormatInput,
                String.format(NextLessonCommandParser.MESSAGE_CONSTRAINTS));

        // Invalid time ranges -> invalid
        String invalidTimeRangeInput = "1 " + PREFIX_NEXTLESSON + "15/6/2025 1960-2200";
        assertParseFailure(parser, invalidTimeRangeInput, MESSAGE_INVALID_TIME);
        invalidTimeRangeInput = "1 " + PREFIX_NEXTLESSON + "15/6/2025 2100-2260";
        assertParseFailure(parser, invalidTimeRangeInput, MESSAGE_INVALID_TIME);
        invalidTimeRangeInput = "1 " + PREFIX_NEXTLESSON + "15/6/2025 2400-2200";
        assertParseFailure(parser, invalidTimeRangeInput, MESSAGE_INVALID_TIME);
        invalidTimeRangeInput = "1 " + PREFIX_NEXTLESSON + "15/6/2025 2300-2400";
        assertParseFailure(parser, invalidTimeRangeInput, MESSAGE_INVALID_TIME);

        // Same start and end time -> invalid
        String invalidStartEqualEndTimeInput = "1 " + PREFIX_NEXTLESSON + "15/6/2025 0900-0900";
        assertParseFailure(parser, invalidStartEqualEndTimeInput, MESSAGE_INVALID_START_END_TIME);

        // Start time after end time -> invalid
        String invalidStartAfterEndTimeInput = "1 " + PREFIX_NEXTLESSON + "15/6/2025 1100-0900";
        assertParseFailure(parser, invalidStartAfterEndTimeInput, MESSAGE_INVALID_START_BEFORE_END);

        // Date >1 year in the future -> invalid
        String invalidFutureDateInput = "1 " + PREFIX_NEXTLESSON
                + CURRENT_DATE.plusYears(1).format(java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy")) + " "
                + CURRENT_TIME.plusHours(1).format(java.time.format.DateTimeFormatter.ofPattern("HHmm")) + "-"
                + CURRENT_TIME.plusHours(1).plusMinutes(1).format(java.time.format.DateTimeFormatter.ofPattern("HHmm"));
        assertParseFailure(parser, invalidFutureDateInput, MESSAGE_INVALID_YEAR);

        // Date in the past -> invalid
        String invalidPastDateInput = "1 " + PREFIX_NEXTLESSON + invalidLessonDateTime;
        assertParseFailure(parser, invalidPastDateInput, MESSAGE_INVALID_PAST_LESSON);

        // Time in the past -> invalid
        String invalidPastTimeInput = "1 " + PREFIX_NEXTLESSON
                + CURRENT_DATE.format(java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy")) + " "
                + CURRENT_TIME.minusMinutes(4).format(java.time.format.DateTimeFormatter.ofPattern("HHmm")) + "-"
                + CURRENT_TIME.plusMinutes(1).format(java.time.format.DateTimeFormatter.ofPattern("HHmm"));
        assertParseFailure(parser, invalidPastTimeInput, MESSAGE_INVALID_PAST_LESSON);

        // DateTimeException -> invalid
        String invalidDateTimeInput = "1 " + PREFIX_NEXTLESSON + "30/2/2025 1900-2100";
        assertParseFailure(parser, invalidDateTimeInput, MESSAGE_INVALID_DATE_TIME);
    }
}
