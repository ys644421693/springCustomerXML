package com.baofu.behavior.spring.module;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 * @author shipotian
 * @since 2017/8/30
 */
public class IFBean implements Command{
    private Command ifCommand;
    private ActionBean checkOkAction;
    @Override
    public boolean execute(Context context) throws Exception {
        if(!ifCommand.execute(context)){
            return checkOkAction.execute(context);
        }
        return CONTINUE_PROCESSING;
    }

    public Command getIfCommand() {
        return ifCommand;
    }

    public void setIfCommand(Command ifCommand) {
        this.ifCommand = ifCommand;
    }

    public ActionBean getCheckOkAction() {
        return checkOkAction;
    }

    public void setCheckOkAction(ActionBean checkOkAction) {
        this.checkOkAction = checkOkAction;
    }
}
