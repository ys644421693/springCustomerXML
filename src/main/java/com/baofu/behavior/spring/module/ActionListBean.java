package com.baofu.behavior.spring.module;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author shipotian
 * @since 2017/8/30
 */
public class ActionListBean extends ChainBase implements InitializingBean {
    private List<Command> actionList;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(actionList!=null && actionList.size()>0){
            this.commands = new Command[actionList.size()];
            for(int i=0;i<actionList.size();i++){
                this.commands[i] = actionList.get(i);
            }
        }
    }

    public List<Command> getActionList() {
        return actionList;
    }

    public void setActionList(List<Command> actionList) {
        this.actionList = actionList;
    }
}
