package ie.book.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import ie.book.service.JwtService;
import ie.book.service.UserService;

public class JwtAuthFilter extends OncePerRequestFilter{

	private JwtService jwtService;
	private UserService userService;
	
	public JwtAuthFilter(JwtService jwtService, UserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		
		String authorization = httpServletRequest.getHeader("Authorization");
		
		if(authorization != null && authorization.startsWith("Bearer")){
			String token = authorization.split(" ")[1];
			boolean isValid = jwtService.tokenValid(token);
			
			if(isValid) {
				String emailUser = jwtService.getEmailUser(token);
				UserDetails user = userService.loadUserByUsername(emailUser);
				UsernamePasswordAuthenticationToken userAuthentication = new
						UsernamePasswordAuthenticationToken(user, null,
								user.getAuthorities());
				userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(userAuthentication);
			}
		}
		
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
}
