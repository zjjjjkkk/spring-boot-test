package top.zjk.boot.mp01.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author zjk
 */
@Data
public class BookUpdateDTO {
    @NotBlank
    private String title;
    private String author;
    private String category;

    @NotNull(message = "version 不能为空（乐观锁）")
    private Integer version;
}