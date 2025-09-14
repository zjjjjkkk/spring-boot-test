package top.zjjjjkkk.model;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component // 注入
public class Team {
    @Value("${team.name}")
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @Value("${team.leader}")
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 8)
    private String leader;

    @Value("${team.age}")
    @Min(1)
    @Max(4)
    private Integer age;

    @Value("${team.phone}")
    @Pattern(regexp = "^[0-9]{11}$") //正则表达式 由0-9组成的11位字符串
    private String phone;

    @Past
    private LocalDate createTime;
}
