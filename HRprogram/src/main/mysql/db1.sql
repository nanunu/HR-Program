create database HRProgram;
use HRProgram;

/*1. 부서 정보 : 부서코드, 부서명*/
create table Department(
	Dcode varchar(10) not null,
    Dname varchar(10) not null,
    primary key(Dcode)
);

/*2. 사원 정보 : 사원코드, 이름, 부서코드, 직급, 비밀번호, 이메일, 입사일, 연락처, 생년월일, 우편번호, 주소, 성별, 최종학력, 병역, 결혼여부, 면허자격, 은행, 계좌번호, 예금주, 차량*/
create table Employee(
   Ecode varchar(15) not null,
    Ename varchar(10) not null,
    Dcode varchar(20),
    position varchar(10) not null,
    password varchar(12) not null,
    email varchar(50) not null,
    joinday date not null,
    phone varchar(20) not null,
    birth varchar(6),
    zipcode varchar(5),
    address varchar(100),
    sex varchar(4) not null,
    education varchar(20),
    military boolean,
    maritalStatus varchar(4),
    licence varchar(100),
    bank varchar(10) not null,
    bankbook varchar(30) not null,
    depositor varchar(10) not null,
    car varchar(10),
    primary key(Ecode),
    foreign key(Dcode) references Department(Dcode)
);


select Ename,Ecode from Employee;

/*3. 휴가 : 휴가코드, 휴가명칭, 부여시간, 부여일*/
create table Holiday(
	Hcode varchar(10) not null,
    Hname varchar(10) not null, 
    Htime int,
    Hday int,
	primary key(Hcode)
);

/*4. 사원 휴가 : 사원코드, 부여휴가수, 사용휴가수*/
create table EH(
	Ecode varchar(15) not null,
    numOfmyholiday decimal(5,3) not null,
    usenumOfholiday decimal(5,3) default 0.00,
    primary key(Ecode),
    foreign key(Ecode) references Employee(Ecode)
);

/*5. 휴가 기록 : 휴가기록코드, 사원코드, 휴가코드, 적용일, 시작시간, 종료시간, 사용시간, 사용일수, 사유, 결재상태*/
create table holiRecord(
	holiRcode int auto_increment not null,
    Ecode varchar(15) not null,
    holicode varchar(10) not null,
    holiRuseday date not null,
    holiRstarttime time,
    holiRendtime time,
    holiRusetime int,
    holiRdays int, 
    holiRreason varchar(100) not null,
    holiRapproval varchar(10) not null defalut 'waiting',
    primary key(holiRcode),
    foreign key(Ecode) references Employee(Ecode)
); 

/*6. 출퇴근 기록 : 출퇴근기록코드, 사원코드, 일자, 출근시간, 퇴근시간*/
create table Commute(
	Cmcode int auto_increment not null,
    Ecode varchar(15) not null,
    Cmday date not null,
    CmAtTime time not null,
    CmGetoffTime time,
    primary key(Cmcode),
    foreign key(Ecode) references Employee(Ecode)
);

/*7. 급여(수당) : 급여(수당)코드, 급여(수당)명, 금액*/
create table Pay(
	PayCode varchar(10) not null,
    PayName varchar(10) not null,
    PayMoney int not null,
    primary key(PayCode)
);

/*8. 공제 : 공제코드, 공제명, 공제율, 공제액*/
create table Deduction(
	DeCode varchar(10) not null,
    DeName varchar(10) not null,
    DeRate decimal(4,3),
    DeAllowance int,
    primary key(DeCode)
);

/*9. 급여명세서 : 명세서코드, 사원코드, 근무월, 급여(수당)코드, 공제코드, 총액, 명세서발송, 지급여부*/
create table PayStub(
	PScode int auto_increment not null,
    Ecode varchar(15) not null,
    PsMonth int not null,
    AllPayCode varchar(100) not null,
    AllDeCode varchar(100) not null,
    Sum int,
    send boolean not null,
    PSStatus boolean not null,
    primary key(PSCode),
    foreign key(Ecode) references Employee(Ecode)
);

/*10. 초과근무기록 : 초과근무기록코드, 사원코드, 초과근무일, 시작시간, 종료시간, 총 초과근무시간, 초과근무사유 ,결재상태*/
create table OverTime(
	OTCode int auto_increment not null,
    Ecode varchar(15) not null,
    OTDay date not null,
    OTstartTime time not null,
    OTendTime time not null,
    TimeSum int not null,
    OTReason varchar(500),
    OTapproval varchar(10) not null defalut 'waiting',
    primary key(OTCode),
    foreign key(Ecode) references Employee(Ecode)
);

/*11. 탄력근무신청 : 탄력근무기록코드, 사원코드, 탄력근무시작일, 탄력근무종료일, (월)근무시작시간, (월)근무종료시간, (화)근무시작시간, (화)근무종료시간, (수)근무시작시간, (수)근무종료시간, (목)근무시작시간, (목)근무종료시간, (금)근무시작시간, (금)근무종료시간, 결재상태, 결재승인일*/
create table FlexTime(
	FTCode int auto_increment not null,
    Ecode varchar(15) not null,
    FTstartday date not null,
    FTendday date not null,
    MonStart time not null,
    MonEnd time not null,
    TueStart time not null,
    TueEnd time not null,
    WedStart time not null,
    WedEnd time not null,
    ThuStart time not null,
    ThuEnd time not null,
    FriStart time not null,
    FriEnd time not null,
    FTapproval varchar(10) not null,
    AdmissionDate date,
    primary key(FTCode),
    foreign key(ECode) references Employee(Ecode)
);