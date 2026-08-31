package lk.ijse.wedding_dress.repository;

import lk.ijse.wedding_dress.entity.WeddingDress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeddingDressRepository
        extends JpaRepository<WeddingDress, Long> {
}