#스케쥴러 샘플 프로젝트
## 목표

스프링 MVC 기반 MyBatis 방식을 이용하여 게시판 검증,쿠키,세션 등<br/>
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





