package challenge.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket apiV1() { // Swagger 설정을 할 수 있게 도와주는 클래스
        Parameter parameterBuilder = new ParameterBuilder() // 모든 API에 전역 파라미터를 설정
                .name(HttpHeaders.AUTHORIZATION)    // 모든 API 테스트 시 header에 AUTHORIZATION 추가
                .description("Access Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        List<Parameter> globalParameters = new ArrayList<>();
        globalParameters.add(parameterBuilder);

        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message("OK")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message("Not Found Error")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message("Internal Server Error")
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("66Challenge")   // Docket Bean이 한 개 이상일 경우 충돌이 나기때문에 이름을 설정해준다.
                .select()   // ApiSelectorBuilder를 생성하여 apis()와 paths()를 사용할 수 있다.
                .apis(RequestHandlerSelectors   // api 스펙이 작성된 패키지를 지정한다.
                        .basePackage("challenge.server"))
                .paths(PathSelectors.any()) // apis로 선택되어진 API중 특정 path 조건에 맞는 API들을 필터링 후 문서화한다.
                .build()
                .globalOperationParameters(globalParameters)
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("66Challenge")
                .description("66Challenge의 API 정의서입니다.")
                .version("0.0.1")
                .build();
    }
}
