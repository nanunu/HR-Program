<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>급여명세서</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/ef95c4be0b.js" crossorigin="anonymous"></script>    
    <link rel="stylesheet"  href="/resources/CSSfile/Index.css" /><!-- 필수 CSS -->
    <link rel="stylesheet"  href="/resources/CSSfile/Pay_stub.css" />
    <script src="/resources/JSfile/Staff_pay_stub.js"></script>  
</head>
<body style="width:900px;padding:0px 70px;overflow:hidden;">
    <!-- main view -->
    <div class="right-container" style="width:100%;">
        <!-- content 내용 출력구분.  -->
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
                <div class="btn-box">
                    <input type="button" class="btn btn-warning seccess" onclick="admission()" value="승인완료" /> <input type="button" value="정산하기" onclick="pay_cal('사원번호')" class="btn btn-dark caculator" />
                </div>
            </div>
        <div><!-- pay-container end-->
    </div><!-- right-container end -->
</body>
</html>