package com.mindhub.homebaking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()//autoriza peticiones

                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers("/api/transaction/pdf").permitAll()
                .antMatchers(HttpMethod.POST,"/api/loans/admin").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts","/api/clients/current/cards","/api/transactions","/api/loans", "/api/clients/current/payment","/api/transactions/pdf").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/cards/{id}","/api/clients/current/account/{id}").hasAuthority("CLIENT")
                .antMatchers("/api/transactions/pdf").hasAuthority("CLIENT")
                .antMatchers("/manager.html","/web/manager.js","/web/style.css","/rest/**","/h2-console","/api/clients","/newLoanAdmin.html").hasAuthority("ADMIN")
                .antMatchers("/web/index.html","/web/index.css", "/web/img/**","/web/signIn.html","/web/signIn.css","/web/signIn.js","/web/registro.html","/web/registro.css","/web/registro.js").permitAll()
                .antMatchers("/account.html","/web/**","/api/clients/current","/api/clients/current/accounts", "/web/transfers.html", "/api/loans","/api/clients/current/cards").hasAuthority("CLIENT")

                .anyRequest().denyAll();





        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");


        http.csrf().disable();// desactivamos la comprovacion de token csrf

        http.headers().frameOptions().disable(); // para tener acceso al console/h2

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));// envia msj de error si no esta autenticado

        http.formLogin().successHandler((req, res, exc) -> clearAuthenticationAttributes(req));// si login exit limpiar las banderas de autenticacion

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));// si falla login envia una respuesta de error

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());// si el logout salio bien envia un msj exitoso

//        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendRedirect("/web/index.html"));

        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
