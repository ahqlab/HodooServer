package net.octacomm.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({ "rest/test/" })
public class ExTestController {

	@RequestMapping(value = "/ex1", method = RequestMethod.POST)
	public String ex1(@RequestParam("date") String date, HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception {
		
		StringBuilder sb = new StringBuilder("{ \n")
	            .append("    \"timestamp g\": ").append("\"").append(DateTime.now().toString()).append("\" \n")
	            .append("    \"status\": ").append(resp.getStatus()).append(" \n")
	            .append("    \"error\": ").append("\"").append(HttpStatus.valueOf(resp.getStatus()).name()).append("\" \n")
	            .append("    \"exception\": ").append("\"").append(e.getClass().toString().substring(6)).append("\" \n")
	            .append("    \"message\": ").append("\"").append(e.getMessage()).append("\" \n")
	            .append("    \"path\": ").append("\"").append(req.getServletPath()).append("\" \n")
	            .append("}");

	    String errorMessage = String.format(sb.toString());
	    System.err.println("errorMessage : >> " + errorMessage);
		return null;
	}

	@RequestMapping("/ex4")
	public String ex4() {
		// 전역 처리자 메소드 handleBaseException에 잡힐 것이다.
		throw new NullPointerException("null pointer exception");
	}

	@RequestMapping("/ex5")
	public String ex5() {
		// 컨트롤러 예외 처리자 메소드 nfeHandler에 잡힐 것이다.
		throw new NumberFormatException("number format exception");
	}

	/**
	 * 이 컨트롤러 내에서 발생하는 모든 Number Format 예외를 처리한다 *
	 */
	@ExceptionHandler(value = NumberFormatException.class)
	public String nfeHandler(NumberFormatException e) {
		return e.getMessage();
	}

}
