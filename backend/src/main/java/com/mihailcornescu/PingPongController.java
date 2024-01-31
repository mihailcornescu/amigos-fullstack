package com.mihailcornescu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

    record PingPong(String result) {}

    @GetMapping("/ping-pong")
    public PingPong pingPong() {
        return new PingPong("ping-pong");
    }
}
