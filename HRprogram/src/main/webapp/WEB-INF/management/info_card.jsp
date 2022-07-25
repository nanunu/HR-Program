<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header.jsp" %>
	<title>인사기록카드</title>        
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Info_card.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
	            <!-- content 내용 출력구분.  -->
            <div class="container">
                <div class="sub-container">
                    <div class="head-info">
                        <div class="img">
                            <img src="/resources/image/staff/Sample_img.jpg" alt="사원이미지" class="profile_img" />
                        </div>
                        <div class="info">
                            <div class="class">김 ㅇㅇ</div>
                            <div class="name">개발부 / 대표이사</div>
                        </div>
                    </div>
                    <div class="main-info">
                        <h5>인사정보</h5>
                        <div class="HR-info">                                
                            <div class="info-box">
                                <div class="title">사원코드</div><div class="content">22012156Add1</div>
                            </div>
                            <div class="info-box">
                                <div class="title">입사일</div><div class="content">2022-07-02</div>
                            </div>
                        </div>
                        <h5>개인정보</h5>
                        <div class="HR-info">                                
                            <div class="info-box">
                                <div class="title">생년월일</div><div class="content">1994-01-03</div>
                            </div>
                            <div class="info-box">
                                <div class="title">연락처</div><div class="content">010-2123-1231</div>
                            </div>
                            <div class="info-box">
                                <div class="title">주소</div><div class="content">창원시 마산합포구 가포순환로 33.</div>
                            </div>
                            <div class="info-box">
                                <div class="title">은행</div><div class="content">신한</div>
                            </div>
                            <div class="info-box">
                                <div class="title">계좌번호</div><div class="content">110-480-050656</div>                                
                            </div>                                
                        </div>
                        <h5>경력/자격증</h5>
                        <div class="HR-info">                                
                            <div class="info-box">
                                <div class="title">최종학력</div><div class="content">고등학교 졸업</div>
                            </div>
                            <div class="info-box">
                                <div class="title">병역</div><div class="content">해당사항없음</div>
                            </div>
                            <div class="info-box">
                                <div class="title">면허자격</div><div class="content">해당사항없음</div>
                            </div>
                            <div class="info-box">
                                <div class="title">차량</div><div class="content"></div>
                            </div>
                        </div>                        
                    </div>
                </div><!-- sub-conrainer end-->
            </div><!--conrainer end-->
	<%@ include file="/footer.jsp" %>
</body>
</html>