<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<c:set value="${pageContext.request.contextPath }" var="ctx"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HODOO - 이메일 인증</title>
</head>
<body>

<c:choose>
	<%-- <c:when test="${state == -2 }">
    	<script type="text/javascript">
    		alert("인증세션이 만료되었습니다.");
    		self.close();
    	</script>
    </c:when> --%>
    <c:when test="${state == -1 }">
    	<script type="text/javascript">
    		alert("인증을 이미 완료했습니다.");
    		self.close();
    	</script>
    </c:when>
    <c:when test="${state == 0 }">
    	<script type="text/javascript">
    		alert("서버에 문제가 발생 했습니다.\n관리자에게 연락바랍니다.");
    		self.close();
    	</script>
    </c:when>
    <c:otherwise>
    	<script type="text/javascript">
    		location.href = "${ctx}/user/welcomeSignup.do";
    	</script>
    </c:otherwise>
</c:choose>


</body>
</html>