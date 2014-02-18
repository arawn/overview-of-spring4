<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Overview of Spring 4.0 :: OXQuiz</title>
    <style type="text/css">
        .question {
            font-size: 1.5em;
            cursor: pointer;
        }

        .question:hover,
        .question.selected {
            background-color: #CCCCCC;
        }

        .question.used {
            background-color: #B3B3B3;
        }
    </style>
</head>
<body>
<h1>사회자 페이지</h1>
<hr/>
<h3>문제 목록</h3>
<ul>
    <li class="question">스프링을 누가 만든지 알고 있다.</li>
    <li class="question">웹소켓을 써본적이 있다.</li>
    <li class="question">유겐휠러가 누군지 안다.</li>
    <li class="question">스프링 3.2 이상을 쓰고 있다.</li>
    <li class="question">코멧이 뭔지 알고 있다.</li>
    <li class="question">STOMP라는 프로토콜에 대해 들어봤다.</li>
    <li class="question">SockJS라는 라이브러리를 들어봤다.</li>
</ul>
<hr/>
출제할 문제 : <input id="txtQuestion" type="text" size="100"/>
<button id="btnSend">문제 보내기</button>
<hr/>
<button id="btnClose">문제 종료</button>
<script src="/resources/components/jquery/jquery.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var current$, txtQuestion$ = $('#txtQuestion'), questions$ = $('.question');

        questions$.click(function (e) {
            questions$.removeClass('selected');
            current$ = $(this).addClass('selected');
            txtQuestion$.val($(this).html());
        });

        $('#btnSend').click(function () {
            if (current$.html() === txtQuestion$.val()) {
                current$.addClass('used');
            }
            $.post("/question/send", { question: txtQuestion$.val() }, function() {
                txtQuestion$.val('');
            });
        });

        $('#btnClose').click(function () {
            $.post("/question/close");
        });
    });
</script>
</body>
</html>