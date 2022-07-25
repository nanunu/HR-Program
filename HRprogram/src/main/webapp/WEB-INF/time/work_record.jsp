<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header.jsp" %>
 	<title>본인 근무 기록조회</title>        
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Work_record.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
	                <!-- content 내용 출력구분.  -->
                <div class="nav-giude">근태관리 > 근무기록 조회</div>
                <div class="filter-form">
                    <form action="#" class="form-control align">
                        <div class="form-box">
                            <label class="classfy">
                                <span class="classfy-title">부서명</span>
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
                                <span class="classfy-title">사원번호 혹은 사원명</span>
                                <input type="text" name="number" />
                            </label>
                            <input type="button" class="btn-white search-btn" value="조회하기"/>
                        </div>
                    </form>
                </div>
                <div class="view-container">                    
                    <div class="container">
                        <div class="row">
                            <div class="table-title">부서명</div>
                            <div class="table-title">사원번호</div>                        
                            <div class="table-title">사원명</div>
                            <div class="table-title">직급</div>
                            <div class="table-title">출근날짜</div>                
                            <div class="table-title">출근시간</div>                
                            <div class="table-title">퇴근시간</div>                
                            <div class="table-title">시간외근무시간</div>                                                        
                            <div class="table-title etc">비고</div>
                        </div>
                                        
                        <div class="row">                
                            <div class="content-text"></div>
                            <div class="content-text">CEO_A</div>
                            <div class="content-text">최여진</div>
                            <div class="content-text">대표이사</div>
                            <div class="content-text">20</div>                
                            <div class="content-text">0</div>
                            <div class="content-text">100</div>
                            <div class="content-text">F0002</div>
                            <div class="content-text"></div>
                        </div>

                        <div class="row">                
                            <div class="content-text"></div>
                            <div class="content-text">CEO_B</div>
                            <div class="content-text">김은아</div>
                            <div class="content-text">대표이사</div>
                            <div class="content-text">20</div>                
                            <div class="content-text">0</div>
                            <div class="content-text">100</div>
                            <div class="content-text">F0002</div>
                            <div class="content-text"></div>
                        </div>

                        <div class="row">                
                            <div class="content-text">경영(인사)부</div>
                            <div class="content-text">220101A001B</div>
                            <div class="content-text">김현일</div>
                            <div class="content-text">팀장</div>
                            <div class="content-text">10</div>                
                            <div class="content-text">0</div>
                            <div class="content-text">0</div>
                            <div class="content-text">F0002</div>
                            <div class="content-text"></div>
                        </div>

                        <div class="row">                
                            <div class="content-text">경영(인사)부</div>
                            <div class="content-text">220523A001C</div>
                            <div class="content-text">박민후</div>
                            <div class="content-text">사원</div>
                            <div class="content-text">10</div>                
                            <div class="content-text">8</div>
                            <div class="content-text">7</div>
                            <div class="content-text">F0002</div>
                            <div class="content-text"></div>
                        </div>

                        <div class="row">                
                            <div class="content-text">개발부</div>
                            <div class="content-text">220101B001B</div>
                            <div class="content-text">이창기</div>
                            <div class="content-text">팀장</div>
                            <div class="content-text">15</div>                
                            <div class="content-text">12</div>
                            <div class="content-text">0</div>
                            <div class="content-text">F0002</div>
                            <div class="content-text"></div>
                        </div>

                        <div class="row">                
                            <div class="content-text">개발부</div>
                            <div class="content-text">220523B001C</div>
                            <div class="content-text">김민호</div>
                            <div class="content-text">사원</div>
                            <div class="content-text">12</div>                
                            <div class="content-text">8</div>
                            <div class="content-text">2</div>
                            <div class="content-text">F0002</div>
                            <div class="content-text"></div>
                        </div>

                    </div><!-- container end -->

                </div><!-- view-container end -->
	<%@ include file="/footer.jsp" %>
</body>
</html>