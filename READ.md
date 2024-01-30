# 로그인 회원가입 기능
DB 는 resource에 있는 datasource 자기자신에 맞는 DB정보로 변경하고 사용하면 됨.

demo(back) 실행 방법	

    cd demo
    mvn spring-boot:run
백 실행 잘안되면 종속성 문제기 때문에 mvn install이랑 DB 연결 잘 확인하길.

front 실행 방법	(react 사용함)

    cd front
    npm install
    npm start
이것도 마찬가지고 종속성 문제로 실행안될수 있으니 확인 잘하길





## 추가한 내용

- front
@mui/material @emotion/react @emotion/styled

- back
datasource:
    url: jdbc:mysql://localhost:3307/loc
    username: loc
    password: 1234





``` java
public class WebMvcConfig implements WebMvcConfigurer {
    
    String uploadPath = "file:///C:/location/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("images/**")
                .addResourceLocations(uploadPath);
    }
}

/*
이 코드는 Spring Framework에서 사용되는 WebMvcConfigurer 인터페이스를 구현하는 WebMvcConfig 클래스를 정의하고 있습니다. 이 클래스는 웹 애플리케이션의 MVC(모델-뷰-컨트롤러) 구성을 설정하는 데 사용됩니다.

여기에서는 파일 업로드 경로를 설정하고, 이를 웹 애플리케이션에서 접근할 수 있도록 리소스 핸들러를 등록하는 메서드를 제공하고 있습니다. 구체적으로는 addResourceHandlers 메서드를 재정의하여 정적 리소스의 핸들링을 설정하고 있습니다.

코드에서 uploadPath 변수는 파일 업로드 경로를 나타내며, "file:///C:/location/"로 초기화되어 있습니다. 따라서 이 경로에 저장된 파일은 웹 애플리케이션에서 "images/**"로 접근할 수 있게 됩니다. 즉, images라는 경로로 들어오는 요청은 실제로 서버의 파일 시스템에서 해당 경로로 매핑됩니다.

예를 들어, http://localhost:8080/images/example.jpg와 같은 요청이 들어오면 해당 요청은 실제로 파일 시스템의 C:/location/example.jpg 파일과 매핑됩니다.

이 코드를 사용하기 위해서는 해당 클래스가 Spring의 구성 클래스로 인식되도록 어노테이션을 추가하거나, XML 설정 파일에서 등록해야 합니다. 예를 들어, @Configuration 어노테이션을 추가하여 스프링 컨텍스트에 이 클래스가 구성으로 사용됨을 알릴 수 있습니다.
*/
```

``` java
@Getter
@Builder
@AllArgsConstructor
public class RegistImgReqDTO {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static RegistInfoReqDTO of(LocationImg locationImg) {
        return modelMapper.map(locationImg, RegistInfoReqDTO.class);
    }
}

/*
정적 필드 및 메서드:
modelMapper: ModelMapper 라이브러리를 사용하여 객체 간에 매핑을 수행하기 위한 정적 필드입니다.
of 메서드: 주어진 LocationImg 객체를 RegistInfoReqDTO 객체로 변환하여 반환하는 정적 메서드입니다. 이는 modelMapper를 사용하여 객체 간 매핑을 수행합니다.
*/
```