package com.kobin.entity.log;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created by @author shibinbin on 2017/11/8.
 */

@Data
public class AnalysisLog {

    private Object requestContent;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object responseContent;
    private long runtime;
}
