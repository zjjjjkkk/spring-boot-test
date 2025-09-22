package top.zjk.boot.mp01.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author zjk
 */
@Data
public class StockAdjustDTO {
    @NotNull
    private Integer amount;
}