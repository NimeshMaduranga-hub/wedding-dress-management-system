package lk.ijse.wedding_dress.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wedding_dresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeddingDress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // Dress Name
    // =========================

    @NotBlank(message = "Dress name is required")
    @Size(
            min = 3,
            max = 100,
            message = "Dress name must be between 3 and 100 characters"
    )
    @Column(nullable = false, length = 100)
    private String name;


    // =========================
    // Description
    // =========================

    @NotBlank(message = "Description is required")
    @Size(
            min = 10,
            max = 500,
            message = "Description must be between 10 and 500 characters"
    )
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;


    // =========================
    // Price
    // =========================

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @Column(nullable = false)
    private Double price;


    // =========================
    // Size
    // =========================

    @NotBlank(message = "Size is required")
    @Size(
            min = 1,
            max = 10,
            message = "Size must be between 1 and 10 characters"
    )
    @Column(nullable = false, length = 10)
    private String size;


    // =========================
    // Color
    // =========================

    @NotBlank(message = "Color is required")
    @Size(
            min = 2,
            max = 30,
            message = "Color must be between 2 and 30 characters"
    )
    @Column(nullable = false, length = 30)
    private String color;


    // =========================
    // Image
    // =========================

    @Size(
            max = 500,
            message = "Image path must not exceed 500 characters"
    )
    @Column(length = 500)
    private String image;


    // =========================
    // Availability
    // =========================

    @NotNull(message = "Availability is required")
    @Column(nullable = false)
    private Boolean available;


    // =========================
    // Category Relationship
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private DressCategory category;


    // =========================
    // Size Relationship
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private DressSize sizeEntity;


    // =========================
    // Color Relationship
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private DressColor colorEntity;


    // =========================
    // Dress Images
    // =========================

    @OneToMany(
            mappedBy = "dress",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DressImage> images = new ArrayList<>();


    // =========================
    // Dress Inventory
    // =========================

    @OneToOne(
            mappedBy = "dress",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private DressInventory inventory;
};