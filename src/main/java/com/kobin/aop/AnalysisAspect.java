package com.kobin.aop;

import com.google.common.base.Stopwatch;


import com.kobin.entity.ResMsg;
import com.kobin.entity.enums.CodeEnum;
import com.kobin.entity.exception.IVException;
import com.kobin.entity.log.AnalysisLog;

import org.apache.http.conn.ConnectTimeoutException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import static com.kobin.util.JsonUtil.jacksonString;
import static com.kobin.util.RealIpUtil.getReqRealIP;


/**
 * Created by @author shibinbin on 2017/11/8.
 */
@Aspect
@Component
@Slf4j
public class AnalysisAspect {
    private static final Logger ivLog = LoggerFactory.getLogger("ID_verified_analysis_log");

    @Pointcut("@annotation(com.kobin.annotation.LogAnalysis)")
    public void logAnalysis(){

    }

    @Around("logAnalysis()")
    public ResMsg aroud(final ProceedingJoinPoint point){
        Stopwatch stopWatch = Stopwatch.createStarted();
        ResMsg resMsg = null;
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("url", getReqRealIP(req));
        try {
            String classType = point.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String methodName = point.getSignature().getName();
            Method method = clazz.getDeclaredMethod(methodName, getMethodParamTypes(clazz.newInstance(), methodName));
            for (int i = 0; i < method.getParameters().length; i++) {
                Parameter parameter = method.getParameters()[i];
                reqMap.put(parameter.getName(), point.getArgs()[i]);
            }
            resMsg = (ResMsg) point.proceed(point.getArgs());
        } catch (Throwable t) {
            resMsg = this.getExceptionRes(t);
        } finally {
            AnalysisLog logEntity = new AnalysisLog();
            logEntity.setRequestContent(reqMap);
            logEntity.setResponseContent(resMsg);
            logEntity.setRuntime(stopWatch.stop().elapsed(TimeUnit.MILLISECONDS));
            ivLog.info(jacksonString(logEntity));
        }
        return resMsg;
    }


    /**
     * 通过反射获取到参数信息
     * @param classInstance
     * @param methodName
     * @return
     * @throws ClassNotFoundException
     */
    private Class[] getMethodParamTypes(Object classInstance, String methodName)
            throws ClassNotFoundException {
        Class[] paramTypes = null;
        //全部方法
        Method[] methods = classInstance.getClass().getMethods();
        for (Method method : methods) {
            //和传入方法名匹配
            if (methodName.equals(method.getName())) {
                Class[] params = method.getParameterTypes();
                paramTypes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    paramTypes[i] = Class.forName(params[i].getName());
                }
                break;
            }
        }
        return paramTypes;
    }

    /**
     * 处理异常时的返回消息.
     */
    private ResMsg getExceptionRes(Throwable t) {
        ResMsg resMsg;
        if (t instanceof IVException) {
            IVException bisException = (IVException) t;
            resMsg = bisException.getIVResponse();
        } else if (t instanceof HttpClientErrorException) {
            log.error("--> HttpClientErrorException", t);
            HttpClientErrorException hceException = (HttpClientErrorException) t;
            resMsg = ResMsg.fail(CodeEnum.UNKNOWN, hceException.getStatusCode().toString(),
                    hceException.getResponseBodyAsString());
        } else if (t instanceof HttpServerErrorException) {
            log.error("--> HttpServerErrorException", t);
            HttpServerErrorException hseException = (HttpServerErrorException) t;
            resMsg = ResMsg.fail(CodeEnum.UNKNOWN, hseException.getStatusCode().toString(),
                    hseException.getResponseBodyAsString());
        } else if (t instanceof ResourceAccessException) {
            log.error("--> ResourceAccessException", t);
            ResourceAccessException resourceAccessException = (ResourceAccessException) t;
            if (resourceAccessException.getCause() instanceof SocketTimeoutException) {
                SocketTimeoutException socketTimeoutException = (SocketTimeoutException) resourceAccessException
                        .getCause();
                resMsg = ResMsg.fail(CodeEnum.READ_TIME_OUT, "", socketTimeoutException.toString());
            } else if (resourceAccessException.getCause() instanceof ConnectTimeoutException) {
                ConnectTimeoutException connectTimeoutException = (ConnectTimeoutException) resourceAccessException
                        .getCause();
                resMsg = ResMsg.fail(CodeEnum.CONNECT_TIME_OUT, "", connectTimeoutException.toString());
            } else {
                resMsg = ResMsg.fail(CodeEnum.UNKNOWN, "", resourceAccessException.toString());
            }
        } else {
            log.error("--> unknowException : ", t);
            resMsg = ResMsg.unknowWithMsg(t.toString());
        }
        return resMsg;
    }

}
