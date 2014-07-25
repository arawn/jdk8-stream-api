package io.github.arawn.examples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author arawn.kr@gmail.com
 */
public class StreamsCanBeCreatedFrom {

    public static void main(String[] args) throws Exception {
        StreamsCanBeCreatedFrom streamsCanBeCreatedFrom = new StreamsCanBeCreatedFrom();
        streamsCanBeCreatedFrom.collection();
        streamsCanBeCreatedFrom.arrays();
        streamsCanBeCreatedFrom.ranges();
        streamsCanBeCreatedFrom.values();
        streamsCanBeCreatedFrom.generators();
        streamsCanBeCreatedFrom.resources();
    }

    private void collection() {
        List<String> collection = new ArrayList<>();
        collection.add("김지영");
        collection.add("박성철");
        collection.add("박용권");
        collection.add("정대원");

        Stream<String> stream = collection.stream();

        Stream<String> parallelStream = collection.parallelStream();
    }

    private void arrays() {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

        // array to stream
        IntStream stream = Arrays.stream(numbers);

        // can convert into parallelStream
        IntStream parallelStream = stream.parallel();
    }

    private void ranges() {
        // 1 to 9
        IntStream intStream = IntStream.range(1, 10);

        // 1 to 10
        LongStream longStream = LongStream.rangeClosed(1, 10);
    }

    private void values() {
        Stream<String> stream = Stream.of("Using", "Stream", "API", "From", "Java8");

        // can convert into parallelStream
        Stream<String> parallelStream = stream.parallel();
    }

    private void generators() {
        // 난수 스트림
        Stream<Double> random = Stream.generate(Math::random);

        // 0 1 2 3 ... 무한 수열
        Stream<Integer> numbers = Stream.iterate(0, n -> n + 1);

        // 피보나치 수열
        Stream<int[]> fibonacci = Stream.iterate(new int[]{0,1}, n -> new int[]{n[1], n[0]+n[1]});
        fibonacci.limit(10)
                 .map(n -> n[0]).forEach(System.out::println);
    }

    private void resources() throws IOException {
        // 주소록에서 플로리다 주에 사는 사람은 몇명?
        try(Stream<String> lines = Files.lines(Paths.get("address-books-1000.csv"))) {
            long count = lines.map(line -> line.split(","))
                              .filter(values -> values[6].contains("FL"))
                              .count();
            System.out.println("count = " + count);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
