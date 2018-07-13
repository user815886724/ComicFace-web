import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter{
    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    private String allowOrigin;
    private String allowMethods;
    private String allowCredentials;
    private String allowHeaders;
    private String exposeHeaders;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
    }

    /**
     * 通过CORS技术实现ajax跨域访问，只要将CORS响应头写入response对象即可
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String currentOrigin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin",currentOrigin);

        /**
         * Access-Control-Allow-Origin：允许访问的客户端域名，若为*，则表示从任意域都能访问即不做限制
         * Access-Control-Allow-Methods：允许访问的方法名，多个方法用逗号分隔，例如：GET，POST，PUT，DELETE，OPTIONS
         * Access-Control-Allow-Credentials：是否允许请求带有验证信息，若要获取客户端域下的cookie时，需要设置其为true
         * Access-Control-Allow-Headers：允许服务端访问的客户端请求头，多个请求用逗号分隔，例如：Content-Type
         * Access-Control-Expose-Headers：允许客户端访问的服务端响应头，多个响应头用逗号分隔
         */

        if(StringUtil.isNullOrEmpty(allowMethods)){
            response.setHeader("Access-Control-Allow-Methods",allowMethods);
        }
        if(StringUtil.isNullOrEmpty(allowCredentials)){
            response.setHeader("Access-Control-Allow-Credentials",allowCredentials);
        }
        if(StringUtil.isNullOrEmpty(allowHeaders)){
            response.setHeader("Access-Control-Allow-Headers",allowHeaders);
        }
        if(StringUtil.isNullOrEmpty(exposeHeaders)){
            response.setHeader("Access-Control-Expose-Headers",exposeHeaders);
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
