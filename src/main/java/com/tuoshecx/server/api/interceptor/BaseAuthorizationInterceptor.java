package com.tuoshecx.server.api.interceptor;

import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.security.CredentialContextUtils;
import com.tuoshecx.server.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 基础权限认证拦截器
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class BaseAuthorizationInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BaseAuthorizationInterceptor.class);

    private final TokenService service;

    public BaseAuthorizationInterceptor(TokenService service) {
        this.service = service;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
        //跨域处理
        if(CorsUtils.isPreFlightRequest(request)){
            logger.debug("Cors request ......");
            return true;
        }

        Optional<String> optional = getToken(request);
        if(!optional.isPresent()){
            responseForbid(request, response);
            return  false;
        }

        Optional<Credential> credential = validateAndGet(optional.get());
        if(!credential.isPresent() || !authorization(credential.get())){
            responseForbid(request, response);
            return  false;
        }

        CredentialContextUtils.setCredential(credential.get());
        return true;
    }

    private Optional<String> getToken(HttpServletRequest request){
        Optional<String> optional = Optional.ofNullable(request.getHeader("token"));
        if(optional.isPresent()){
            return optional;
        }
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(e -> StringUtils.removeStart(e, "Bearer"))
                .map(StringUtils::trim);
    }

    private Optional<Credential> validateAndGet(String token){
        return Optional.ofNullable(service.validateAndGet(token));
    }

    protected boolean authorization(Credential credential){
        return true;
    }

    /**
     * 输出禁止访问信息到客户端
     *
     * @param request     {@link HttpServletResponse}
     * @param response    {@link HttpServletResponse}
     * @throws IOException
     */
    private void responseForbid(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().append("{\"code\":410, \"messages\":[\"access forbid\"]}");
        response.getWriter().flush();
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        CredentialContextUtils.setCredential(null);
    }
}
