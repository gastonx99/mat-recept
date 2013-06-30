package se.dandel.recipe.web.infra;

import java.io.IOException;
import java.util.logging.Logger;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthenticationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    private static final String LOGGED_ON_AKTOR = "LOGGED_ON_AKTOR";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        try {
            if (!isResourceRequest(request)) {
                filter(request, response);
            }
            chain.doFilter(request, response);
        } finally {
            AktorManager.destroy();
        }
    }

    private boolean isResourceRequest(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getRequestURI().contains(ResourceHandler.RESOURCE_IDENTIFIER);
    }

    private void filter(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        Aktor aktor = (Aktor) session.getAttribute(LOGGED_ON_AKTOR);
        if (aktor == null) {
            logger.fine("Aktor not in session. Retrieving from user service");
            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            if (user != null) {
                String email = user.getEmail();
                String nickname = user.getNickname();
                String userId = user.getUserId();
                aktor = new Aktor(userId, nickname, email);
                session.setAttribute(LOGGED_ON_AKTOR, aktor);
                logger.fine("Setting aktor in session");
            }
        }

        if (aktor != null) {
            logger.fine("Setting aktor in thread");
            AktorManager.initialize(aktor);
        } else {
            UserService userService = UserServiceFactory.getUserService();
            HttpServletResponse resp = (HttpServletResponse) response;
            String url = userService.createLoginURL(req.getRequestURI());
            logger.fine("No aktor could be found. Redirecting to " + url);
            resp.sendRedirect(url);
        }
    }

    @Override
    public void destroy() {
    }

}
