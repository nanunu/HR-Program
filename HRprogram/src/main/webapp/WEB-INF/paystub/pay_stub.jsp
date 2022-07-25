<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header.jsp" %>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>급여명세서</title>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Pay_stub.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
		            <!-- content 내용 출력구분.  -->
            <div class="pay-container pad-height">
                <div class="pre">
                    <a href="/html/money/pay_stub.html?month=6" class="link-pre"><i class="fa-solid fa-arrow-right"></i></a>
                    <a href="/html/money/pay_stub.html?month=6" class="link-pre">6월 급여명세서</a>                                        
                </div>
                <div class="next">
                    <a href="/html/money/pay_stub.html?month=8" class="link-next">8월 급여명세서</a>
                    <a href="/html/money/pay_stub.html?month=8" class="link-next"><i class="fa-solid fa-arrow-right"></i></a> 
                </div>
            </div>
            <div class="pay-container">
                <div class="giude-title head">급여명세서</div>
                <div class="info-box" >
                    <div class="staff-info">
                        <div class="info-title">지급월</div><div class="info-content">2022년 7월</div>
                        <div class="info-title">부서명</div><div class="info-content">CEO</div>
                        <div class="info-title">직위</div><div class="info-content">이사</div>
                    </div>
                    <div class="staff-info">
                        <div class="info-title">성명</div><div class="info-content">김은아</div>
                        <div class="info-title">생년월일</div><div class="info-content">1994-01-03</div>
                        <div class="info-title">사원번호</div><div class="info-content">220101A001A</div>
                    </div>
                </div>
                <div class="giude-title">■ 지급 및 공제내역</div>
                    <div class="money-box">
                        <div class="money-box-p">
                            <div> 지 급 항 목 </div>                    
                            <div class="default">기 본 급</div><div class="money">원</div>
                            <div class="default">식    대</div><div class="money">원</div>
                            <div class="default">차량유지비</div><div class="money">원</div>
                            <div class="default">초과근로수당</div><div class="money">원</div>                            
                            <div class="default">합계</div><div class="money">원</div>                        
                        </div>
                        <div class="money-box-n">
                            <div> 공 제 내 역 </div>                    
                            <div class="default">국민연금</div><div class="money">원</div>
                            <div class="default">건강보험</div><div class="money">원</div>
                            <div class="default">장기요양보험</div><div class="money">원</div>
                            <div class="default">고용보험</div><div class="money">원</div>                            
                            <div class="default">합계</div><div class="money">원</div>                        
                        </div>
                        <div class="default">실수령액</div><div class="money total">원</div>
                    </div>
                </div>                
                <div class="pay-sign">
                    <p class="thanks">귀하의 노고에 감사드립니다.</p>
                    <p class="date">20<span class="nbsp"></span>년<span class="nbsp"></span>월<span class="nbsp"></span>일</p>
                    <p class="CEOsign">대표이사 최00 (인)</p>
                </div>
            <div><!-- pay-container end-->
	<%@ include file="/footer.jsp" %>
</body>
</html>