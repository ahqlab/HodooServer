<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#content .question').click(function() {
			$('#content .question').removeClass('close'); // as well as remove the 'expanded' icon
			$('#content .answer').slideUp();
			$(this).next('.answer').slideDown(); //slidedown the appropriate div
			$(this).addClass('close'); //as well as insert the 'expanded' icon 
		});
	});
</script>
<style type="text/css">
.answer {
	display: none;
}
</style>
<title>Insert title here</title>
</head>
<body>
	<div id="content">
		<div id="col_1">
			<div class="question">
				<div align="justify">Q: This is question 1.</div>
			</div>
			<div class="answer" style="width: 360px;">
				<div align="justify">A: This answer is the answer to question
					1.</div>
			</div>
			<br> <br>
			<div class="question">Q: This is question 2.</div>
			<div class="answer" style="width: 360px;">
				<div align="justify">A: This answer is the answer to question
					2.</div>
			</div>
			<br> <br>
			<div class="question">
				<div align="justify">Q: This is question 3.</div>
			</div>
			<div class="answer" style="width: 360px;">
				<div align="justify">A: This answer is the answer to question
					3.</div>
			</div>
		</div>
	</div>
</body>
</html>