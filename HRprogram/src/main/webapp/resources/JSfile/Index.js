var menu_chek ;

function submenu_open(menu){        
    var open = document.getElementById("sub-menubar");
    if(open.style.display == "block"& menu_chek==menu){ open.style.display = "none"; }
    else{ open.style.display = "block"; menu_chek=menu; }

    var sub_title_1 = ["근무기록조회","탄력근무제 신청/승인","초과근무 신청/승인","휴가/결근 신청/승인"];
    var sub_title_2 = ["급여명세서조회(본인)","사원급여정산"];
    var sub_title_3 = ["사원전체조회","신입사원등록"];
    var sub_title_4 = ["휴가 부여","연차확인","menu1_sub2"];
  
    var li_box = document.getElementById('ul');
    while(li_box.hasChildNodes()) { li_box.removeChild(li_box.firstChild); }

    if(menu == "time"){ 
        for(var i=0;i<sub_title_1.length;i++){            
            var li_plus = document.createElement("sub-menu");
            li_plus.innerHTML = "<li class='title-sub' onclick='page("+(i+1)+")'>"+sub_title_1[i]+"</li>";
            li_box.appendChild(li_plus);      
        }
     }
     else if(menu == "money"){
        for(var i=0;i<sub_title_2.length;i++){            
            var li_plus = document.createElement("sub-menu");
            li_plus.innerHTML = "<li class='title-sub' onclick='page("+(i+1)+")'>"+sub_title_2[i]+"</li>";
            li_box.appendChild(li_plus);      
        }
     }
     else if(menu == "view"){
        for(var i=0;i<sub_title_3.length;i++){            
            var li_plus = document.createElement("sub-menu");
            li_plus.innerHTML = "<li class='title-sub' onclick='page("+(i+1)+")'>"+sub_title_3[i]+"</li>";
            li_box.appendChild(li_plus);      
        }
     }
     else if(menu == "etc"){
        for(var i=0;i<sub_title_4.length;i++){            
            var li_plus = document.createElement("sub-menu");
            li_plus.innerHTML = "<li class='title-sub' onclick='page("+(i+1)+")'>"+sub_title_4[i]+"</li>";
            li_box.appendChild(li_plus);      
        }
     }

}

function page(page){    
    if(menu_chek=="time"){ timepage(page); }
    else if(menu_chek=="money"){ moneypage(page); }
    else if(menu_chek=="view"){ viewpage(page); }
    else if(menu_check=="etc"){ etcpage(page); }
}

function timepage(page){     
    if(page==1){location.href = "/work_record.do";}
    else if(page==2){location.href = "/free_work.do";} 
    else if(page==3){location.href = "/over_work.do";} 
    else if(page==4){location.href = "/day_off.do"; }    
}    

function moneypage(page){
    if(page==1){location.href = "/pay_stub.do";}
    else if(page==2){ location.href = "/staff_pay_stub.do"; }
}

function viewpage(page){
    if(page==1){location.href = "/all_staff.do";}
    else if(page==2){location.href = "/register_staff.do";}
}

function etcpage(page){
    if(page==1){location.href = "/work_record.do";}
    else if(page==2){location.href = "/free_work.do";} 
    else if(page==3){location.href = "/over_work.do";} 
    else if(page==4){location.href = "/day_off.do"; }  
}
