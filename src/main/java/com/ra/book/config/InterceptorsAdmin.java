package com.ra.book.config;

import com.ra.book.model.entity.User;
import com.ra.book.model.entity.constants.RoleName;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InterceptorsAdmin implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        User userLogin = (User) request.getSession().getAttribute("userLogin");
        HttpSession session = request.getSession();
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin != null) {
            if (userLogin.getRoles().stream().anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN))) {
                return true;
            }
            else {
                response.sendRedirect("/403");
                return false;
            }
        } else {
            response.sendRedirect("/login");
            return false;
        }
    }
}
