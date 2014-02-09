<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="sf" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Overview of Spring 4.0 :: OXQuiz</title>
    <style type="text/css">
        li.question {
            cursor: pointer;
        }
    </style>
</head>
<body>
<h1>사회자 페이지</h1>
<hr/>
<h3>문제 목록</h3>
<ul>
    <li class="question">1. 문제</li>
    <li class="question">2. 문제</li>
    <li class="question">3. 문제</li>
</ul>
<hr/>
출제할 문제 : <input id="txtQuestion" type="text" size="100"/> <button id="btnSend">문제 보내기</button>
<hr/>
<button id="btnClose">문제 종료</button>
<hr/>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
    (function() {
        $('li.question').click(function(e) {
            $('#txtQuestion').val($(e.target).html());
        });
        $('#btnSend').click(function() {
            $.post("/emcee/question/send", { question: $('#txtQuestion').val() });
        });
        $('#btnClose').click(function() {
            $.post("/emcee/question/close");
        });
    })();
</script>

</body>
</html>