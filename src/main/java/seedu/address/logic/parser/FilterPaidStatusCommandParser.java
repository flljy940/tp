package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterPaidStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PayStatus;
import seedu.address.model.person.PayStatusEqualsPaidPredicate;

/**
 * Parses input arguments and creates a new FilterPaidStatusCommand object
 */
public class FilterPaidStatusCommandParser implements Parser<FilterPaidStatusCommand> {

    /**
     * Represents the result of validating a payment status string.
     */
    private static class PayStatusResult {
        final boolean isPaid;
        final String payStatus;

        PayStatusResult(boolean isPaid, String payStatus) {
            this.isPaid = isPaid;
            this.payStatus = payStatus;
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FilterPaidStatusCommand
     * and returns a FilterPaidStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterPaidStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidStatusCommand.MESSAGE_USAGE));
        }

        String[] statusStrings = trimmedArgs.split("\\s+");
        if (statusStrings.length > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidStatusCommand.MESSAGE_USAGE));
        }

        PayStatusResult result = validateAndGetPayStatus(statusStrings[0]);

        return new FilterPaidStatusCommand(
                new PayStatusEqualsPaidPredicate(result.isPaid),
                result.payStatus);
    }

    /**
     * Validates the given status string and returns the corresponding PayStatusResult.
     * @throws ParseException if the status string is invalid
     */
    private PayStatusResult validateAndGetPayStatus(String status) throws ParseException {
        if (status.equalsIgnoreCase("paid")) {
            return new PayStatusResult(true, PayStatus.PAID);
        } else if (status.equalsIgnoreCase("unpaid")) {
            return new PayStatusResult(false, PayStatus.NOT_PAID);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidStatusCommand.MESSAGE_USAGE));
        }
    }
}
