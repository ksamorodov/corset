package ru.saprcorset.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saprcorset.backend.dto.ConstructionsDTO;
import ru.saprcorset.backend.service.ConstuctionsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kernels")
public class ConstructionsController {

    @Autowired
    private ConstuctionsService constuctionsService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ConstructionsDTO constructionsDTO) {
        Optional<Integer> id = constuctionsService.save(constructionsDTO);
        if (id.isPresent()) {
            return ResponseEntity.ok(id);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getConstructions() {
        return ResponseEntity.ok(constuctionsService.getKernelsList());
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
