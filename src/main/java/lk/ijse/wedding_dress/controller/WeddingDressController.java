package lk.ijse.wedding_dress.controller;

import jakarta.validation.Valid;
import lk.ijse.wedding_dress.entity.WeddingDress;
import lk.ijse.wedding_dress.service.WeddingDressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dresses")
@RequiredArgsConstructor
public class WeddingDressController {

    private final WeddingDressService weddingDressService;


    // =========================================================
    // GET ALL DRESSES
    // USER + ADMIN
    // =========================================================
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<WeddingDress>> getAllDresses() {

        return ResponseEntity.ok(
                weddingDressService.getAllDresses()
        );
    }


    // =========================================================
    // GET DRESS BY ID
    // USER + ADMIN
    // =========================================================
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<WeddingDress> getDressById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                weddingDressService.getDressById(id)
        );
    }


    // =========================================================
    // ADD DRESS
    // ADMIN ONLY
    // =========================================================
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WeddingDress> saveDress(
            @Valid @RequestBody WeddingDress dress) {

        return ResponseEntity.ok(
                weddingDressService.saveDress(dress)
        );
    }


    // =========================================================
    // UPDATE DRESS
    // ADMIN ONLY
    // =========================================================
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WeddingDress> updateDress(
            @PathVariable Long id,
            @Valid @RequestBody WeddingDress dress) {

        return ResponseEntity.ok(
                weddingDressService.updateDress(id, dress)
        );
    }


    // =========================================================
    // DELETE DRESS
    // ADMIN ONLY
    // =========================================================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDress(
            @PathVariable Long id) {

        weddingDressService.deleteDress(id);

        return ResponseEntity.noContent().build();
    }

    // =========================================================
// PATCH DRESS
// ADMIN ONLY
// =========================================================
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WeddingDress> patchDress(
            @PathVariable Long id,
            @RequestBody WeddingDress dress) {

        return ResponseEntity.ok(
                weddingDressService.patchDress(id, dress)
        );
    }
}