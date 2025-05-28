package ru.testtask.webapp.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class InitDataAuthenticationToken  extends AbstractAuthenticationToken {

    private final String initData;

    public InitDataAuthenticationToken(String initData) {
        super(null);
        this.initData = initData;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return initData;
    }

    @Override
    public Object getPrincipal() {
        return initData;
    }
}
