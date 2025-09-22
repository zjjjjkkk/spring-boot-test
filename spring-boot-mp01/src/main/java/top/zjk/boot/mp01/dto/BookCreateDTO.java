package top.zjk.boot.mp01.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author zjk
 */
@Data
public class BookCreateDTO {
    @NotBlank
    private String title;
    private String author;
    @NotBlank @Pattern(regexp = "\\d{10,17}") private String isbn;
    private String category;
    @Min(0) private Integer stock = 0;
}



