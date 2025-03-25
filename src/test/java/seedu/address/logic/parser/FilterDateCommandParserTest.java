package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FilterDateCommandParser.MESSAGE_INVALID_DATE;
import static seedu.address.logic.parser.FilterDateCommandParser.MESSAGE_INVALID_PAST_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterDateCommand;
import seedu.address.model.person.NextLessonEqualsDatePredicate;

public class FilterDateCommandParserTest {

    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private FilterDateCommandParser parser = new FilterDateCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterDateCommand.MESSAGE_USAGE));

        // string of spaces
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterDateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterDateCommand() {
        FilterDateCommand expectedFilterDateCommand =
                new FilterDateCommand(new NextLessonEqualsDatePredicate(
                        LocalDate.parse("20/4/2025", DateTimeFormatter.ofPattern("d/M/yyyy"))
                ));

        assertParseSuccess(parser, "20/4/2025", expectedFilterDateCommand);

        // trailing whitespaces
        assertParseSuccess(parser, " \n 20/4/2025  \t", expectedFilterDateCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterDateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_pastDate_throwsParseException() {
        String pastDate = CURRENT_DATE.minusDays(1).format(java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy"));
        assertParseFailure(parser, pastDate, MESSAGE_INVALID_PAST_DATE);
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String invalidDateFormat = "15-04-2025";
        assertParseFailure(parser, invalidDateFormat, MESSAGE_INVALID_DATE);

        String[] invalidDates = {
                "0/4/2025", // Invalid day (0)
                "15/14/2025", // Invalid month (14)
                "15/4/202", // Invalid year (202)
        };

        for (String invalidDate : invalidDates) {
            assertParseFailure(parser, invalidDate, MESSAGE_INVALID_DATE);
        }
    }

}
