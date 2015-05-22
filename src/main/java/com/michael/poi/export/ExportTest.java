package com.michael.poi.export;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Michael
 */
public class ExportTest {
    public static void main(String[] args) throws FileNotFoundException {
        JsonObject obj = new JsonObject();
        // 基本信息
        obj.addProperty("typeName", "投诉");
        obj.addProperty("yetaiName", "商管");
        obj.addProperty("targetTypeName", "消费者");
        obj.addProperty("provinceName", "江苏省");
        obj.addProperty("cityName", "徐州市");
        obj.addProperty("orgName", "徐州铜山万达广场");
        obj.addProperty("eventTime", "2015-04-21 10:19:00");
        obj.addProperty("targetName", "闫海旭");
        obj.addProperty("sexName", "先生");
        obj.addProperty("phone1", "13439027665");
        obj.addProperty("phone2", "");
        obj.addProperty("faContent", "投诉内容--测试");
        obj.addProperty("require", "要求--测试");

        // 附件
        JsonArray arr = new JsonArray();
        JsonObject a1 = new JsonObject();
        a1.addProperty("name", "附件1");
        a1.addProperty("url", "http://www.baidu.com");
        arr.add(a1);
        JsonObject a2 = new JsonObject();
        a2.addProperty("name", "附件2");
        a2.addProperty("url", "http://www.sina.com");
        arr.add(a2);
        JsonObject a3 = new JsonObject();
        a3.addProperty("name", "附件3");
        a3.addProperty("url", "http://www.google.com");
        arr.add(a3);
        obj.add("attachment", arr);
        new ExportEngine().export(new FileOutputStream("d:/官网.xlsx"), new FileInputStream("d:/官网明细.xlsx"), obj);
    }
}
