package org.example.offerpilot.service;

import org.example.offerpilot.dto.JobAddDTO;
import org.example.offerpilot.dto.JobPageQueryDTO;
import org.example.offerpilot.dto.JobUpdateDTO;
import org.example.offerpilot.vo.JobDetailVO;
import org.example.offerpilot.vo.PageResult;

public interface JobService {

    void add(JobAddDTO dto, Long userId);

    void update(JobUpdateDTO dto, Long userId);

    void delete(Long id, Long userId);

    JobDetailVO detail(Long id, Long userId);

    PageResult<JobDetailVO> page(JobPageQueryDTO dto, Long userId);

    void updateStatus(Long id, Integer status, Long userId);
}
