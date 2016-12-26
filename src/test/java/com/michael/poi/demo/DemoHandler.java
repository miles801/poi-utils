package com.michael.poi.demo;

import com.michael.poi.core.Handler;

/**
 * 真正的处理器：
 * 当成功读取一行记录，并组装成DTO对象后要执行的操作
 * 例如：保存到数据库
 *
 * @author Michael
 */
public class DemoHandler implements Handler<DemoDTO> {
    @Override
    public void execute(DemoDTO dto) {
        System.out.println(String.format("%s,%s,%s,%s,%s,%s", dto.getIndex(), dto.getName(), dto.getPhone(), dto.getSalary(), dto.getCardNo(), dto.getValid()));
    }

}
