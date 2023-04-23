package com.hspedu.entity;

import lombok.*;

import java.util.Date;

/**
 * @author Zexi He.
 * @date 2023/4/23 22:52
 * @description:    entity 层 Monster 与数据库表成映射关系
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Monster {

    private Integer id;
    private Integer age;
    private String name;
    private String email;
    private Date birthday;
    private double salary;
    private Integer gender;

}