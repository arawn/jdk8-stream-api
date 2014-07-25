package io.github.arawn.examples;

import io.github.arawn.model.Contact;
import io.github.arawn.model.ContactSource;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author arawn.kr@gmail.com
 */
public class StreamApiExamples {

    public static void main(String[] args) {
        StreamApiExamples examples = new StreamApiExamples();

        System.out.println("print_1to10");
        examples.print_1to10();

        System.out.println("print_1to10_even");
        examples.print_1to10_even();

        System.out.println("print_contact_in_NewYork");
        examples.print_contact_in_NewYork();

        System.out.println("print_contact_email");
        examples.print_contact_email();

        System.out.println("print_1to100_sum");
        examples.print_1to100_sum();

        examples.summaryStatistics();
    }

    public void print_1to10() {
        // for 구문
        for(int idx = 1; idx <= 10; idx++) {
            if(idx % 2 == 0) {
                System.out.println(idx);
            }
        }

        // Stream API + Method References
        IntStream.rangeClosed(1, 10)
                 .filter(n -> n % 2 == 0)
                 .forEach(System.out::println);
    }

    public void print_1to10_even() {
        // for 구문
        for(int idx = 1; idx <= 10; idx++) {
            if(idx % 2 == 0) {
                System.out.println(idx);
            }
        }

        // Stream API + Method References
        IntStream.rangeClosed(1, 10)
                 .filter(n -> n % 2 == 0)
                 .forEach(System.out::println);
    }

    public void print_contact_in_NewYork() {
        List<Contact> contacts = ContactSource.findAll();

        // for 구문
        for(Contact contact : contacts) {
            if(contact.equalToState("New York")) {
                System.out.print(contact);
            }
        }

        // Stream API(filter, forEach) + Lambda Expressions
        contacts.stream()
                .filter(contact -> contact.equalToState("New York"))
                .forEach(contact -> System.out.println(contact));

        // Stream API(filter, forEach) + Method References
        contacts.stream()
                .filter(contact -> contact.equalToState("New York"))
                .forEach(System.out::println);
    }

    public void print_contact_email() {
        List<Contact> contacts = ContactSource.findAll();

        // for 구문
        for(Contact contact : contacts) {
            System.out.println(contact.getEmail());
        }

        // Stream API + Lambda Expressions
        contacts.stream()
                .forEach(contact -> System.out.println(contact.getEmail()));

        // Stream API + Lambda Expressions
        contacts.stream()
                .map(contact -> contact.getEmail())
                .forEach(email -> System.out.println(email));

        // Stream API + Method References
        contacts.stream()
                .map(Contact::getEmail)
                .forEach(System.out::println);
    }

    public void print_1to100_sum() {
        // for 구문
        int sum = 0;
        for(int number = 1; number <= 100; number++) {
            sum += number;
        }

        // Stream API + Lambda Expressions
        int sum1 = IntStream.rangeClosed(1, 100)
                            .reduce(0, (left, right) -> left + right);

        // Stream API + Method References
        int sum2 = IntStream.rangeClosed(1, 100)
                            .reduce(0, Integer::sum);

        // Stream API + Primitive Type Stream Methods
        int sum3 = IntStream.rangeClosed(1, 100)
                            .sum();
    }

    public void summaryStatistics() {
        List<Contact> contacts = ContactSource.findAll();

        long sum = 0;
        int min = 0, max = 0;
        for(Contact contact : contacts) {
            int age = contact.getAge();
            sum += age;
            min = Math.min(min, age);
            max = Math.max(max, age);
        }
        double average = sum / contacts.size();


        int sum1 = contacts.stream().mapToInt(Contact::getAge).sum();
        OptionalDouble average1 = contacts.stream().mapToInt(Contact::getAge).average();
        OptionalInt min1 = contacts.stream().mapToInt(Contact::getAge).min();
        OptionalInt max1 = contacts.stream().mapToInt(Contact::getAge).max();

        IntSummaryStatistics summaryStatistics = contacts.stream().mapToInt(Contact::getAge).summaryStatistics();
        long sum2 = summaryStatistics.getSum();
        double average2 = summaryStatistics.getAverage();
        int min2 = summaryStatistics.getMin();
        int max2 = summaryStatistics.getMax();
        long count = summaryStatistics.getCount();
    }

    public void collect_city() {
        List<Contact> contacts = ContactSource.findAll();

        // for 구문
        List<String> cities = new ArrayList<>();
        for(Contact contact : contacts) {
            String city = contact.getCity();
            if(!cities.contains(city)) {
                cities.add(contact.getCity());
            }
        }

        Iterator<Contact> contactIterator = contacts.iterator();

        Object[] objectArray = contacts.stream().toArray();
        Contact[] contactArray = contacts.stream().toArray(Contact[]::new);

        cities = contacts.stream()
                .map(contact -> contact.getCity())
                .distinct()
                .collect(() -> new ArrayList<>()
                        , (list, city) -> list.add(city)
                        , (left, right) -> left.addAll(right));

        cities = contacts.stream()
                .map(contact -> contact.getCity())
                .distinct()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        cities = contacts.stream()
                .map(contact -> contact.getCity())
                .distinct()
                .collect(Collectors.toList());


        // Collection 타입으로 요소를 모을 때
        contacts.stream().collect(Collectors.toList());
        contacts.stream().collect(Collectors.toSet());
        contacts.stream().collect(Collectors.toCollection(TreeSet::new));

        // Map 타입으로 요소를 모을 때
        contacts.stream().collect(Collectors.toMap(Contact::getName, Contact::getBirthday));
        contacts.stream().collect(Collectors.toMap(Contact::getName, Function.identity()));

        // 스트림에 있는 모든 문자열을 서로 연결해서 모을 때
        contacts.stream().map(Contact::getName).collect(Collectors.joining());
        contacts.stream().map(Object::toString).collect(Collectors.joining("|"));
    }

}
