<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	String msg = (String)request.getAttribute("msg"); 
	if(msg!=null){
%>
	<script>alert("<%=msg%>");</script>
<%
	}
%>
<head>
	<%@ include file="/header.jsp" %>
 	<title>탄력근무제 신청란</title>
    <script type="text/javascript" src="${path}/resources/JSfile/Confirm_form.js?ver=<%=System.currentTimeMillis()%>"></script>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Confirm_form.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
	            <!-- content 내용 출력구분.  -->

            <div class="form-box">
                <form action="flextime1.do" name="form_data" method="post" class="box-control">
                    <div class="read-only">
                        <div class="title">
                            <label>부서명</label>
                            <label>직급</label>                                      
                            <label>사원번호</label>
                            <label>사원명</label>                                
                        </div>
                        <div class="content">
                            <input type="text" class="form-control update-not" name="Dname" value="<%=dname%>" readonly />
                            <input type="text" class="form-control update-not" name="Pname" value="<%=pname%>" readonly />
                            <input type="text" class="form-control update-not" name="Ecode" value="<%=ecode%>" readonly />
                            <input type="text" class="form-control update-not" name="Ename" value="<%=ename%>" readonly />  
                        </div>                            
                    </div>
                    <div class="confirm_class">
                        <div class="title">
                            <label>근무신청유형</label>                            
                        </div>
                        <div>
                            <input type="text" name="work_type" class="form-control" value="탄력근무신청" readonly />
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
                                <input type="date" class="form-control" name="endday" id="endday"/>                                                                
                            </div>
                            <div class="flex-input content ">                                    
                                 <!-- 탄력근무신청 선택 > 신청일자(월요일)선택시 요일별 근무시간 설정하는 폼출력란. -->                                 
                                <div class="input-box" id="input-box">
                                    <div>
                                        <label class="weekday"></label><label class="worktime">근무시작시간</label><label class="worktime">근무종료시간</label>
                                    </div>
                                    <div>
                                        <label class="weekday">월요일</label><input type='number' name='freedaystart' id='start_1' class='form-control set-time' min='8' max='18' value='8' onchange='freetime(1)' />~<input type='number' name='freedayend' id='end_1' class='form-control set-time' min='12' max='22' onchange='freetime(1)' /><label id="1" style="width:100px;height:20px;"></label>
                                    </div>
                                    <div>
                                        <label class="weekday">화요일</label><input type='number' name='freedaystart' id='start_2' class='form-control set-time' min='8' max='18' value='8'  onchange='freetime(2)' />~<input type='number' name='freedayend' id='end_2' class='form-control set-time' min='12' max='22' onchange='freetime(2)' /><label id="2" style="width:100px;height:20px;"></label>
                                    </div>
                                    <div>
                                        <label class="weekday">수요일</label><input type='number' name='freedaystart' id='start_3' class='form-control set-time' min='8' max='18' value='8'  onchange='freetime(3)' />~<input type='number' name='freedayend' id='end_3' class='form-control set-time' min='12' max='22' onchange='freetime(3)' /><label id="3" style="width:100px;height:20px;"></label>
                                    </div>
                                    <div>
                                        <label class="weekday">목요일</label><input type='number' name='freedaystart' id='start_4' class='form-control set-time' min='8' max='18' value='8'  onchange="freetime(4)" />~<input type='number' name='freedayend' id='end_4' class='form-control set-time' min='12' max='22' onchange='freetime(4)' /><label id="4" style="width:100px;height:20px;"></label>
                                    </div>
                                    <div>
                                        <label class="weekday">금요일</label><input type='number' name='freedaystart' id='start_5' class='form-control set-time' min='8' max='18' value='8'  onchange='freetime(5)' />~<input type='number' name='freedayend' id='end_5' class='form-control set-time' min='12' max='22' onchange='freetime(5)' /><label id="5" style="width:100px;height:20px;"></label>
                                    </div>
                                </div>                               
                            </div>
                             <label>오후1시부터 13시로 표기해주시길바랍니다.</label>
                        </div>
                    </div>
                    <div class="btn-align">
                        <input type="reset" class="btn btn-danger" />
                        <input type="button" class="btn btn-primary" onclick ="Confirm_Checking()" value="결재승인요청" />                            
                    </div>
                </form>
            </div>
	<%@ include file="/footer.jsp" %>
</body>
</html>