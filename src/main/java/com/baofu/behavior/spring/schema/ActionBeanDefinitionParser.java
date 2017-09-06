package com.baofu.behavior.spring.schema;

import com.baofu.behavior.spring.module.ActionBean;
import com.baofu.behavior.spring.module.ActionListBean;
import com.baofu.behavior.spring.module.IFBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author shipotian
 * @since 2017/8/30
 */
public class ActionBeanDefinitionParser implements BeanDefinitionParser {

    private static final Logger logger = LoggerFactory.getLogger(ActionBeanDefinitionParser.class);
    private final Class<?> beanClass;

    public ActionBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }


    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setLazyInit(false);
        beanDefinition.setBeanClass(beanClass);

        logger.info("开始解析节点 " + element.getNodeName());
        if (ActionBean.class.equals(beanClass)) {
            String ref = element.getAttribute("ref");
            beanDefinition.getPropertyValues().addPropertyValue("refCommand", new RuntimeBeanReference(ref));
        } else if (IFBean.class.equals(beanClass)) {
            String ref = element.getAttribute("ref");
            beanDefinition.getPropertyValues().addPropertyValue("ifCommand", new RuntimeBeanReference(ref));
            parseIfChildeNode(element.getChildNodes(), beanDefinition, parserContext);
        } else if (ActionListBean.class.equals(beanClass)) {
            String id = element.getAttribute("id");
            logger.info("XXXXXXX id = "+id);
            parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
            parseBeanListChildNode(element.getChildNodes(), beanDefinition, parserContext);
        }

        return beanDefinition;
    }
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
       return parse(element,parserContext,this.beanClass);
    }

    private static void parseBeanListChildNode(NodeList nodeList, RootBeanDefinition beanRootDefinition, ParserContext parserContext) {
        if (nodeList != null && nodeList.getLength() > 0) {
            ManagedList subBeans = new ManagedList();
            Node node;
            Element element;
            BeanDefinition beanDefinition = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node instanceof Element) {
                    element = (Element) node;
                    if ("action".equals(node.getNodeName()) || "action".equals(node.getLocalName())) {
                        beanDefinition = parse(element, parserContext,ActionBean.class);
                    } else if ("if".equals(node.getNodeName()) || "if".equals(node.getLocalName())) {
                        beanDefinition = parse(element, parserContext,IFBean.class);
                    }
                    if (beanDefinition != null) {
                        subBeans.add(beanDefinition);
                    }
                }
            }
            beanRootDefinition.getPropertyValues().addPropertyValue("actionList", subBeans);
        }
    }

    private static void parseIfChildeNode(NodeList nodeList, RootBeanDefinition beanRootDefinition, ParserContext parserContext) {
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node;
            Element element;
            BeanDefinition beanDefinition = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node instanceof Element) {
                    element = (Element) node;
                    if ("action".equals(node.getNodeName()) || "action".equals(node.getLocalName())) {
                        beanDefinition = parse(element, parserContext,ActionBean.class);
                        logger.info("IF  set ActionBean "+beanDefinition);
                        beanRootDefinition.getPropertyValues().addPropertyValue("checkOkAction", beanDefinition);
                        break;
                    }
                }
            }
        }
    }
}
