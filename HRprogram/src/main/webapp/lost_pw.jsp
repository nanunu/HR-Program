<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>비밀번호 찾기</title>
    <link rel="stylesheet" href="/resources/CSSfile/Lost.css" />
    <script src="https://kit.fontawesome.com/ef95c4be0b.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="/resources/JSfile/Lost.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
        <div class="container lost">
            <form action="#" method="post" name="lostpw">
                <label id="giude_text">이메일을 입력해주세요.</label>
                <input type="email" name="email" id="email" class="form-control input" placeholder="Email 주소를 입력하세요." />
                <input type="button" class="btn-danger col-12" value="비밀번호 찾기" onclick="lost_check()" />
            </form>
        </div>
</body>
</html>