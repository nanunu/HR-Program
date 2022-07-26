<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header.jsp" %>
 	<title>탄력근무제 조회 / 출력</title>
    <script type="text/javascript" src="${path}/resources/JSfile/Description.js?ver=<%=System.currentTimeMillis()%>"></script>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Free_work.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
		            <!-- content 내용 출력구분.  -->
            <div class="nav-giude">근태관리 > 탄력근무제 내역 및 신청</div>
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
                            <span class="classfy-title">날짜별</span>
                            <input type="date" name="date" class="form-control-sm">
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
                        <div class="table-title class-width">부서명</div>
                        <div class="table-title number-width">사원번호</div>                        
                        <div class="table-title name-width">사원명</div>
                        <div class="table-title rank-width">직급</div>
                        <div class="table-title date-width">신청일자</div>
                        <div class="table-title confirmday-width">승인일자</div>
                        <div class="table-title confirm-width">승인여부</div>
                        <div class="table-title etc">상세내역</div>
                    </div>

                    <div class="row">                
                        <div class="content-text">경영(인사)부</div>
                        <div class="content-text">220101A001B</div>
                        <div class="content-text">김현일</div>
                        <div class="content-text">팀장</div>
                        <div class="content-text">2022.07.18 ~ 2022.07.22</div>                
                        <div class="content-text">2022.07.17</div>
                        <div class="content-text">승인완료</div>
                        <!--사원번호 들고 이동함-->
                        <div class="content-text" onclick="description('220101A001B','free')">상세내역보기</div>
                    </div>

                    <div class="row">                
                        <div class="content-text">경영(인사)부</div>
                        <div class="content-text">220523A001C</div>
                        <div class="content-text">박민후</div>
                        <div class="content-text">사원</div>
                        <div class="content-text">2022.07.25 ~ 2022.07.29</div>                
                        <div class="content-text"></div>
                        <div class="content-text">대기중</div>
                        <div class="content-text" onclick="description('220101A001B','free')">상세내역보기</div>
                    </div>

                    <div class="row">                
                        <div class="content-text">개발부</div>
                        <div class="content-text">220101B001B</div>
                        <div class="content-text">이창기</div>
                        <div class="content-text">팀장</div>
                        <div class="content-text">2022.07.25 ~ 2022.07.29</div>                
                        <div class="content-text">2022.07.19</div>
                        <div class="content-text">승인완료</div>
                        <div class="content-text" onclick="description('220101A001B','free')">상세내역보기</div>                           
                    </div>

                    <div class="row">                
                        <div class="content-text">개발부</div>
                        <div class="content-text">220523B001C</div>
                        <div class="content-text">김민호</div>
                        <div class="content-text">사원</div>
                        <div class="content-text">2022.07.25 ~ 2022.07.29</div>                
                        <div class="content-text">2022.07.19</div>
                        <div class="content-text">승인거부</div>
                        <div class="content-text" onclick="description('220101A001B','free')">상세내역보기</div>                       
                    </div>

                </div><!-- container end -->

            </div><!-- view-container end -->
	<%@ include file="/footer.jsp" %>
</body>
</html>