package com.silver.common.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.silver.common.api.R;
import com.silver.common.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 * 统一拦截并处理系统中各类异常，转换为标准响应格式。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常。
     * 返回业务错误码和错误信息。
     *
     * @param e 业务异常
     * @return 统一响应
     */
    @ExceptionHandler(BusinessException.class)
    public R<Void> handleBusinessException(BusinessException e) {
        return R.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理未登录异常。
     * 返回 401 未登录状态。
     *
     * @param e 未登录异常
     * @return 统一响应
     */
    @ExceptionHandler(NotLoginException.class)
    public R<Void> handleNotLoginException(NotLoginException e) {
        return R.fail(401, "未登录或登录已失效");
    }

    /**
     * 处理无权限异常。
     * 返回 403 无权限状态。
     *
     * @param e 无权限异常
     * @return 统一响应
     */
    @ExceptionHandler(NotPermissionException.class)
    public R<Void> handleNotPermissionException(NotPermissionException e) {
        return R.fail(403, "无权限访问该资源");
    }

    /**
     * 处理兜底异常。
     * 捕获所有未被特定处理器处理的异常，返回 500 错误。
     *
     * @param e 未知异常
     * @return 统一响应
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        return R.fail(500, e.getMessage());
    }
}
