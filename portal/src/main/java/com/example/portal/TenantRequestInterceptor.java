package com.example.portal;

import com.example.portal.scheduler.jobs.Message;
import com.example.portal.scheduler.jobs.ServiceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TenantRequestInterceptor implements AsyncHandlerInterceptor {

    public TenantRequestInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        TenantContext.setCurrentTenant(request.getHeader("x-tenant"));

        ServiceContext context = new ServiceContext();
        context.setCurrentTenant(request.getHeader("x-tenant"));
        context.setCurrentUserId(request.getHeader(Message.USER_ID_HEADER));

        ServiceContext.install(new ServiceContext());


        ServiceContext.get().w

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}
