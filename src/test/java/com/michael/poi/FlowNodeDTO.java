package com.michael.poi;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.core.DTO;

/**
 * @author Michael
 */
@ImportConfig(
        // 要导入的文件的路径
        file = "D:\\workspace\\michael\\poi-utils\\src\\test\\resources\\客诉服务平台流程节点配置_20150321.xlsx",
        // 要读取哪些“工作区”
        sheets = {0},
        // 数据要从哪一行开始读取
        startRow = 1
)
public class FlowNodeDTO implements DTO {

    @Col(index = 0)
    private String flowId;  // 值来自第一列

    @Col(index = 1)
    private String nodeName;    // 值来自第二列，并且需要进行转换
    private String nodeId;

    @Col(index = 2)
    private String roleName;// 岗位名称，来自第三列，并且需要进行转换
    private String roleId;  // 岗位ID
    @Col(index = 3)
    private String roleType;// 来自第四列

    @Col(index = 4)
    private String condition;


    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
