package org.example.offerpilot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("job_post")
public class JobPost {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
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
