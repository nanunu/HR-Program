<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.*" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header.jsp" %>
	<title>초과/휴가 신청란</title>
    <script type="text/javascript" src="${path}/resources/JSfile/Confirm_form.js?ver=<%=System.currentTimeMillis() %>"></script>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Confirm_form.css" />
</head>
<body>
	<%	
		if(request.getAttribute("message")!=null){
	%>
			<script>alert('<%=request.getAttribute("message")%>');</script>
	<%
		}
	%>

	<%@ include file="/main-container.jsp" %>
	            <!-- content 내용 출력구분.  -->

            <div class="form-box">
                <form action="#" name="form_data" method="post" class="box-control">
                    <div class="read-only">
                        <div class="title">
                            <label>부서명</label>
                            <label>직급<label>                                      
                            <label>사원번호</label>
                            <label>사원명</label>                                
                        </div>
                        <div class="content">
                            <input type="text" class="form-control update-not" name="Dname" value="<%=dname %>" readonly />
                            <input type="text" class="form-control update-not" name="position" value="<%=position %>" readonly />
                            <input type="text" class="form-control update-not" name="Ecode" value="<%=ecode %>" readonly />
                            <input type="text" class="form-control update-not" name="Ename" value="<%=ename %>" readonly />                                
                        </div>                          
                    </div>
                    <div class="confirm_class">
                        <div class="title">
                            <label>근무신청유형</label>
                            <label>근무코드</label>
                        </div>
                        <div>                                
                            <select name="work" id = "confirm_select" class="form-select first-select" onchange="Confirm_select(this)">                                                       
                                <option value="OverTime">초과근무신청</option>                                
                                <option value="HoliRecord">휴가근무신청</option>                                                             
                            </select>                                                         
                            <select name="Hcode" id = "Hcode_select" class="form-select second-select" disabled >                                                                                  
                                <option value="H0001">연차</option>
                                <option value="H0002">반차</option> 
                                <option value="H0003">외출</option> 
                                <option value="H0004">지참</option> 
                                <option value="H0005">조퇴</option>
                                <option value="H1001">본인결혼</option>
                                <option value="H1002">조사</option>                             
                                <option selected></option>  
                            </select>
                        </div>
                    </div>
                    <div class="confirm_date">
                        <div class="title">
                            <label>신청일자</label>                                
                        </div>                       
                       	<div class="flex-input content">                                
                        	<input type="date" class="form-control" name="startday" id="startday" value="<%= LocalDate.now() %>" onchange="Confirm_startday(this)" />
                            <span style="margin:0 5px;line-height:20px;">~</span>
                            <input type="date" class="form-control" name="endday" id="endday" />
                        </div>                            
                        <div class="title">
                            <label>적용시간</label>                                
                        </div>
                        <div class="flex-input content">
                        	<input type="number" name="starttime" id="starttime" class="form-control" /> ~ <input type="number" name="endtime" class="form-control" />
                        </div>
                        <div class="title">
                            <label>신청사유</label>                                
                        </div>
                        <div>
                            <textarea class="form-control" col="20" name="Reason">

                            </textarea>
                        </div>
                    </div>
                    <div class="btn-align">
                        <input type="reset" class="btn btn-danger" />
                        <input type="button" class="btn btn-primary" onclick ="Confirm_Checking_ver1()" value="결제승인요청" />                            
                    </div>
                </form>
            </div>
	<%@ include file="/footer.jsp" %>
</body>
</html>