package lk.ijse.wedding_dress.service;

import lk.ijse.wedding_dress.dto.WeddingDressPatchDTO;
import lk.ijse.wedding_dress.dto.WeddingDressRequestDTO;
import lk.ijse.wedding_dress.dto.WeddingDressResponseDTO;

import java.util.List;

public interface WeddingDressService {

    // =====================================================
    // SAVE
    // =====================================================

    WeddingDressResponseDTO saveDress(
            WeddingDressRequestDTO requestDTO
    );


    // =====================================================
    // GET ALL
    // =====================================================

    List<WeddingDressResponseDTO> getAllDresses();


    // =====================================================
    // GET BY ID
    // =====================================================

    WeddingDressResponseDTO getDressById(
            Long id
    );


    // =====================================================
    // PUT - FULL UPDATE
    // =====================================================

    WeddingDressResponseDTO updateDress(
            Long id,
            WeddingDressRequestDTO requestDTO
    );


    // =====================================================
    // PATCH - PARTIAL UPDATE
    // =====================================================

    WeddingDressResponseDTO patchDress(
            Long id,
            WeddingDressPatchDTO patchDTO
    );


    // =====================================================
    // DELETE
    // =====================================================

    void deleteDress(
            Long id
    );
}