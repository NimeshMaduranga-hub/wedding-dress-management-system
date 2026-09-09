package lk.ijse.wedding_dress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeddingDressRequestDTO {

    @NotBlank(message = "Dress name is required")
    @Size(
            min = 3,
            max = 100,
            message = "Dress name must be between 3 and 100 characters"
    )
    private String name;


    @NotBlank(message = "Description is required")
    @Size(
            min = 10,
            max = 500,
            message = "Description must be between 10 and 500 characters"
    )
    private String description;


    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;


    @NotBlank(message = "Size is required")
    @Size(
            min = 1,
            max = 10,
            message = "Size must be between 1 and 10 characters"
    )
    private String size;


    @NotBlank(message = "Color is required")
    @Size(
            min = 2,
            max = 30,
            message = "Color must be between 2 and 30 characters"
    )
    private String color;


    @Size(
            max = 500,
            message = "Image path must not exceed 500 characters"
    )
    private String image;


    @NotNull(message = "Availability is required")
    private Boolean available;
}