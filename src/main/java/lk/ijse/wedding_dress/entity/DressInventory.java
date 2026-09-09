package lk.ijse.wedding_dress.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dress_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DressInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dress_id", nullable = false, unique = true)
    private WeddingDress dress;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity = 1;

    @Column(name = "condition_status", nullable = false, length = 50)
    private String conditionStatus = "GOOD";
}