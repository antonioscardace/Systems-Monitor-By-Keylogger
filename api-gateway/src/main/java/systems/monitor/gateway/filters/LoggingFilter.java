package systems.monitor.gateway.filters;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.java.Log;

// This class is a Zuul pre-filter responsible for logging incoming requests.
// It logs the method and URI of each incoming request.
// This filter receives incoming requests and passes them to other Filters.
// @author Antonio Scardace
// @version 1.0

@Log
@Component
public class LoggingFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String method = context.getRequest().getMethod();
        String uri = context.getRequest().getRequestURI();

        log.info("Incoming request: " + method + " " + uri);
        return null;
    }
}