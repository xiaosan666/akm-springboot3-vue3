package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CacheRoleOrg {

    @Schema(title = "sys_role表id")
    private String roleId;


    @Schema(title = "sys_log表id")
    private String orgId;

}
