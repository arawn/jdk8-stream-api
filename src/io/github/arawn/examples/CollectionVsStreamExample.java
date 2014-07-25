package io.github.arawn.examples;

import io.github.arawn.model.Contact;
import io.github.arawn.model.ContactSource;
import io.github.arawn.model.Gender;

import java.util.List;

public class CollectionVsStreamExample {

    public static void main(String[] args) {
        List<Contact> contacts = ContactSource.findAll();

        int manCount = 0;
        int totalAge = 0;
        for(Contact contact : contacts) {
            if("Florida".equals(contact.getState())
                && Gender.Male == contact.getGender()) {
                manCount += 1;
                totalAge += contact.getAge();
            }
        }
        double averageAge = totalAge / manCount;


        contacts.stream()
                .forEach(contact -> System.out.println(contact.getName()));

        contacts.stream()
                .filter(contact -> "Florida".equals(contact.getState()))
                .filter(contact -> Gender.Male == contact.getGender())
                .mapToInt(contact -> contact.getAge())
                .average();
    }

}
