package com.arc.zero.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author may
 * @since 2020/1/5 22:27
 */
public class SecurityBrowserConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests().anyRequest().authenticated();

    }
}

