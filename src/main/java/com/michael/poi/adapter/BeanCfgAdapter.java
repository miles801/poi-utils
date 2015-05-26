package com.michael.poi.adapter;

import com.michael.poi.core.DTO;
import com.michael.poi.core.Handler;
import com.michael.poi.exceptions.ImportConfigException;
import com.michael.poi.imp.cfg.Configuration;

/**
 * @author Michael
 */
public class BeanCfgAdapter extends Configuration implements Adapter {
    private String targetClass;
    private String handlerClass;

    @Override
    @SuppressWarnings("unchecked")
    public Configuration parse() {
        try {
            if (getClazz() == null) {
                if (targetClass == null) {
                    throw new ImportConfigException("配置错误!没有指定目标对象类!");
                }
                setClazz((Class<? extends DTO>) Class.forName(targetClass));
            }
            if (getHandler() == null) {
                if (handlerClass == null) {
                    throw new ImportConfigException("配置错误!没有指定结果处理类!");
                }
                setHandler((Handler) Class.forName(handlerClass).newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }
}
