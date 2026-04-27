package org.example.offerpilot.dto;

import lombok.Data;

@Data
public class JobPageQueryDTO {

    private Long pageNum = 1L;

    private Long pageSize = 10L;

    private String companyName;

    private String city;

    private Integer status;

    private String keyword;
}
