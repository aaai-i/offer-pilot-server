package org.example.offerpilot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.offerpilot.entity.JobPost;

@Mapper
public interface JobPostMapper extends BaseMapper<JobPost> {
}