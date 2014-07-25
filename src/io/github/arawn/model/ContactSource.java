package io.github.arawn.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author arawn.kr@gmail.com
 */
public class ContactSource {

    public static List<Contact> findAll() {
        return getStreamFromSmallData().collect(Collectors.toList());
    }

    public static Stream<Contact> getStreamFromSmallData() {
        return getStream("address-books-1000.csv");
    }

    public static Stream<Contact> getStreamFromLargeData() {
        return getStream("address-books-100000.csv");
    }

    private static Stream<Contact> getStream(String path) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // cvs field order: first_name,last_name,gender,birthday,email,address,city,state,zip,country
            return Files.lines(Paths.get(path))
                    .parallel()
                    .map(line -> line.split(","))
                    .map(values -> {
                        Contact contact = new Contact();
                        contact.setFirstName(values[0]);
                        contact.setLastName(values[1]);
                        contact.setGender(Gender.valueOf(values[2]));
                        contact.setBirthday(LocalDate.parse(values[3], dateTimeFormatter));
                        contact.setEmail(values[4]);
                        contact.setCountry(values[9]);
                        contact.setStreetAddress(values[5]);
                        contact.setCity(values[6]);
                        contact.setState(values[7]);
                        contact.setZip(values[8]);

                        return contact;
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        getStreamFromSmallData().forEach(System.out::println);
    }

}
