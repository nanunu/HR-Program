<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header.jsp" %>
    <title>급여관리</title>
    <script type="text/javascript" src="${path}/resources/JSfile/Staff_pay_stub.js"></script>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Free_work.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
	            <!-- content 내용 출력구분.  -->
            <div class="nav-giude">급여관리 > 사원급여정산 </div>
            <div class="filter-form">
                <form action="#" class="form-control align">
                    <div class="form-box">
                        <label class="classfy">
                            <span class="classfy-title">부서별</span>
                            <select name="class" class="form-select-sm">
                                <option value="all">전체</option>
                                <option value="business">경영(인사)부</option>
                                <option value="development">개발부</option>
                            </select>
                        </label>                            
                        <label class="classfy">
                            <span class="classfy-title">직급별</span>
                            <select name="rank" class="form-select-sm">
                                <option value="all">전체</option>
                                <option value="staff">사원</option>
                                <option value="general">팀장</option>
                                <option value="ceo">대표이사</option>
                            </select>
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">월별</span>
                            <select name="month" class="form-select-sm">
                                <option value="all">전체</option>
                                <option value="1">1월</option>
                                <option value="2">2월</option>
                                <option value="3">3월</option>
                                <option value="4">4월</option>
                                <option value="5">5월</option>
                                <option value="6">6월</option>
                                <option value="7">7월</option>
                                <option value="8">8월</option>
                                <option value="9">9월</option>
                                <option value="10">10월</option>
                                <option value="11">11월</option>
                                <option value="12">12월</option>
                            </select>
                        </label>
                        <input type="button" class="btn-white search-btn" value="조회하기"/>
                    </div>
                </form>
            </div>
            <div class="view-container">                    
                <div class="container">
                    <div class="row">
                        <div class="table-title select-box" style="cursor:pointer ;" onclick="checkbox_all()">전체선택</div>
                        <div class="table-title class-width">부서명</div>
                        <div class="table-title number-width">사원번호</div>                        
                        <div class="table-title name-width">사원명</div>
                        <div class="table-title rank-width">직급</div>
                        <div class="table-title confirm-width">발급여부</div>
                        <div class="table-title etc">명세서 확인</div>
                    </div>

                    <div class="row">
                        <div class="content-text"><input type="checkbox" name="staff_check" class="staff_list" value="사원번호"  /></div>
                        <div class="content-text">경영(인사)부</div>
                        <div class="content-text">220101A001B</div>
                        <div class="content-text">김현일</div>
                        <div class="content-text">팀장</div>
                        <div class="content-text">미발급</div>
                        <!--사원번호 들고 이동함-->
                        <div class="content-text" onclick="pay_stub_open('220101A001B')">명세서보기</div>
                    </div>

                    <div class="row">                
                        <div class="content-text"><input type="checkbox" name="staff_check" class="staff_list" value="사원번호"  /></div>
                        <div class="content-text">경영(인사)부</div>
                        <div class="content-text">220523A001C</div>
                        <div class="content-text">박민후</div>
                        <div class="content-text">사원</div>
                        <div class="content-text">미발급</div>
                        <div class="content-text" onclick="pay_stub_open('220101A001B')">명세서보기</div>
                    </div>

                    <div class="row">                
                        <div class="content-text"><input type="checkbox" name="staff_check" class="staff_list" value="사원번호"  /></div>
                        <div class="content-text">개발부</div>
                        <div class="content-text">220101B001B</div>
                        <div class="content-text">이창기</div>
                        <div class="content-text">팀장</div>                            
                        <div class="content-text">미발급</div>
                        <div class="content-text" onclick="pay_stub_open('220101A001B')">명세서보기</div>                           
                    </div>

                    <div class="row">                
                        <div class="content-text"><input type="checkbox" name="staff_check" class="staff_list" value="사원번호"  /></div>
                        <div class="content-text">개발부</div>
                        <div class="content-text">220523B001C</div>
                        <div class="content-text">김민호</div>
                        <div class="content-text">사원</div>
                        <div class="content-text">미발급</div>
                        <div class="content-text" onclick="pay_stub_open('220101A001B')">명세서보기</div>                       
                    </div>

                </div><!-- container end -->

            </div><!-- view-container end -->
	<%@ include file="/footer.jsp" %>
</body>
</html>