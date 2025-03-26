package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnpayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnpayCommand object
 */
public class UnpayCommandParser implements Parser<UnpayCommand> {
    @Override
    public UnpayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.equalsIgnoreCase("all")) {
            return new UnpayCommand(true);
        }

        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new UnpayCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpayCommand.MESSAGE_USAGE), ive);
        }
    }
}
