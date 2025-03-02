package systems.monitor.gateway.filters;

import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

// This class is a Zuul pre-filter responsible for implementing the rate limiter.
// It applies rate limiting to prevent abuse of the service.
// @author Antonio Scardace
// @version 1.0

@Log
@NoArgsConstructor
@Component
public class RateLimiterFilter extends ZuulFilter {

    private static RateLimiter rateLimiter = RateLimiter.create(10);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();

        if (!rateLimiter.tryAcquire(1)) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(429);
            context.setResponseBody("Rate limit exceeded.");
            log.warning("Rate limit exceeded.");
        }
        
        return null;
    }
}
