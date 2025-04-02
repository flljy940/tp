package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterPaidStatusCommand;
import seedu.address.model.person.PayStatus;
import seedu.address.model.person.PayStatusEqualsPaidPredicate;

public class FilterPaidStatusCommandParserTest {

    private FilterPaidStatusCommandParser parser = new FilterPaidStatusCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterPaidStatusCommand() {
        // Basic command
        FilterPaidStatusCommand expectedCommand =
                new FilterPaidStatusCommand(new PayStatusEqualsPaidPredicate(true), PayStatus.PAID);
        assertParseSuccess(parser, "paid", expectedCommand);

        // Case insensitive
        assertParseSuccess(parser, "PAID", expectedCommand);
        assertParseSuccess(parser, "PaId", expectedCommand);
    }

    @Test
    public void parse_validUnpaidArgs_returnsFilterPaidStatusCommand() {
        // Basic command
        FilterPaidStatusCommand expectedCommand =
                new FilterPaidStatusCommand(new PayStatusEqualsPaidPredicate(false), PayStatus.NOT_PAID);
        assertParseSuccess(parser, "unpaid", expectedCommand);

        // Case insensitive
        assertParseSuccess(parser, "UNPAID", expectedCommand);
        assertParseSuccess(parser, "UnPaId", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid status
        assertParseFailure(parser, "pending",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidStatusCommand.MESSAGE_USAGE));

        // Multiple statuses
        assertParseFailure(parser, "paid unpaid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidStatusCommand.MESSAGE_USAGE));
    }
}
