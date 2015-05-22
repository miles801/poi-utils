package com.michael.poi.adapter;

import com.michael.poi.core.DTO;
import com.michael.poi.core.Handler;
import com.michael.poi.imp.cfg.Configuration;

/**
 * @author Michael
 */
public class BeanCfgAdapter extends Configuration implements Adapter {
    private String targetClass;
    private String handlerClass;

    @Override
    public Configuration parse() {
        try {
            setClazz((Class<? extends DTO>) Class.forName(targetClass));
            setHandler((Handler) Class.forName(handlerClass).newInstance());
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
