function login(){
    
    var id = document.getElementById("id").value;
    var pw = document.getElementById("pw").value;    
    var fail = document.getElementsByClassName('login-fail');
    
    for(var i = 0;i<fail.length;i++){ fail[i].style.fontSize = "0px"; }
    
    if(id==null || id==""){ 
        fail[0].style.fontSize = "13px";
        return false;
    }
    if(pw==null || pw==""){
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
    if(confirm('퇴근처리를 도와드릴까요?')){
        location.replace("/html/login.html");
    }
    else{ return false; }
}
