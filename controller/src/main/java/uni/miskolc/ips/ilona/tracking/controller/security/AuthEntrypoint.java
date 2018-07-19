package uni.miskolc.ips.ilona.tracking.controller.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import uni.miskolc.ips.ilona.tracking.controller.model.MobilDummy;

public class AuthEntrypoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		System.out.println(exception.toString());
		/*
		if (exception instanceof ProviderNotFoundException) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		*/
		if(exception instanceof BadCredentialsException) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ObjectMapper mapper = new ObjectMapper();
			String text = mapper.writeValueAsString(new MobilDummy("BAD CREDENTIALS", 1.0));
			
			PrintWriter writer = response.getWriter();
			writer.write(text);
			return;
		}
		
		if(exception instanceof ProviderNotFoundException) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ObjectMapper mapper = new ObjectMapper();
			String text = mapper.writeValueAsString(new MobilDummy("NO SUCH USER!", 1.0));
			
			PrintWriter writer = response.getWriter();
			writer.write(text);
			return;
		}
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ObjectMapper mapper = new ObjectMapper();
		String text = mapper.writeValueAsString(new MobilDummy("SERVER ERROR!", 1.0));
		
		PrintWriter writer = response.getWriter();
		writer.write(text);
	}

}
