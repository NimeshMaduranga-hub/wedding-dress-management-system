package lk.ijse.wedding_dress.controller;

import jakarta.validation.Valid;
import lk.ijse.wedding_dress.dto.WeddingDressPatchDTO;
import lk.ijse.wedding_dress.dto.WeddingDressRequestDTO;
import lk.ijse.wedding_dress.dto.WeddingDressResponseDTO;
import lk.ijse.wedding_dress.service.WeddingDressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dresses")
@RequiredArgsConstructor
public class WeddingDressController {

    private final WeddingDressService weddingDressService;


    // =====================================================
    // GET ALL DRESSES
    // =====================================================

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<WeddingDressResponseDTO>> getAllDresses() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        weddingDressService.getAllDresses()
                );
    }


    // =====================================================
    // GET DRESS BY ID
    // =====================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<WeddingDressResponseDTO> getDressById(
            @PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        weddingDressService.getDressById(id)
                );
    }


    // =====================================================
    // POST - SAVE DRESS
    // =====================================================

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WeddingDressResponseDTO> saveDress(
            @Valid @RequestBody WeddingDressRequestDTO requestDTO) {

        WeddingDressResponseDTO savedDress =
                weddingDressService.saveDress(
                        requestDTO
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        savedDress
                );
    }


    // =====================================================
    // PUT - UPDATE DRESS
    // =====================================================

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WeddingDressResponseDTO> updateDress(
            @PathVariable Long id,
            @Valid @RequestBody WeddingDressRequestDTO requestDTO) {

        WeddingDressResponseDTO updatedDress =
                weddingDressService.updateDress(
                        id,
                        requestDTO
                );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        updatedDress
                );
    }


    // =====================================================
    // DELETE DRESS
    // =====================================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDress(
            @PathVariable Long id) {

        weddingDressService.deleteDress(
                id
        );

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


    // =====================================================
    // PATCH - PARTIAL UPDATE
    // =====================================================

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WeddingDressResponseDTO> patchDress(
            @PathVariable Long id,
            @Valid @RequestBody WeddingDressPatchDTO patchDTO) {

        WeddingDressResponseDTO patchedDress =
                weddingDressService.patchDress(
                        id,
                        patchDTO
                );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        patchedDress
                );
    }
}