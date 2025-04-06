package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextLesson;
import seedu.address.model.person.PayStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.subject.Subject;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final NextLesson DEFAULT_NEXTLESSON = new NextLesson();
    public static final NextLesson SAMPLE_NEXTLESSON_1 = new NextLesson("15/06/2025 1900-2100");
    public static final NextLesson SAMPLE_NEXTLESSON_2 = new NextLesson("22/06/2025 1900-2100");
    public static final PayStatus DEFAULT_PAYSTATUS = new PayStatus(PayStatus.NOT_PAID);
    public static final PayStatus NOT_DEFAULT_PAYSTATUS = new PayStatus(PayStatus.PAID);

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), SAMPLE_NEXTLESSON_1, NOT_DEFAULT_PAYSTATUS,
                getSubjectSet("math")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), SAMPLE_NEXTLESSON_2, NOT_DEFAULT_PAYSTATUS,
                getSubjectSet("math", "chemistry")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), DEFAULT_NEXTLESSON, DEFAULT_PAYSTATUS,
                getSubjectSet("physics")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), DEFAULT_NEXTLESSON, DEFAULT_PAYSTATUS,
                getSubjectSet("math")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), DEFAULT_NEXTLESSON, DEFAULT_PAYSTATUS,
                getSubjectSet("math")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), DEFAULT_NEXTLESSON, DEFAULT_PAYSTATUS,
                getSubjectSet("biology"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Subject> getSubjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Subject::new)
                .collect(Collectors.toSet());
    }

}
