package com.baofu.behavior.spring.schema;

import com.baofu.behavior.spring.module.ActionBean;
import com.baofu.behavior.spring.module.ActionListBean;
import com.baofu.behavior.spring.module.IFBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author shipotian
 * @since 2017/8/30
 */
public class ActionNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        this.registerBeanDefinitionParser("actions", new ActionBeanDefinitionParser(ActionListBean.class));
        this.registerBeanDefinitionParser("action", new ActionBeanDefinitionParser(ActionBean.class));
        this.registerBeanDefinitionParser("if", new ActionBeanDefinitionParser(IFBean.class));
    }
}
