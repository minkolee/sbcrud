package cc.conyli.sbcrud.react;

import cc.conyli.sbcrud.entity.Employee;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ReactorTest {

    public static void main(String[] args) {

    }
    @Test
    public void createFlux_just() {
        String[] fruits = new String[]{"Apple", "Orange", "Grape", "Banana", "Strawberry"};
        Flux<String> fruitFlux = Flux.fromArray(fruits);
        fruitFlux.subscribe(s -> System.out.println(s));

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();

    }

    @Test
    public void createFluxFromIterable() {
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Grape");
        fruits.add("Banana");
        fruits.add("Strawberry");
        Flux<String> fruitFlux = Flux.fromIterable(fruits);
        fruitFlux.subscribe(s -> System.out.println(s));

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();

    }

    @Test
    public void createFluxFromRange() {
        Flux<Integer> intervalCount = Flux.range(1, 5);
        intervalCount.subscribe(s -> System.out.println(s));

        StepVerifier.create(intervalCount)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();

    }

    @Test
    public void createFluxOfInterval() {
        Flux<Long> intervalCount = Flux.interval(Duration.ofSeconds(1)).take(5);
        intervalCount.subscribe(s -> System.out.println(s));

        StepVerifier.create(intervalCount)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }

    @Test
    public void mergeFluxes() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl").delayElements(Duration.ofMillis(500));

        Flux<String> zi = Flux.just("master", "queen", "slave", "guardian").delaySubscription(Duration.ofMillis(250)).delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = names.mergeWith(zi);

        mergedFlux.subscribe(s -> System.out.println(s));

        StepVerifier.create(mergedFlux)
                .expectNext("jenny")
                .expectNext("master")
                .expectNext("cony")
                .expectNext("queen")
                .expectNext("minko")
                .expectNext("slave")
                .expectNext("owl")
                .expectNext("guardian")
                .verifyComplete();
    }

    @Test
    public void mergeFluxesToTuple() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl");

        Flux<String> zi = Flux.just("master", "queen", "slave", "guardian");

        Flux<Tuple2<String, String>> mergedFlux = names.zipWith(zi);

        mergedFlux.subscribe(s -> System.out.println(s));

        StepVerifier.create(mergedFlux)
                .expectNextMatches(p -> p.getT1().equals("jenny") && p.getT2().equals("master"))
                .expectNextMatches(p -> p.getT1().equals("cony") && p.getT2().equals("queen"))
                .expectNextMatches(p -> p.getT1().equals("minko") && p.getT2().equals("slave"))
                .expectNextMatches(p -> p.getT1().equals("owl") && p.getT2().equals("guardian"))
                .verifyComplete();
    }

    @Test
    public void mergeFluxesProcessed() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl");

        Flux<String> zi = Flux.just("master", "queen", "slave", "guardian");

        Flux<String> mergedFlux = Flux.zip(names, zi, (a, b) -> a + "|" + b);


        mergedFlux.subscribe(s -> System.out.println(s));

        StepVerifier.create(mergedFlux)
                .expectNext("jenny|master")
                .expectNext("cony|queen")
                .expectNext("minko|slave")
                .expectNext("owl|guardian")
                .verifyComplete();
    }

    @Test
    public void mergeFluxesFirst() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl").delaySubscription(Duration.ofMillis(100));

        Flux<String> zi = Flux.just("master", "queen", "slave", "guardian");

        Flux<String> mergedFlux = Flux.first(names, zi);


        mergedFlux.subscribe(s -> System.out.println(s));

        StepVerifier.create(mergedFlux)
                .expectNext("master")
                .expectNext("queen")
                .expectNext("slave")
                .expectNext("guardian")
                .verifyComplete();
    }

    @Test
    public void skipFlux() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl").skip(2);

        names.subscribe(s -> System.out.println(s));

        StepVerifier.create(names)
                .expectNext("minko")
                .expectNext("owl")
                .verifyComplete();
    }

    @Test
    public void skipTimeFlux() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl").delayElements(Duration.ofMillis(99)).skip(Duration.ofMillis(400));

        names.subscribe(s -> System.out.println(s));

        StepVerifier.create(names)
                .expectNext("minko")
                .expectNext("owl")
                .verifyComplete();
    }

    @Test
    public void takeFlux() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl").take(3);

        names.subscribe(s -> System.out.println(s));

        StepVerifier.create(names)
                .expectNext("jenny")
                .expectNext("cony")
                .expectNext("minko")
                .verifyComplete();
    }

    @Test
    public void filterFlux() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl", "gege", "dazhuan").filter(s -> s.length() == 4);

        names.subscribe(s -> System.out.println(s));

        StepVerifier.create(names)
                .expectNext("jenny")
                .expectNext("minko")
                .verifyComplete();
    }

    @Test
    public void distinctFlux() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl", "gege", "dazhuan","owl","minko").distinct();

        names.subscribe(s -> System.out.println(s));

        StepVerifier.create(names)
                .expectNext("jenny")
                .expectNext("cony")
                .expectNext("minko")
                .expectNext("owl")
                .expectNext("gege")
                .expectNext("dazhuan")
                .verifyComplete();
    }

    @Test
    public void mappedFlux() {
        Flux<Employee> names = Flux.just("jenny", "cony", "minko", "owl")
                .map(s -> new Employee(s, "", ""));

        names.subscribe(s -> System.out.println(s));

        StepVerifier.create(names)
                .expectNext(new Employee("jenny","",""))
                .expectNext(new Employee("cony","",""))
                .expectNext(new Employee("minko","",""))
                .expectNext(new Employee("owl","",""))
                .verifyComplete();
    }


    @Test
    public void flatmappedFlux() {
        Flux<Employee> names = Flux.just("jenny", "cony", "minko", "owl", "gege")
                .flatMap(s -> Mono.just(s).map(n -> new Employee(n, "", ""))).subscribeOn(Schedulers.parallel());


    }

    @Test
    public void bufferedFlux() {
        Flux<String> names = Flux.just("jenny", "cony", "minko", "owl", "gege");

        Flux<List<String>> buffered = names.buffer(2);

        StepVerifier.create(buffered)
                .expectNext(Arrays.asList("jenny", "cony"))
                .expectNext(Arrays.asList("minko", "owl"))
                .expectNext(Arrays.asList("gege"))
                .verifyComplete();
    }

    @Test
    public void bufferedFlatMapFlux() {
        Flux.just("jenny", "cony", "minko", "owl", "gege")
                .buffer(3).
                flatMap(x -> Flux.fromIterable(x)
                        .map(y -> y.toUpperCase())
                        .subscribeOn(Schedulers.parallel())
                        .log()
                ).subscribe(s->System.out.println(s));

    }















}
