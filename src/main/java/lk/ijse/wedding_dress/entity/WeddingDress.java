package lk.ijse.wedding_dress.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank(message = "Dress name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @Column(nullable = false)
    private Double price;

    @NotBlank(message = "Size is required")
    @Column(nullable = false)
    private String size;

    @NotBlank(message = "Color is required")
    @Column(nullable = false)
    private String color;

    private String image;

    @Column(nullable = false)
    private Boolean available;
}