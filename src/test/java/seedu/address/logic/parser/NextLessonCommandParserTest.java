package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTLESSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NextLessonCommand;
import seedu.address.model.person.NextLesson;

public class NextLessonCommandParserTest {

    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private final NextLessonCommandParser parser = new NextLessonCommandParser();
    private final String nonEmptyDate = "15/4/2025 1900-2100";

    @Test
    public void parse_indexSpecified_success() {
        // have date of next lesson
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NEXTLESSON + nonEmptyDate;
        NextLessonCommand expectedCommand = new NextLessonCommand(INDEX_FIRST_PERSON, new NextLesson(nonEmptyDate));
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
        assertParseFailure(parser, NextLessonCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }

    @Test
    void constructor_invalidDateFormat_throwsParseException() {
        String[] invalidInputs = {
            "15-4-2025 0900-1100", // Wrong separator in date
            "15/04/2025 9:00-11:00", // Wrong time format
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
            "15/4/2025 1200-1200", // Start time == End time
            "15/4/2025 1500-1400" // Start time > End time
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
    public void parse_invalidInput_throwsParseException() {
        // Invalid index
        String invalidIndexInput = "a " + PREFIX_NEXTLESSON + "15/4/2025 1900-2100";
        assertParseFailure(parser, invalidIndexInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE));

        // Invalid date format
        String invalidDateFormatInput = "1 " + PREFIX_NEXTLESSON + "15-4-2025 1900-2100";
        assertParseFailure(parser, invalidDateFormatInput,
                String.format(NextLessonCommandParser.MESSAGE_CONSTRAINTS));

        // Invalid time format
        String invalidTimeFormatInput = "1 " + PREFIX_NEXTLESSON + "15/4/2025 19:00-21:00";
        assertParseFailure(parser, invalidTimeFormatInput,
                String.format(NextLessonCommandParser.MESSAGE_CONSTRAINTS));
    }
}
