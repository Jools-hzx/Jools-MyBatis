package com.hspedu.hzxmybatis.mapper;

import lombok.*;

/**
 * @author Zexi He.
 * @date 2023/4/23 23:21
 * @description:
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Function {

    private String functionName;
    private String sqlType;
    private String parameterType;
    private Object resultType;
    private String sql;

}
