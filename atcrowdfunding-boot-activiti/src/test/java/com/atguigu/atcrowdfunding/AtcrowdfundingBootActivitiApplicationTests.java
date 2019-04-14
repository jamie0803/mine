package com.atguigu.atcrowdfunding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtcrowdfundingBootActivitiApplicationTests {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    //12.测试流程监听器
    @Test
    public void test12() {

        ProcessDefinition pd = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess7")
                .latestVersion()
                .singleResult();

        ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());

        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list1 = taskQuery.processDefinitionId(pd.getId()).taskAssignee("zhangsan").list();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("flag", false);
        for (Task task : list1) {
            taskService.complete(task.getId(), variables);
        }


    }

    //11.网关-包含网关 - 相当于排他网关和并行网关的综合使用.
    @Test
    public void test11() {

        ProcessDefinition pd = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess6")
                .latestVersion()
                .singleResult();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("days", 5);
        variables.put("cost", 3000);

        ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId(), variables);
        System.out.println(pi);

        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list1 = taskQuery.processDefinitionId(pd.getId()).taskAssignee("zhangsan").list();
        System.out.println("zhangsan的任务:" + list1.size());
        for (Task task : list1) {
            System.out.println("zhangsan的任务:" + task);
            taskService.complete(task.getId());
        }
        System.out.println("zhangsan完成了-任务");

        System.out.println("===================================");

        List<Task> list2 = taskQuery.processDefinitionId(pd.getId()).taskAssignee("lisi").list();
        System.out.println("lisi的任务:" + list2.size());

        for (Task task : list2) {
            System.out.println("lisi的任务:" + task);
            taskService.complete(task.getId());
        }
        System.out.println("lisi完成了-任务");
    }

    //10.网关-并行网关
    @Test
    public void test10() {

        ProcessDefinition pd = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess5")
                .latestVersion()
                .singleResult();

        ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());
        System.out.println(pi);

        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list1 = taskQuery.processDefinitionId(pd.getId()).taskAssignee("zhangsan").list();
        System.out.println("zhangsan的任务:" + list1.size());
        for (Task task : list1) {
            System.out.println("zhangsan的任务:" + task);
            taskService.complete(task.getId());
        }
        System.out.println("zhangsan完成了-任务");

        System.out.println("===================================");
        /*
        List<Task> list2 = taskQuery.processDefinitionId(pd.getId()).taskAssignee("lisi").list();
		System.out.println("lisi的任务:"+list2.size());

		for (Task task : list2) {		
			System.out.println("lisi的任务:"+task);
			taskService.complete(task.getId());
		}
		System.out.println("lisi完成了-任务");*/
    }


    //9.网关-排他网关
    @Test
    public void test9() {
        //1.部署
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess4.bpmn").deploy();
        System.out.println(deploy);

        ProcessDefinition pd = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess4")
                .latestVersion()
                .singleResult();
        //2.启动流程实例
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("days", "5");

        ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId(), variables);
        System.out.println(pi);

        //3.组长审批
        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list = taskQuery.taskAssignee("zhangsan").list();
        System.out.println("zhangsan的任务:" + list.size());

        for (Task task : list) {
            taskService.complete(task.getId());
        }

    }


    //org.activiti.engine.ActivitiException: Unknown property used in expression: ${tl}
    //8.测试流程变量
    @Test
    public void test8() {
        ProcessDefinition pd = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess3")
                .latestVersion()
                .singleResult();

        //ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("tl", "zhangsan");

        ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId(), variables);
        System.out.println(pi);
    }

    @Test
    public void test81() {

        //查询任务
        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list = taskQuery.taskAssignee("zhangsan").list();
        System.out.println("zhangsan的任务:" + list.size());

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("pm", "lisi");

        for (Task task : list) {

            taskService.complete(task.getId(), variables);
        }

    }


    //7.设置委托组,根据委托组查询任务.
    @Test
    public void test7() {

        //查询任务
        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list = taskQuery.taskCandidateGroup("auth").list();
        for (Task task : list) {
            System.out.println(task);
            taskService.claim(task.getId(), "zhangsan");
        }

    }

    @Test
    public void test71() {

        //查询任务
        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list = taskQuery.taskAssignee("zhangsan").list();
        System.out.println("zhangsan领取到的任务:" + list.size());
        for (Task task : list) {
            taskService.complete(task.getId());
        }

    }


    //6.查询历史记录
    @Test
    public void test6() {

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess1")
                .latestVersion()
                .singleResult();

        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();

        HistoricProcessInstance historicProcessInstance =
                historicProcessInstanceQuery
                        .processDefinitionId(processDefinition.getId())
                        .finished().singleResult();

        System.out.println(historicProcessInstance);

    }

    //5.1 完成任务
    @Test
    public void test51() {

        //查询任务
        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list1 = taskQuery.taskAssignee("lisi").list();

        for (Task task : list1) {
            System.out.println("lisi的任务:" + task.getId() + " - " + task.getName());
            taskService.complete(task.getId());
        }

    }

    //5.演示委托人,获取任务
    @Test
    public void test5() {
		/*ProcessDefinition pd = repositoryService
			.createProcessDefinitionQuery()
			.processDefinitionKey("myProcess1")
			.latestVersion()
			.singleResult();*/
		
		/*ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());
		System.out.println(pi);*/

        //查询任务
        TaskQuery taskQuery = taskService.createTaskQuery();

        List<Task> list1 = taskQuery.taskAssignee("zhangsan").list();
        List<Task> list2 = taskQuery.taskAssignee("lisi").list();

        for (Task task : list1) {
            System.out.println("zhangsan的任务:" + task.getId() + " - " + task.getName());
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (Task task : list2) {
            System.out.println("lisi的任务:" + task.getId() + " - " + task.getName());
        }

    }


    //4.启动流程实例
	/*
		act_hi_actinst 执行任务活动实例表
		act_hi_procinst 流程实例表.
		act_hi_taskinst 执行任务历史表
		act_ru_execution 正在执行任务信息表
		act_ru_task 正在执行任务信息表
	 */
    @Test
    public void test4() {
        ProcessDefinition pd = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess2")
                .latestVersion()
                .singleResult();

        ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());
        System.out.println(pi);
    }


    //act_re_procdef
    //3.流程定义查询
    @Test
    public void test3() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

	/*	List<ProcessDefinition> list = processDefinitionQuery.list();
		for (ProcessDefinition pd : list) {
			System.out.println(pd.getId() +" - "+pd.getName() +" - " + pd.getKey() +" - " + pd.getVersion());
		}*/
		
		/*long count = processDefinitionQuery.count();
		System.out.println("count="+count);*/
		
		/*ProcessDefinition pd = processDefinitionQuery.processDefinitionKey("myProcess").latestVersion().singleResult();
		System.out.println(pd.getId() +" - "+pd.getName() +" - " + pd.getKey() +" - " + pd.getVersion());
	*/
		
		/*ProcessDefinitionQuery query = processDefinitionQuery.orderByProcessDefinitionVersion().desc();
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition pd : list) {
			System.out.println(pd.getId() +" - "+pd.getName() +" - " + pd.getKey() +" - " + pd.getVersion());
		}*/

        int startindex = 1;
        int pagesize = 1;
        List<ProcessDefinition> list = processDefinitionQuery.listPage(startindex, pagesize);
        for (ProcessDefinition pd : list) {
            System.out.println(pd.getId() + " - " + pd.getName() + " - " + pd.getKey() + " - " + pd.getVersion());
        }
    }


    //2.测试流程部署
    // act_re_deployment 部署表
    // act_ge_bytearray 部署二进制文件表
    // act_re_procdef 流程定义信息表
    @Test
    public void test2() {
        //可以通过流程引擎对象获取,也可以依赖注入.
        //RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess7.bpmn").deploy();
        System.out.println(deploy);
    }

    //1.测试流程引擎对象,创建25张表.
    //Caused by: java.lang.ClassNotFoundException: org.apache.ibatis.annotations.Mapperss
    @Test
    public void test1() {
        System.out.println(processEngine);
    }

}
