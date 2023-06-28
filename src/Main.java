import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) {
        Main myClass = new Main();

        List<String> names = new ArrayList<>();
        names.add("John");
        names.add("Mary");
        names.add("Adam");
        names.add("Emily");
        names.add("Tony");
        names.add("Poul");

        String[] array = {"1, 2, 0", "4, 5"};

        long a = 25214903917L;
        long c = 11L;
        long m = (1L << 48);

        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> stream2 = Stream.of(6, 7, 8, 9, 10, 11, 12);

        System.out.println(myClass.firstTask(names));

        System.out.println(myClass.secondTask(names));

        System.out.println(myClass.thirdTask(array));

        Stream<Long> randomStream = forthTask(0L, a, c, m);
        randomStream.limit(10).forEach(System.out::println);


        Stream<Integer> zippedStream = zip(stream1, stream2);
        zippedStream.forEach(System.out::println);
    }

    public Map<Integer, String> firstTask(List<String> names) {
        return IntStream.range(0, names.size())
                .filter(i -> i % 2 != 0)
                .boxed()
                .collect(Collectors.toMap(i -> i, names::get));
    }

    public List<String> secondTask(List<String> names) {
        return names.stream()
                .map(String::toUpperCase)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    public String thirdTask(String[] array) {
        List<Integer> numbers = Arrays.stream(array)
                .flatMap(s -> Arrays.stream(s.split(", ")))
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());

        return numbers.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    public static Stream<Long> forthTask(long seed, long a, long c, long m) {
        return Stream.iterate(seed, n -> (a * n + c) % m);
    }

    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second){
        Iterator<T> iterator1 = first.iterator();
        Iterator<T> iterator2 = second.iterator();

        Iterator<T> zippedIterator = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return iterator1.hasNext() && iterator2.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements in the streams.");
                }
                return iterator1.next();
            }
        };

        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(zippedIterator, Spliterator.ORDERED);

        return StreamSupport.stream(spliterator, false);
    }
}