<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<h3>플레이어 목록</h3>
<table>
<thead>
    <tr>
        <th>이름</th>
        <th>마지막 답변시간</th>
        <th>마지막 접속일</th>
    </tr>
</thead>
<tbody>
<c:forEach items="${players}" var="player">
    <tr>
        <td>${player.name}</td>
        <td><sf:eval expression="player.lastAnswerDateTime"/></td>
        <td><sf:eval expression="player.lastConnectionDate"/></td>
    </tr>
</c:forEach>
</tbody>
</table>
<hr/>
<sf:eval expression="@environment.getProperty('configType')" var="configType"/>
<c:if test="${configType eq 'Java'}">
<h3>App Environment</h3>
<ul>
    <li>AppName : <sf:eval expression="@environment.getProperty('appName')"/></li>
    <li>AppVersion : <sf:eval expression="@environment.getProperty('appVersion')"/></li>
</ul>
<hr/>
</c:if>
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