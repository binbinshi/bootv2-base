package com.kobin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author shibinbin on 2017/11/8.
 */

@Slf4j
public class JsonUtil {
    /**
     * Jackson Object转JsonString.
     */
    public static String jacksonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("--> JsonProcessingException[AnalysisLogAspect - toJsonString]", object);
            return "JsonProcessingException[AnalysisLogAspect - toJsonString]";
        }
    }
}
