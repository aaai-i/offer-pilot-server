package org.example.offerpilot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobStatusUpdateDTO {

    @NotNull(message = "状态不能为空")
    private Integer status;
}