package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_PHYSICS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterSubjectCommand;
import seedu.address.model.person.SubjectContainsKeywordsPredicate;

public class FilterSubjectCommandParserTest {

    private FilterSubjectCommandParser parser = new FilterSubjectCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterSubjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterSubjectCommand() {
        // no leading and trailing whitespaces
        String userInput = VALID_SUBJECT_MATH + " " + VALID_SUBJECT_PHYSICS;
        List<String> keywords = Arrays.asList(VALID_SUBJECT_MATH.toLowerCase(), VALID_SUBJECT_PHYSICS.toLowerCase());
        FilterSubjectCommand expectedFilterSubjectCommand =
                new FilterSubjectCommand(new SubjectContainsKeywordsPredicate(keywords), userInput);
        assertParseSuccess(parser, userInput, expectedFilterSubjectCommand);

        // multiple whitespaces between keywords
        String multipleSpacesInput = " \n " + VALID_SUBJECT_MATH + " \n \t " + VALID_SUBJECT_PHYSICS + " \t";
        assertParseSuccess(parser, multipleSpacesInput,
                new FilterSubjectCommand(new SubjectContainsKeywordsPredicate(keywords), multipleSpacesInput.trim()));
    }
}
