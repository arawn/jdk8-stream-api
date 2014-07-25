package io.github.arawn.examples;

import io.github.arawn.model.Contact;
import io.github.arawn.model.ContactSource;

import java.util.List;
import java.util.Optional;

public class OptionalExample {

    public static void main(String[] args) {

        List<Contact> contacts = ContactSource.findAll();

        Optional<Contact> optional = contacts.stream()
                                             .filter(contact -> contact.equalToState("Florida"))
                                             .findFirst();

        if (optional.isPresent()) {
            optional.get().call();
        }


        contacts.stream()
                .filter(contact -> contact.equalToState("Florida"))
                .findFirst()
                .ifPresent(c -> c.call());

        contacts.stream()
                .filter(contact -> contact.equalToState("Florida"))
                .findFirst()
                .ifPresent(Contact::call);


        contacts.stream()
                .filter(contact -> contact.equalToState("Florida"))
                .findFirst()
                .orElseGet(() -> Contact.empty());

        contacts.stream()
                .filter(contact -> contact.equalToState("Florida"))
                .findFirst()
                .orElseGet(Contact::empty);
    }

}
