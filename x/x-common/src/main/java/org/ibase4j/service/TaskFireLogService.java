package org.ibase4j.service;

import base.core.BaseService;
import org.ibase4j.model.TaskFireLog;
import org.springframework.stereotype.Service;

@Service
public class TaskFireLogService extends BaseService<TaskFireLog> {

    public TaskFireLog selectById(Long id) {
        return mapper.selectById(id);
    }
}