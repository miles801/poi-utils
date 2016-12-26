package com.michael.poi.demo.annotation;

import com.michael.poi.adapter.AnnotationCfgAdapter;
import com.michael.poi.core.ImportEngine;
import com.michael.poi.demo.DemoDTO;
import com.michael.poi.demo.DemoHandler;
import com.michael.poi.imp.cfg.Configuration;
import org.junit.Test;

/**
 * @author Michael
 */
public class DemoAnnotationTest {
    @Test
    public void testAnnotation() throws Exception {
        System.out.println("测试基于Annotation的导入[如果导入成功，会输出导入信]....");

        AnnotationCfgAdapter adapter = new AnnotationCfgAdapter(DemoDTO.class);
        Configuration configuration = adapter.parse();
        String path = DemoAnnotationTest.class.getClassLoader().getResource("demo.xlsx").getPath();
        configuration.setPath(path);                    // 覆盖Annotation中的filePath
        configuration.setHandler(new DemoHandler());    // 指定处理器
        ImportEngine engine = new ImportEngine(configuration);
        engine.execute();

        System.out.println("导入完成....");
    }
}
