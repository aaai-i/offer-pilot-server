package org.example.offerpilot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.example.offerpilot.dto.JobAddDTO;
import org.example.offerpilot.dto.JobPageQueryDTO;
import org.example.offerpilot.dto.JobUpdateDTO;
import org.example.offerpilot.entity.JobPost;
import org.example.offerpilot.exception.BizException;
import org.example.offerpilot.mapper.JobPostMapper;
import org.example.offerpilot.service.JobService;
import org.example.offerpilot.vo.JobDetailVO;
import org.example.offerpilot.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobPostMapper jobPostMapper;

    @Override
    public void add(JobAddDTO dto, Long userId) {
        JobPost jobPost = new JobPost();
        BeanUtils.copyProperties(dto, jobPost);
        jobPost.setUserId(userId);
        if (jobPost.getPriority() == null) {
            jobPost.setPriority(0);
        }
        jobPostMapper.insert(jobPost);
    }


@Override
public void update(JobUpdateDTO dto, Long userId) {
    JobPost exist = getUserJobOrThrow(dto.getId(), userId);
    BeanUtils.copyProperties(dto, exist);
    exist.setUserId(userId);
    jobPostMapper.updateById(exist);
}

@Override
public void delete(Long id, Long userId) {
    JobPost exist = getUserJobOrThrow(id, userId);
    jobPostMapper.deleteById(exist.getId());
}

@Override
public JobDetailVO detail(Long id, Long userId) {
    JobPost exist = getUserJobOrThrow(id, userId);
    JobDetailVO vo = new JobDetailVO();
    BeanUtils.copyProperties(exist, vo);
    return vo;
}

@Override
public PageResult<JobDetailVO> page(JobPageQueryDTO dto, Long userId) {
    Page<JobPost> page = new Page<>(dto.getPageNum(), dto.getPageSize());

    LambdaQueryWrapper<JobPost> wrapper = new LambdaQueryWrapper<JobPost>()
            .eq(JobPost::getUserId, userId)
            .like(StringUtils.hasText(dto.getCompanyName()), JobPost::getCompanyName, dto.getCompanyName())
            .eq(StringUtils.hasText(dto.getCity()), JobPost::getCity, dto.getCity())
            .eq(dto.getStatus() != null, JobPost::getStatus, dto.getStatus())
            .and(StringUtils.hasText(dto.getKeyword()), w -> w
                    .like(JobPost::getJobName, dto.getKeyword())
                    .or()
                    .like(JobPost::getCompanyName, dto.getKeyword()))
            .orderByDesc(JobPost::getUpdateTime)
            .orderByDesc(JobPost::getId);

    IPage<JobPost> resultPage = jobPostMapper.selectPage(page, wrapper);

    List<JobDetailVO> records = resultPage.getRecords().stream().map(item -> {
        JobDetailVO vo = new JobDetailVO();
        BeanUtils.copyProperties(item, vo);
        return vo;
    }).collect(Collectors.toList());

    return new PageResult<>(resultPage.getTotal(), records);
}

@Override
public void updateStatus(Long id, Integer status, Long userId) {
    JobPost exist = getUserJobOrThrow(id, userId);
    exist.setStatus(status);
    jobPostMapper.updateById(exist);
}

private JobPost getUserJobOrThrow(Long id, Long userId) {
    JobPost jobPost = jobPostMapper.selectOne(new LambdaQueryWrapper<JobPost>()
            .eq(JobPost::getId, id)
            .eq(JobPost::getUserId, userId));
    if (jobPost == null) {
        throw new BizException("岗位不存在或无权访问");
    }
    return jobPost;
}
}