package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTLESSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NextLessonCommand;
import seedu.address.model.person.NextLesson;

public class NextLessonCommandParserTest {
    private NextLessonCommandParser parser = new NextLessonCommandParser();
    private final String nonEmptyDate = "Today.";

    @Test
    public void parse_indexSpecified_success() {
        // have date of next lesson
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NEXTLESSON + nonEmptyDate;
        NextLessonCommand expectedCommand = new NextLessonCommand(INDEX_FIRST_PERSON, new NextLesson(nonEmptyDate));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no date of next lesson
        userInput = targetIndex.getOneBased() + " " + PREFIX_NEXTLESSON;
        expectedCommand = new NextLessonCommand(INDEX_FIRST_PERSON, new NextLesson(""));
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
}
