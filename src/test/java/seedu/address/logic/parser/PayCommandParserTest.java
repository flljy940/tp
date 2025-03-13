package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PayCommand;

public class PayCommandParserTest {

    private final PayCommandParser parser = new PayCommandParser();
    private final String invalidIndex = "-3";

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = String.valueOf(targetIndex.getOneBased());
        PayCommand expectedCommand = new PayCommand(INDEX_FIRST_PERSON);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE);

        assertParseFailure(parser, invalidIndex, expectedMessage);
    }

    @Test
    public void parse_missingIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PayCommand.COMMAND_WORD, expectedMessage);
        assertParseFailure(parser, PayCommand.COMMAND_WORD + " ", expectedMessage);
    }
}
