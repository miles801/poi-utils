package com.michael.poi.core;

/**
 * 在导入/导出运行时的数据上下文
 *
 * @author Michael
 */
public class RuntimeContext {
    private static ThreadLocal<Context> _ = new ThreadLocal<Context>();

    public static void set(Context context) {
        _.set(context);
    }

    public static Context get() {
        return _.get();
    }

    public static void remove() {
        _.remove();

    }
}
