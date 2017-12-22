package org.ibase4j.service;

import org.ibase4j.mapper.TaskFireLogMapper;
import org.ibase4j.model.TaskFireLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFireLogService {

    @Autowired
    TaskFireLogMapper taskFireLogMapper;

    public TaskFireLog selectById(Long id) {
        return taskFireLogMapper.selectById(id);
    }
}