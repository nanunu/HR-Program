

function lost_check(){
    var name = document.getElementById('email').value;
    if(name==null) { 
        alert("Email을 입력해주세요."); 
        return false;
    }

    document.lostpw.submit();
}