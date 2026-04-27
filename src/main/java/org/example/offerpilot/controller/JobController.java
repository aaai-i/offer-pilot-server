package org.example.offerpilot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.offerpilot.common.Result;
import org.example.offerpilot.common.UserContext;
import org.example.offerpilot.dto.JobAddDTO;
import org.example.offerpilot.dto.JobPageQueryDTO;
import org.example.offerpilot.dto.JobStatusUpdateDTO;
import org.example.offerpilot.dto.JobUpdateDTO;
import org.example.offerpilot.service.JobService;
import org.example.offerpilot.vo.JobDetailVO;
import org.example.offerpilot.vo.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public Result<Void> add(@RequestBody @Valid JobAddDTO dto) {
        Long userId = UserContext.getUserId();
        jobService.add(dto, userId);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Valid JobUpdateDTO dto) {
        Long userId = UserContext.getUserId();
        dto.setId(id);
        jobService.update(dto, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        jobService.delete(id, userId);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<JobDetailVO> detail(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return Result.success(jobService.detail(id, userId));
    }

    @GetMapping("/page")
    public Result<PageResult<JobDetailVO>> page(JobPageQueryDTO dto) {
        Long userId = UserContext.getUserId();
        return Result.success(jobService.page(dto, userId));
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody @Valid JobStatusUpdateDTO dto) {
        Long userId = UserContext.getUserId();
        jobService.updateStatus(id, dto.getStatus(), userId);
        return Result.success();
    }
}
