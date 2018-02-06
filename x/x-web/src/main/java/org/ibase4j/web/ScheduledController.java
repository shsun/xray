/**
 * 
 */
package org.ibase4j.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;

import base.Assert;
import base.core.AbstractMSAController;
import base.core.Parameter;
import base.scheduler.TaskScheduled;
import base.scheduler.TaskScheduled.TaskType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "调度管理", description = "调度管理")
@RequestMapping(value = "/scheduled")
public class ScheduledController extends AbstractMSAController<ISysProvider> {
    public String getService() {
        return "scheduledService";
    }

    @PostMapping
    @ApiOperation(value = "新增任务")
    @RequiresPermissions("sys.task.scheduled.update")
    public Object updateTask(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody TaskScheduled scheduled) {
        Assert.notNull(scheduled.getJobType(), "JOBTYPE");
        Assert.notNull(scheduled.getTaskType(), "TASKTYPE");
        Assert.notNull(scheduled.getTargetObject(), "TARGETOBJECT");
        Assert.notNull(scheduled.getTargetMethod(), "TARGETMETHOD");
        Assert.notNull(scheduled.getTaskCron(), "TASKCRON");
        Assert.notNull(scheduled.getTaskDesc(), "TASKDESC");
        if (TaskType.dubbo.equals(scheduled.getTaskType())) {
            Assert.notNull(scheduled.getTargetSystem(), "TARGETSYSTEM");
        }
        Parameter parameter = new Parameter(getService(), "updateTask").setModel(scheduled);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @DeleteMapping
    @ApiOperation(value = "删除任务")
    @RequiresPermissions("sys.task.scheduled.close")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody TaskScheduled scheduled) {
        Assert.notNull(scheduled.getTaskGroup(), "TASKGROUP");
        Assert.notNull(scheduled.getTaskName(), "TASKNAME");
        Parameter parameter = new Parameter(getService(), "delTask").setModel(scheduled);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @PostMapping("/run")
    @ApiOperation(value = "立即执行任务")
    @RequiresPermissions("sys.task.scheduled.run")
    public Object exec(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody TaskScheduled scheduled) {
        Assert.notNull(scheduled.getTaskGroup(), "TASKGROUP");
        Assert.notNull(scheduled.getTaskName(), "TASKNAME");
        Parameter parameter = new Parameter(getService(), "execTask").setModel(scheduled);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @PostMapping("/open")
    @ApiOperation(value = "启动任务")
    @RequiresPermissions("sys.task.scheduled.open")
    public Object open(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody TaskScheduled scheduled) {
        Assert.notNull(scheduled.getTaskGroup(), "TASKGROUP");
        Assert.notNull(scheduled.getTaskName(), "TASKNAME");
        Parameter parameter = new Parameter(getService(), "openTask").setModel(scheduled);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @PostMapping("/close")
    @ApiOperation(value = "暂停任务")
    @RequiresPermissions("sys.task.scheduled.close")
    public Object close(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody TaskScheduled scheduled) {
        Assert.notNull(scheduled.getTaskGroup(), "TASKGROUP");
        Assert.notNull(scheduled.getTaskName(), "TASKNAME");
        Parameter parameter = new Parameter(getService(), "closeTask").setModel(scheduled);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @PutMapping("/read/tasks")
    @ApiOperation(value = "任务列表")
    @RequiresPermissions("sys.task.scheduled.read")
    public Object list(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user) {
        Parameter parameter = new Parameter(getService(), "getAllTaskDetail");
        List<?> records = provider.execute(parameter).getList();
        map.put("recordsTotal", records.size());
        map.put("total", records.size());
        map.put("current", 1);
        map.put("size", records.size());
        return setSuccessModelMap(map, records);
    }

    @PutMapping("/read/log")
    @ApiOperation(value = "任务执行记录")
    @RequiresPermissions("sys.task.log.read")
    public Object getFireLog(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody Map<String, Object> log) {
        Parameter parameter = new Parameter(getService(), "queryLog").setMap(log);
        Page<?> list = provider.execute(parameter).getPage();
        return setSuccessModelMap(map, list);
    }
}