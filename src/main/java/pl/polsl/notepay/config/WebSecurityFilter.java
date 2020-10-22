package pl.polsl.notepay.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class WebSecurityFilter extends BasicAuthenticationFilter {

    public WebSecurityFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }


        UsernamePasswordAuthenticationToken authentication = getAuthentication(token.substring("Bearer ".length()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            // parse the token.
            String email = AuthenticationUtils.getSubjectFromToken(token);

            if (email != null) {
                return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
            }
            return null;
        }
        return null;
    }
}
