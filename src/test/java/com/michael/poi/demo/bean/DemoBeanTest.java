package com.michael.poi.demo.bean;

import com.michael.poi.adapter.BeanCfgAdapter;
import com.michael.poi.core.ImportEngine;
import com.michael.poi.demo.BooleanConverter;
import com.michael.poi.demo.DemoDTO;
import com.michael.poi.demo.DemoHandler;
import com.michael.poi.imp.cfg.ColMapping;
import com.michael.poi.imp.cfg.Configuration;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael
 */
public class DemoBeanTest {

    @Test
    public void testBean() throws Exception {
        System.out.println("测试基于Bean配置的导入[导入成功后会输出语句]...");
        BeanCfgAdapter adapter = new BeanCfgAdapter();
        adapter.setTargetClass(DemoDTO.class.getName());        // 目标对象类型
        adapter.setHandlerClass(DemoHandler.class.getName());   // 处理器
        adapter.setStartRow(3);                                 // 其实行
        adapter.setPath(DemoBeanTest.class.getClassLoader().getResource("demo.xlsx").getPath());    // 数据文件

        // 配置映射信息
        List<ColMapping> colMappings = new ArrayList<ColMapping>();
        ColMapping col1 = new ColMapping();
        col1.setIndex(0);
        col1.setColName("index");
        colMappings.add(col1);

        ColMapping col2 = new ColMapping();
        col2.setIndex(1);
        col2.setColName("name");
        col2.setRequired(true);
        colMappings.add(col2);

        ColMapping col3 = new ColMapping();
        col3.setIndex(2);
        col3.setColName("cardNo");
        col3.setRequired(true);
        colMappings.add(col3);

        ColMapping col4 = new ColMapping();
        col4.setIndex(3);
        col4.setColName("phone");
        colMappings.add(col4);

        ColMapping col5 = new ColMapping();
        col5.setIndex(4);
        col5.setColName("salary");
        colMappings.add(col5);

        ColMapping col6 = new ColMapping();
        col6.setIndex(5);
        col6.setColName("valid");
        col6.setConverter(new BooleanConverter());
        colMappings.add(col6);

        adapter.setMappings(colMappings);
        Configuration configuration = adapter.parse();
        configuration.setHandler(new DemoHandler());
        ImportEngine importEngine = new ImportEngine(configuration);
        importEngine.execute();

        System.out.println("导入成功...");
    }

}
