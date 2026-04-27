package org.example.offerpilot.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobDetailVO {
    private Long id;
    private String companyName;
    private String jobName;
    private String city;
    private Integer salaryMin;
    private Integer salaryMax;
    private String jobUrl;
    private String source;
    private Integer status;
    private Integer priority;
    private String remark;
    private Long resumeId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}