package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SubjectContainsKeywordsPredicate;

/**
 * Filters and lists all persons in address book whose subjects contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterSubjectCommand extends Command {

    public static final String COMMAND_WORD = "filter-subject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose subjects contain "
            + "any of the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " math physics";

    public static final String MESSAGE_SUCCESS = "Listed all persons taking: %1$s";
    public static final String MESSAGE_NO_MATCHES = "No persons found taking: %1$s\n"
            + "Available subjects: %2$s";

    private final SubjectContainsKeywordsPredicate predicate;
    private final String subjects;

    /**
     * Creates a FilterSubjectCommand to filter persons by the specified subjects.
     *
     * @param predicate Predicate to test if a person's subjects match the desired subjects
     * @param subjects The subjects string that was used to create the predicate
     */
    public FilterSubjectCommand(SubjectContainsKeywordsPredicate predicate, String subjects) {
        this.predicate = predicate;
        this.subjects = subjects;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> unfilteredList = model.getAddressBook().getPersonList();
        model.updateFilteredPersonList(predicate);

        // Get the current filtered list after applying the predicate
        List<Person> currentFilteredList = model.getFilteredPersonList();

        // If no matches found, show available subjects
        if (currentFilteredList.isEmpty()) {
            String availableSubjects = getAvailableSubjects(unfilteredList);
            return new CommandResult(
                    String.format(MESSAGE_NO_MATCHES, subjects, availableSubjects));
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, subjects));
    }

    /**
     * Returns a string of all unique subjects available in the given list of persons.
     */
    private String getAvailableSubjects(List<Person> personList) {
        return personList.stream()
                .flatMap(person -> person.getSubjects().stream())
                .map(subject -> subject.subjectName)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterSubjectCommand // instanceof handles nulls
                && predicate.equals(((FilterSubjectCommand) other).predicate)
                && subjects.equals(((FilterSubjectCommand) other).subjects)); // state check
    }
}
