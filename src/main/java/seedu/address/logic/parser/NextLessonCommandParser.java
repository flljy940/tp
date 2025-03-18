package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTLESSON;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NextLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NextLesson;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses input arguments and creates a new NextLessonCommand object
 */
public class NextLessonCommandParser implements Parser<NextLessonCommand> {

    private static final String DATE_TIME_FORMAT = "d MMM H-H";
    private static final String DATE_REGEX = "(\\d{1,2})\\s([A-Za-z]{3})\\s(\\d{1,2})-(\\d{1,2})";  // For matching date like 16 Mar 9-12

    /**
     * Parses the given {@code String} of arguments in the ocntext of the {@code NextLessonCommand}
     * and returns a {@code NextLessonCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NextLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NEXTLESSON);

        Index index;
        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextLessonCommand.MESSAGE_USAGE), ive);
        }

        String date = argumentMultimap.getValue(PREFIX_NEXTLESSON).orElse("");

        if (date.isEmpty()) {
            return new NextLessonCommand(index, new NextLesson(""));
        }

        Pattern pattern = Pattern.compile(DATE_REGEX);
        Matcher matcher = pattern.matcher(date);

        if (matcher.matches()) {
            String day = matcher.group(1);
            String month = matcher.group(2);
            String startTime = matcher.group(3);
            String endTime = matcher.group(4);

            int monthValue = getMonthValue(month);
            if (monthValue == -1) {
                throw new ParseException("Invalid month abbreviation: " + month);
            }

            try {
                int dayInt = Integer.parseInt(day);

                if (monthValue == -1) {
                    throw new ParseException("Invalid month abbreviation: " + month);
                }

                System.out.print("Parsed day: " + dayInt + ", Parsed month: " + monthValue);

                // Validate day & month by constructing a temporary LocalDate (with dummy year 2000)
                LocalDate.of(2025, monthValue, dayInt);

                int startHour = Integer.parseInt(startTime);
                int endHour = Integer.parseInt(endTime);

                System.out.println("Parsed start time: " + startHour + ", Parsed end time: " + endHour);

                LocalTime start = LocalTime.of(startHour, 0);
                LocalTime end = LocalTime.of(endHour, 0);

                // Store as "d MMM H-H" without year
                String formattedDate = day + " " + month + " " + startTime + "-" + endTime;
                return new NextLessonCommand(index, new NextLesson(formattedDate));
            } catch (DateTimeException e) {
                throw new ParseException("Invalid date or time values: " + date, e);
            }

        } else {
            throw new ParseException("Invalid date format. Expected format: 'd MMM H-H' (e.g., 16 Mar 9-11)");
        }

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM H-H");
//        try {
//            String[] parts = date.split(" ");
//            if (parts.length != 3) {
//                throw new ParseException("Invalid date format. Expected 'd MMM H-H', but got: " + date);
//            }
//
//            String dayAndMonth = parts[0] + " " + parts[1];
//            String timeRange = parts[2];
//
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM");
//            LocalDateTime lessonDate = LocalDateTime.parse(dayAndMonth, dateFormatter);
////            LocalDateTime nextLesson = LocalDateTime.parse(date, formatter);
//
//            String[] timeParts = timeRange.split("-");
//            if (timeParts.length != 2) {
//                throw new ParseException("Invalid time format. Expected 'H-H', but got: " + timeRange);
//            }
//            LocalTime startTime = LocalTime.parse(timeParts[0] + ":00");
//            LocalTime endTime = LocalTime.parse(timeParts[1] + ":00");
//
////            String formattedDate = nextLesson.toString();
//            String formattedDate = lessonDate.format(DateTimeFormatter.ofPattern("d MMM")) +
//                    " " + startTime + "-" + endTime;
//            return new NextLessonCommand(index, new NextLesson(formattedDate));
//        } catch (DateTimeParseException e) {
//            throw new ParseException("Invalid date format. Please use the format '16 Mar 9-12'");
//        }
    }

    private int getMonthValue(String month) {
        switch (month.toLowerCase()) {
            case "jan": return 1;
            case "feb": return 2;
            case "mar": return 3;
            case "apr": return 4;
            case "may": return 5;
            case "jun": return 6;
            case "jul": return 7;
            case "aug": return 8;
            case "sep": return 9;
            case "oct": return 10;
            case "nov": return 11;
            case "dec": return 12;
            default: return -1;
        }
    }
}
