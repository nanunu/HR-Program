<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header.jsp" %>
	<title>초과/휴가 신청란</title>
    <script type="text/javascript" src="${path}/resources/JSfile/Confirm_form.js"></script>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Confirm_form.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
	            <!-- content 내용 출력구분.  -->

            <div class="form-box">
                <form action="#" name="form-data" method="post" class="box-control">
                    <div class="read-only">
                        <div class="title">
                            <label>부서명</label>
                            <label>직급<label>                                      
                            <label>사원번호</label>
                            <label>사원명</label>                                
                        </div>
                        <div class="content">
                            <input type="text" class="form-control update-not" name="class" value="경영(인사)부" readonly />
                            <input type="text" class="form-control update-not" name="rank" value="팀장" readonly />
                            <input type="text" class="form-control update-not" name="number" value="220101A001B" readonly />
                            <input type="text" class="form-control update-not" name="name" value="김현일" readonly />                                
                        </div>                            
                    </div>
                    <div class="confirm_class">
                        <div class="title">
                            <label>근무신청유형</label>
                            <label>근무코드</label>
                        </div>
                        <div>                                
                            <select name="work" id = "confirm_select" class="form-select first-select" onchange="Confirm_select(this)">                                                       
                                <option value="over">초과근무신청</option>                                
                                <option value="off">휴가근무신청</option>                                                             
                            </select>                                                         
                            <select name="code" id = "code_select" class="form-select second-select" disabled>                                                       
                                <option value="">반차</option>
                                <option value="">외출</option>
                                <option value="">조퇴</option>
                                <option value="">연차</option>
                                <option value="">경조사</option>
                                <option value="">결혼</option>                                    
                                <option value="not" selected></option>  
                            </select>
                        </div>
                    </div>
                    <div class="confirm_date">
                        <div class="title">
                            <label>신청일자</label>                                
                        </div>
                        <div class="tmp">
                            <div class="flex-input content">                                
                                <input type="date" class="form-control" name="startday" id="startday" onchange="Confirm_day(this)" />
                                <span style="margin:0 5px;line-height:20px;">~</span>
                                <input type="date" class="form-control" name="endday" id="endday" />                                
                                <input type="text" name="over_time" id="over_time" class="form-control input-type" onfocus="over_time_checking()" /><span style="margin:0 5px;line-height:20px;">시간</span>
                            </div>                            
                        </div>
                        <div class="title">
                            <label>신청사유</label>                                
                        </div>
                        <div>
                            <textarea class="form-control" col="20">

                            </textarea>
                        </div>
                    </div>
                    <div class="btn-align">
                        <input type="reset" class="btn btn-danger" />
                        <input type="button" class="btn btn-primary" onclick ="Confirm_Checking()" value="결제승인요청" />                            
                    </div>
                </form>
            </div>
	<%@ include file="/footer.jsp" %>
</body>
</html>