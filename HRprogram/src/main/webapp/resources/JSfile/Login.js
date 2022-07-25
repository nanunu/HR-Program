function login(){
    
    var Ecode = document.getElementById("Ecode").value;
    var password = document.getElementById("password").value;    
    var fail = document.getElementsByClassName('login-fail');
    
    for(var i = 0;i<fail.length;i++){ fail[i].style.fontSize = "0px"; }
    
    if(Ecode==null || Ecode==""){ 
        fail[0].style.fontSize = "13px";
        return false;
    }
    if(password==null || password==""){
        fail[1].style.fontSize = "13px";
        return false;
    }

    
    document.login_form.submit();
    
}

function lost_pw(){
    var url="/html/lost_pw.html";
    var option = "width=500px,height=400px,left=700px,top=300px";
    window.open(url,"_blank",option);
}

function logout(){
    if(confirm('퇴근처리를 도와드릴까요?\n 한번 퇴근하면 당일안으로 다시 로그인을 할 수 없습니다.')){
		if(confirm('정말로 퇴근처리를 할까요?')){
			alert("오늘도 수고하셨습니다!");
        	location.replace("logout.do");
        }
        else{ return false; }                
    }
    else{ return false; }
}
