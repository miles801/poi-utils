package com.michael.poi.exp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.Random;
import java.util.UUID;

/**
 * @author Michael
 */
public class ExportTest {
    @Test
    public void testExportByJsonObject() throws Exception {
        System.out.println("测试普通导出...");
        long start = System.currentTimeMillis();
        Random random = new Random();
        JsonArray jsonArray = new JsonArray();
        for (int i = 1; i < 10001; i++) {
            JsonObject obj = new JsonObject();
            obj.addProperty("index", i);
            obj.addProperty("name", "name_" + random.nextInt(10000));
            obj.addProperty("cardNo", UUID.randomUUID().toString());
            obj.addProperty("mobile", "1" + random.nextInt(1000000000));
            obj.addProperty("salr", random.nextDouble());
            obj.addProperty("valid", random.nextBoolean() ? "是" : "否");
            jsonArray.add(obj);
        }
        JsonObject o = new JsonObject();
        o.add("c", jsonArray);
        new ExportEngine().export(new FileOutputStream("d:/export-test-jsonobject.xlsx"), this.getClass().getClassLoader().getResourceAsStream("export-template.xlsx"), o);
        System.out.println(String.format("导出完成，共耗时%d秒", (System.currentTimeMillis() - start) / 1000));
    }

    @Test
    public void testExportByBatchData() throws Exception {
        System.out.println("测试分批次导出...");
        final BatchData batchData = new BatchData();
        batchData.setStart(0);
        batchData.setLimit(100);
        batchData.setTotal(9533);
        batchData.setDataInjector(new DataInjector() {
            @Override
            public JsonObject fetch(int start, int limit) {
                Random random = new Random();
                JsonArray jsonArray = new JsonArray();
                for (int i = 0; i < batchData.getLimit(); i++) {
                    if (batchData.getStart() + i > batchData.getTotal()) {
                        break;
                    }
                    JsonObject obj = new JsonObject();
                    obj.addProperty("index", batchData.getStart() + i);
                    obj.addProperty("name", "name_" + random.nextInt(10000));
                    obj.addProperty("cardNo", UUID.randomUUID().toString());
                    obj.addProperty("mobile", "1" + random.nextInt(1000000000));
                    obj.addProperty("salr", random.nextDouble());
                    obj.addProperty("valid", random.nextBoolean() ? "是" : "否");
                    jsonArray.add(obj);
                }
                JsonObject o = new JsonObject();
                o.add("c", jsonArray);
                return o;
            }
        });
        new ExportEngine().export(new FileOutputStream("d:/export-test-batchdata.xlsx"), this.getClass().getClassLoader().getResourceAsStream("export-template.xlsx"), batchData);
    }
}
