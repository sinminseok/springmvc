package minseok.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;


//요청 파라미터와 다르게 Http 메시지 바디를 통해 데이터가 직접 넘어오는 경우 @RequestParam,@ModelAttribute를 사용할수 없다

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody ={}",messageBody);
        response.getWriter().write("OK");
    }

    //InputStream:HTTP요청 메시지 바디의 내용을 직접 조회
    //OutputStream:Http 응답 메시지 바디에 직접 결괄 ㄹ 출력
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter)
            throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream,
                StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    //스프링 MVC는 다음 파라미터를 지원한다.
    //HttpEntity:Http header,body 정보를 편리하게 조회,응답에도 사용가능하다(메시지 바디 정보 직접 반환,헤더 정보 포함 가능,view 조회는 안댐)

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity){
        String messageBody = httpEntity.getBody();
        log.info("messageBody ={}",messageBody);

        return new HttpEntity<>("Ok");
    }

    //@RequestBody를 사용하면 Http 메기지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하면
    //HttpEntity를 사용하거나 @ReqeustHeader를 사용하면된다. ㅇ
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody){
        log.info("messageBody ={}" ,messageBody);
        return "ok";
    }

}
