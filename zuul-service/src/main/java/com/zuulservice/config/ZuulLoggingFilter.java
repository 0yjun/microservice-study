package com.zuulservice.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;

public class ZuulLoggingFilter extends ZuulFilter {
    org.slf4j.Logger logger = LoggerFactory.getLogger(ZuulLoggingFilter.class);

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        logger.info("*********************************** printion logs : ");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("*********************************** "+ request.getRequestURI());
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
