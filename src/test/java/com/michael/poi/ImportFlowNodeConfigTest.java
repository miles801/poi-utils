package com.michael.poi;

import com.michael.poi.core.ImportEngine;
import org.junit.Before;
import org.junit.Test;

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
        ImportEngine importEngine = new ImportEngine(FlowNodeDTO.class, new FlowNodeSaveHandler());
        importEngine.execute();
    }

}
