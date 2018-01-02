package org.ibase4j.scheduled;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.model.TaskFireLog;
import org.ibase4j.service.TaskFireLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class CoreTask {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private TaskFireLogService taskFireLogService;

    /**
     *
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void ticktock() {
        /*
        logger.info("ticktock");

        TaskFireLog log = taskFireLogService.selectById(1L);

        System.err.println("done---->>" + log.toString());
        */

    }
}
