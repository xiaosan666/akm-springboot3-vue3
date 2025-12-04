package com.akm.springboot3.web.sys.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunLogReq {
    private Integer maxLine;

    private String logFileName;
}
