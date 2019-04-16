package net.octacomm.sample.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.exceptions.BaseException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = BaseException.class)
	public String handleBaseException(BaseException e) {
		System.err.println("handleBaseException :  ");
		return e.getMessage();
	}

	/*
	 * @ExceptionHandler(Exception.class) protected ModelAndView
	 * handleUnauthorizedException(HttpServletRequest request, HttpServletResponse
	 * response, Object handler, Exception ex) {
	 * 
	 * // 응답헤더에 WWW-Authenticate를 추가 response.setHeader("WWW-Authenticate",
	 * "Basic realm=\"Access to user information\"");
	 * 
	 * // ResponseStatusExceptionResolver를 통해 exception을 응답 모델로 변경 return new
	 * ResponseStatusExceptionResolver().resolveException(request, response,
	 * handler, ex); }
	 */

	/*
	 * @ExceptionHandler(Exception.class) public void
	 * handleBadRequests(HttpServletResponse response) throws Exception {
	 * response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with
	 * // a non empty string as 'name'"); System.err.println(response.getStatus());
	 * StringBuilder sb = new
	 * StringBuilder("{ \n").append("    \"timestamp g\": ").append("\"")
	 * .append(DateTime.now().toString()).append("\" \n").append("    \"status\": ")
	 * .append(resp.getStatus())
	 * .append(" \n").append("    \"error\": ").append("\"")
	 * .append(HttpStatus.valueOf(resp.getStatus()).name()).append("\" \n").
	 * append("    \"exception\": ")
	 * .append("\"").append(e.getClass().toString().substring(6)).append("\" \n").
	 * append("    \"message\": ")
	 * .append("\"").append(e.getMessage()).append("\" \n").append("    \"path\": ")
	 * .append("\"") .append(req.getServletPath()).append("\" \n").append("}");
	 * 
	 * String errorMessage = String.format(sb.toString());
	 * 
	 * }
	 */

	/*@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = Exception.class)
	public CommonResponce<String> handleException(HttpServletRequest req, HttpServletResponse resp, Exception e)
			throws Exception {
		CommonResponce<String> responce = new CommonResponce<String>();
		responce.setDomain(null);
		responce.setException(e.getClass().toString().substring(6));
		if (e.getClass().toString().substring(6).equals("org.springframework.jdbc.BadSqlGrammarException")) {
			responce.setStatus(HodooConstant.SERVER_ERROR);
		} else if (e.getClass().toString().substring(6).equals("org.springframework.web.util.NestedServletException")) {
			responce.setStatus(HodooConstant.SERVER_ERROR);
		} else if (e.getClass().toString().substring(6)
				.equals("org.springframework.web.client.HttpServerErrorException")) {
			responce.setStatus(HodooConstant.SERVER_ERROR);
		} else if (e.getClass().toString().substring(6).equals("java.lang.NullPointerException")) {
			responce.setStatus(HodooConstant.SERVER_ERROR);
		} else if (e.getClass().toString().substring(6).equals("org.mybatis.spring.MyBatisSystemException")) {
			responce.setStatus(HodooConstant.SERVER_ERROR);
		} else {
			responce.setStatus(HodooConstant.BAD_REQUEST);
		}
		
		 * responce.setMessage(e.getMessage()); responce.setError("FAILED");
		 * responce.setPath(req.getServletPath()); StringBuilder sb = new
		 * StringBuilder("{ \n").append("    \"timestamp g\": ").append("\"")
		 * .append(DateTime.now().toString()).append("\" \n").append("    \"status\": ")
		 * .append(resp.getStatus())
		 * .append(" \n").append("    \"error\": ").append("\"")
		 * .append(HttpStatus.valueOf(resp.getStatus()).name()).append("\" \n").
		 * append("    \"exception\": ")
		 * .append("\"").append(e.getClass().toString().substring(6)).append("\" \n").
		 * append("    \"message\": ")
		 * .append("\"").append(e.getMessage()).append("\" \n").append("    \"path\": ")
		 * .append("\"") .append(req.getServletPath()).append("\" \n").append("}");
		 * String errorMessage = String.format(sb.toString());
		 
		return responce;
	}*/

}
