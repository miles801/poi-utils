package com.michael.poi;

import com.michael.poi.adapter.AnnotationCfgAdapter;
import com.michael.poi.adapter.BeanCfgAdapter;
import com.michael.poi.core.ImportEngine;
import com.michael.poi.imp.cfg.ColMapping;
import com.michael.poi.imp.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 导入流程节点配置
 * 导入规则：
 * 目标对象：NodeRole
 * 第0行0列开始
 * 前四列为属性字段、后面的为条件字段
 * 1列：flowId
 * 2列：节点名称，需要根据1列的值和当前名称去查询节点的id，即获得nodeId
 * 3列：岗位名称，根据该名称查询对应的岗位的和其类型，即获得roleId和roleType
 * 4列：岗位归属，即postionType
 * 5...n列：都是条件列，需要将其组装成@tsYetai='SG'&&@tsTSDX='TSDX_A009'的格式，设置给condition
 *
 * @author Michael
 */
public class ImportFlowNodeConfigTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testImport() throws Exception {
        AnnotationCfgAdapter adapter = new AnnotationCfgAdapter(FlowNodeDTO.class);
        Configuration configuration = adapter.parse();
        configuration.setHandler(new FlowNodeSaveHandler());
        ImportEngine importEngine = new ImportEngine(configuration);
        importEngine.execute();
    }

    @Test
    public void testImport2() throws Exception {
        BeanCfgAdapter adapter = new BeanCfgAdapter();
        adapter.setTargetClass(FlowNodeDTO.class.getName());
        adapter.setHandlerClass(FlowNodeSaveHandler.class.getName());
        adapter.setStartRow(1);
        adapter.setPath("D:\\workspace\\michael\\poi-utils\\src\\test\\resources\\客诉服务平台流程节点配置_20150321.xlsx");

        List<ColMapping> colMappings = new ArrayList<ColMapping>();
        ColMapping flowIdCol = new ColMapping();
        flowIdCol.setIndex(0);
        flowIdCol.setColName("flowId");
        colMappings.add(flowIdCol);

        ColMapping nodeCol = new ColMapping();
        nodeCol.setIndex(1);
        nodeCol.setColName("nodeName");
        colMappings.add(nodeCol);

        ColMapping roleCol = new ColMapping();
        roleCol.setIndex(2);
        roleCol.setColName("roleName");
        colMappings.add(roleCol);

        adapter.setMappings(colMappings);
        Configuration configuration = adapter.parse();
        configuration.setHandler(new FlowNodeSaveHandler());
        ImportEngine importEngine = new ImportEngine(configuration);
        importEngine.execute();
    }

}
