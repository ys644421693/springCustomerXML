package com.baofu.behavior.spring.module;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 * @author shipotian
 * @since 2017/8/30
 */
public class ActionBean implements Command{
    private Command refCommand;
    @Override
    public boolean execute(Context context) throws Exception {
        return refCommand.execute(context);
    }

    public Command getRefCommand() {
        return refCommand;
    }

    public void setRefCommand(Command refCommand) {
        this.refCommand = refCommand;
    }
}
