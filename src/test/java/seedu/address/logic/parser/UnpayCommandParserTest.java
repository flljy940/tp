package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnpayCommand;

public class UnpayCommandParserTest {

    private final UnpayCommandParser parser = new UnpayCommandParser();

    @Test
    public void parse_validIndex_success() {
        String userInput = String.valueOf(INDEX_FIRST_PERSON.getOneBased());
        UnpayCommand expectedCommand = new UnpayCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allKeyword_success() {
        assertParseSuccess(parser, "all", new UnpayCommand(true));
        assertParseSuccess(parser, "ALL", new UnpayCommand(true)); // case-insensitive test
        assertParseSuccess(parser, "  all  ", new UnpayCommand(true)); // with surrounding spaces
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpayCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "abc", expectedMessage);
        assertParseFailure(parser, "-2", expectedMessage);
        assertParseFailure(parser, "1 abc", expectedMessage);
        assertParseFailure(parser, "all extra", expectedMessage);
    }

    @Test
    public void parse_missingArguments_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpayCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
    }
}
