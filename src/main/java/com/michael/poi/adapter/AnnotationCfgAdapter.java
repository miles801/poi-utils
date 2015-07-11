package com.michael.poi.adapter;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.FieldConvert;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.core.DTO;
import com.michael.poi.imp.cfg.ColMapping;
import com.michael.poi.imp.cfg.Configuration;
import com.michael.poi.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 将使用Annotation的配置方式转换成对应的配置对象
 *
 * @author Michael
 */
public class AnnotationCfgAdapter implements Adapter {
    private Class<? extends DTO> clazz;

    public AnnotationCfgAdapter(Class<? extends DTO> clazz) {
        this.clazz = clazz;
    }


    @Override
    public Configuration parse() {
        Configuration cfg = new Configuration();
        cfg.setClazz(clazz);

        ImportConfig config = clazz.getAnnotation(ImportConfig.class);
        // 获得文件路径
        String filePath = config.file();
        cfg.setPath(filePath);
        // 获得工作表
        int[] sheets = config.sheets();
        for (int s : sheets) {
            cfg.addSheet(s);
        }
        // 获得起始行
        int startRow = config.startRow();
        cfg.setStartRow(startRow);

        // 设置映射关系
        List<Field> fields = ReflectUtils.searchAnnotationFields(clazz, Col.class);
        for (Field field : fields) {
            Col col = field.getAnnotation(Col.class);
            ColMapping mapping = new ColMapping();
            mapping.setIndex(col.index());
            mapping.setColName(field.getName());
            mapping.setRequired(col.required());
            FieldConvert fieldConvert = field.getAnnotation(FieldConvert.class);
            if (fieldConvert != null) {
                try {
                    mapping.setConverter(fieldConvert.convertorClass().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            cfg.addColMapping(mapping);
        }
        return cfg;
    }
}
