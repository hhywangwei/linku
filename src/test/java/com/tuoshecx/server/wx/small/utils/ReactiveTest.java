package com.tuoshecx.server.wx.small.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ReactiveTest {

    @Test
    public void testFlux(){
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.incrementAndGet();
                    sink.next("3 x" + i + " = " + 3 * i);
                    if(i == 10) {
                        sink.complete();
                    }
                    return state;
                },
                (state) -> System.out.print("state:" +state));
        flux.subscribe(System.out::println);
    }

    @Test
    public void testAlphabet(){
        Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
                .handle((i, sink) -> {
                    String letter = alphabet(i);
                    if(letter != null){
                        sink.next(letter);
                    }
                });
        alphabet.subscribe(System.out::println);
    }

    private String alphabet(int letterNumber){
        if(letterNumber < 1 || letterNumber > 26){
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber -1;
        return "" + (char)letterIndexAscii;
    }

    @Test
    public void testToJson()throws IOException {
        Map<String, String> map = new HashMap<>();

        map.put("ext","{extAppid: \"33243232324\"}");

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(map));
    }
}
