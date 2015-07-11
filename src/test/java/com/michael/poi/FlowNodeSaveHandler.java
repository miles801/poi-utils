package com.michael.poi;

import com.michael.poi.core.Handler;

/**
 * 真正的处理器：
 * 当成功读取一行记录，并组装成DTO对象后要执行的操作
 * 例如：保存到数据库
 *
 * @author Michael
 */
public class FlowNodeSaveHandler implements Handler<FlowNodeDTO> {
    @Override
    public void execute(FlowNodeDTO dto) {
        System.out.println(dto.getFlowId() + "-->" + dto.getNodeName() + "-->" + dto.getRoleName());
    }

}
