package org.example.offerpilot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobUpdateDTO {

    @NotNull(message = "岗位ID不能为空")
    private Long id;

    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    @NotBlank(message = "岗位名称不能为空")
    private String jobName;

    private String city;

    private Integer salaryMin;

    private Integer salaryMax;

    private String jobUrl;

    private String source;

    @NotNull(message = "状态不能为空")
    private Integer status;

    private Integer priority;

    private String remark;

    private Long resumeId;
}
