package org.ibase4j.service;

import org.ibase4j.mapper.TaskFireLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFireLogService {

    @Autowired
    TaskFireLogMapper taskFireLogMapper;

}