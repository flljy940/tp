package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class SubjectContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SubjectContainsKeywordsPredicate firstPredicate = new SubjectContainsKeywordsPredicate(firstPredicateKeywordList);
        SubjectContainsKeywordsPredicate secondPredicate = new SubjectContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SubjectContainsKeywordsPredicate firstPredicateCopy = new SubjectContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_subjectsContainKeywords_returnsTrue() {
        // One keyword
        SubjectContainsKeywordsPredicate predicate = new SubjectContainsKeywordsPredicate(Collections.singletonList("math"));
        assertTrue(predicate.test(new PersonBuilder().withSubjects("math").build()));

        // Multiple keywords
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("math", "physics"));
        assertTrue(predicate.test(new PersonBuilder().withSubjects("math", "chemistry").build()));

        // Only one matching keyword
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("math", "physics"));
        assertTrue(predicate.test(new PersonBuilder().withSubjects("math").build()));

        // Mixed-case keywords
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("MaTh", "PhYsIcS"));
        assertTrue(predicate.test(new PersonBuilder().withSubjects("math", "physics").build()));
    }

    @Test
    public void test_subjectsDoNotContainKeywords_returnsFalse() {
        // Zero keywords
        SubjectContainsKeywordsPredicate predicate = new SubjectContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSubjects("math").build()));

        // Non-matching keyword
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("chemistry"));
        assertFalse(predicate.test(new PersonBuilder().withSubjects("math", "physics").build()));

        // Keywords match name but not subject
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withSubjects("math").build()));
    }
} 