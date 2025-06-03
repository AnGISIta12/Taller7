package co.edu.javeriana.llaves.favoritos.interfaces.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/greetings")
public class HelloController {

    @GetMapping()
    public String giveGreetings() {
        return "Hello from Greetings Controller at " + LocalDate.now() +
               " with thread " + Thread.currentThread().getName();
    }
}
