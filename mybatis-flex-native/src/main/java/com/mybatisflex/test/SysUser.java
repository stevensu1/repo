package com.mybatisflex.test;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *  实体类。
 *
 * @author Lenovo
 * @since 2024-04-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_user")
public class SysUser implements Serializable {



    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String sysUserName;

    private String sysUserPassword;

    /**
     * 登录账号
     */
    private Integer loginAccountId;

    /**
     * 电话号码
     */
    private String telNo;

}
