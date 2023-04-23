package com.hspedu.hzxmybatis.mapper;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zexi He.
 * @date 2023/4/23 23:23
 * @description:    封装从 XxxMapper.xml 文件中读取到的配置
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MapperBean {

    private String interfaceName;
    private List<Function> functionList = new ArrayList<>();

}
