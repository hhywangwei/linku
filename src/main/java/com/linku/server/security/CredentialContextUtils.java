package com.linku.server.security;

import java.util.Optional;

/**
 * 运行上下文对象,传递用户认证信息
 *
 * @author WangWei
 */
public class CredentialContextUtils {
    private static final ThreadLocal<Credential> threadLocal = new ThreadLocal<>();

    public static void setCredential(Credential credential){
        threadLocal.set(credential);
    }

    public static Optional<Credential> getCredential(){
        return Optional.ofNullable(threadLocal.get());
    }

    public static boolean hasCredential(){
        return getCredential().isPresent();
    }
}
