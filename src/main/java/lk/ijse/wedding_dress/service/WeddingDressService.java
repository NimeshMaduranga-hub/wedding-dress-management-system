package lk.ijse.wedding_dress.service;

import lk.ijse.wedding_dress.entity.WeddingDress;

import java.util.List;

public interface WeddingDressService {

    WeddingDress saveDress(WeddingDress weddingDress);

    List<WeddingDress> getAllDresses();

    WeddingDress getDressById(Long id);

    WeddingDress updateDress(Long id, WeddingDress weddingDress);

    WeddingDress patchDress(Long id, WeddingDress weddingDress);

    void deleteDress(Long id);
}