package com.atguigu.atcrowdfunding.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class NoListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {

        System.out.println("NoListener...");

    }

}
