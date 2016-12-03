package uni.miskolc.ips.ilona.tracking.controller.security;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException ex) throws IOException, ServletException {
		System.out.println("CustomAuthenticationFailureHandler: " + ex.getClass().getName());
		
		response.setContentType("application/json; charset=UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		//PrintWriter out = new PrintWriter(
        //       new OutputStreamWriter(response.getOutputStream(), "UTF-8"), true);
		ExecutionResultDTO result = new ExecutionResultDTO(300, new ArrayList<String>());
		result.addMessage("ö ü ó ő ú é á ű");
		String json = mapper.writeValueAsString(result);
		System.out.println(json);
		writer.write(json);
		response.setStatus(response.SC_UNAUTHORIZED);
		// writer.write(mapper.writeValueAsString(result));
		// writer.flush();
		// writer.close();
	}

}
