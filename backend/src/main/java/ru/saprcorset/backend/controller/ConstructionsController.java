package ru.saprcorset.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saprcorset.backend.dto.CalculateResponseDTO;
import ru.saprcorset.backend.dto.ConstructionsDTO;
import ru.saprcorset.backend.service.ConstuctionsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/constructions")
public class ConstructionsController {

    @Autowired
    private ConstuctionsService constuctionsService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ConstructionsDTO constructionsDTO) {
        Optional<Integer> id = constuctionsService.save(constructionsDTO);
        if (id.isPresent()) {
            return ResponseEntity.ok(id);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getConstructions() {
        return ResponseEntity.ok(constuctionsService.getKernelsList());
    }

    @GetMapping("/calculate/{id}")
    public ResponseEntity<?> calculate(@PathVariable Integer id) {
        CalculateResponseDTO calculate = constuctionsService.calculate(id);
        return ResponseEntity.ok(calculate);
    }

    @PostMapping("/vlad")
    public ResponseEntity<?> name(@RequestBody String str) {
        return ResponseEntity.ok(str + "Hello from backend!!!");
    }

    @PostMapping("/passport")
    public ResponseEntity<?> passport(@RequestBody Passport passport) {
        return ResponseEntity.ok(passport);
    }

    @PostMapping("/car")
    public ResponseEntity<?> passport(@RequestBody @Valid Car car) {
        return ResponseEntity.ok(car);
    }

    @PostMapping("/smartphone/{email}")
    public ResponseEntity<?> Smartphone(@PathVariable String email, @RequestBody @Valid Smartphone smartphone) {
        Person person = new Person(email, null, null, smartphone);
        return ResponseEntity.ok(person);
    }

    @PostMapping("/person/")
    public ResponseEntity<?> Smartphone(@RequestBody @Valid Person person) {
        return ResponseEntity.ok(person);
    }

    @PutMapping
    public ResponseEntity<?> deleteByName(@RequestBody String name) {
        constuctionsService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<ConstructionsDTO> byId = constuctionsService.getById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public static record Passport(String name, String series, Integer number, Boolean wanted) {}

    public static record Car(@NotNull Double speed, @NotNull String model) {}

    public static record Smartphone(@NotNull String model, @NotNull Double screenSize, @NotNull List<String> additionalAttrubutes) {}

    public static record Person (@NotNull String fio, @NotNull Passport passport, @NotNull Car car, @NotNull Smartphone smartphone) {}
}
