#스케쥴러 샘플 프로젝트
## 목표
(db정보는 맨아래)
스프링 MVC 기반 MyBatis 방식을 이용하여 게시판 검증,쿠키,세션,스프링 시큐리티 로그인 등<br/>
다양한 기능을 접목하여 만들어 보는 것을 목표로 합니다.

## 스팩

### 기본 사양

- spring boot 2.4.5
- MyBatis
- Oracle 11g XE

### Utils dependencies

- thymeleaf
- validation
- lombok
- jdbc
- mybatis
- springSecurity

### IDEL

- IntelliJ 로 구축

## Version

### 1.0 초기 셋팅 버전

- 1.0.0:init gradle 프로젝트 생성
- 1.0.1:DB Setting[Oracle,mybatis]
- 1.0.2:Logger
- 1.0.3:DB Create 셋팅 

### 1.1 Board 구현

- 1.1.0:BoardUser DB 테이블 스키마 설계
- 1.1.1:간단한 View 화면 개발
- 1.1.2:스프링 시큐리티 이용하여 로그인창개발(회원은 postman으로 함)
- 1.1.2:Null,빈칸 검증 처리
- 1.1.3:새로고침시 조회수 무한증가 fix

### 1.0.8

IntelliJ 콘솔 로그 한글 깨짐 해결 방법

- IntelliJ File Encodings 변경

1. Ctrl + alt + S
2. Editor > File Encodings 선택
3. 셋팅

- Global Encoding:UTF-8
- Project Encoding:UTF-8
- Default encoding for properties files:UTF-8

### lombok 설정

1. Setting
2. Annotation Processors
3. Enable annotation processing 체크

### 1.1.1

#### 글 등록시 내용이 모두 비어도 등록되는현상

해결 방법
```java
    @NotEmpty @NotBlank(message = "작성자 입력하세요")
    private String author;
    @NotEmpty @NotBlank(message = "제목 입력하세요")
    private String title;
    @NotEmpty @NotBlank(message = "내용 입력해주세요")
    private String content;
```
NULL," " 빈칸 입력시 오류가 발생하면 Errors 인터페이스를 통해 특정필드나</br>
모델 오브젝트 전체에 대한 오류를 등록

```java
@PostMapping("/post")
    public String getPost(@ModelAttribute @Valid UserVO userVO, Errors errors, Model model){

        if(errors.hasErrors())
        {
            model.addAttribute("userVO",userVO);
            Map<String,String> validatorResult = userServiceImpl.validateHandling(errors);
            for(String key: validatorResult.keySet())
            {
                model.addAttribute(key,validatorResult.get(key));
            }

            return "board/post";
        }
```

에러를 필드를 풀어내 valid_xxxxx 이름을 key, 에러메세지 value 
```java
public Map<String, String> validateHandling(Errors errors) {
        Map<String,String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName,error.getDefaultMessage());
        }

        return validatorResult;

```

#### 스프링시큐리티 처음사용시 페이지 접근이 아예 불가능했음

```java

 @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                     .antMatchers("/user/save").permitAll() //모두 허용
                     .antMatchers("/list").hasAnyAuthority("ADMIN","USER") 권한설정을 처음에 할줄몰라서 페이지 접근조차 할수없었음..
                     .anyRequest().authenticated()
                .and()
                    .csrf().ignoringAntMatchers("/user/save")
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/list") //로그인 성공시 이동할 URL
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                .and()
                .sessionManagement()
                .maximumSessions(1) //같은 아이디로 1명만 로그인
                .maxSessionsPreventsLogin(false) //false :신규 로그인은 허용, 기존 사용자는 세션 아웃  true: 이미 로그인한 세션이있으면 로그인 불가
                .expiredUrl("/login"); //세션 아웃되면 이동할 url

    }
}


-board table
```java
create table board(
id number (4),
author varchar2(30),
title varchar2(30),
content varchar2(200),
regdate DATE DEFAULT SYSDATE,
recnt number(4) default 0,
primary key(id)
);  

create sequence board_sequence 시퀀스
INCREMENT BY 1
START WITH 1
nomaxvalue nocache;
```


-reply table
```java
create table mp_reply (
 id number not null,
 rno number not null,
 content varchar2(1000) not null,
 author varchar2(50) not null,
 regdate date default sysdate,
 primary key(id,rno)
);

alter table mp_reply add constraint mp_reply_id foreign key(id) //부모테이블삭제시 자식인 댓글테이블도 같이삭제
references board(id) ON DELETE CASCADE; 

create sequence mp_reply_seq start with 1 minvalue 0; 시퀀스

```

- file table
```java
CREATE TABLE MP_FILE
(
    FILE_NO NUMBER,                         --파일 번호
    id NUMBER NOT NULL,                    --게시판 번호
    ORG_FILE_NAME VARCHAR2(260) NOT NULL,   --원본 파일 이름
    STORED_FILE_NAME VARCHAR2(36) NOT NULL, --변경된 파일 이름
    FILE_SIZE NUMBER,                       --파일 크기
    REGDATE DATE DEFAULT SYSDATE NOT NULL,  --파일등록일
    DEL_GB VARCHAR2(1) DEFAULT 'N' NOT NULL,--삭제구분
    PRIMARY KEY(FILE_NO)                    --기본키 FILE_NO
);

alter table mp_file add constraint mp_file_id foreign key(id) 테이블삭제시 덩달아 삭제
references board(id) ON DELETE CASCADE;
```

- user table

```java
create table users(
userNo number not null primary key,
userId varchar(20) not null,
password varchar(100) not null,
name varchar(20)
);

create sequence SEQ_USERLIST 시퀀스
start with 1
increment by 1
nomaxvalue nocache;
```


- role table
```java 

create table roles
(
    roleNo number not null primary key,
    roleName varchar(20) not null
);

```


- userRoles table
```
create table userRoles
(
userNo number not null,
roleNo number not null
);


alter table userRoles add constraint user_no foreign key(userNo) 유저롤은 userNo 참조
references users(userNo);
alter table userRoles add constraint role_no foreign key(roleNo) 유저롤은 roleNo 참조
references roles(roleNo);
```




