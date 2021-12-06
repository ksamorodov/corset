package ru.saprcorset.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saprcorset.backend.dto.CalculateResponseDTO;
import ru.saprcorset.backend.dto.ConstructionsDTO;
import ru.saprcorset.backend.service.ConstuctionsService;

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
        return ResponseEntity.ok(new CalculateResponseDTO(0, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST));
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
}
