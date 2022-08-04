package com.edu.flux.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Stream;

public class ReactorCore {

    public static void main(String[] args) {

        /**
         * Mono is 0 or 1
         */
        Mono.empty().subscribe(System.out::println);
        Mono.just("hello").subscribe(System.out::println);

        /**
         * Flux is 0-n
         */
        Flux.just(1,2,3,4,5).subscribe(System.out::println);
        Flux.fromIterable(Arrays.asList("a","b","c","d","e")).subscribe(System.out::println);
        Flux.fromArray(new String[]{"hello","reactor"}).subscribe(System.out::println);
        Flux.range(100,120).subscribe(System.out::print);
        Flux.fromStream(Stream.of(1,3,5,7,9)).subscribe(System.out::println);

        Flux.generate(()->0,(i,sink)->{
            sink.next("2*" + i +"="+ 2*i);
            if (i==9) sink.complete();
            return i+1;
        }).subscribe(System.out::println);
    }
}
