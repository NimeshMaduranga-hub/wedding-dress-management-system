package lk.ijse.wedding_dress.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeddingDressResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String size;

    private String color;

    private String image;

    private Boolean available;

    private String category;

    private String inventoryCondition;

    private Integer quantity;

    private Integer availableQuantity;
}