package minseok.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import minseok.springmvc.basic.HelloData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username ={} age ={}",username,age);
        response.getWriter().write("OK");
    }

    @ResponseBody
    @RequestMapping("/request-para-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge){
                log.info("username ={} age ={}",memberName,memberAge);
                return "ok";
    }

    //RequestParam을 생략하면 required=false를 적용한다.
    //근데 보통@RequestParam를 사용해 명확하게 요청 파라미터에서 데이터를 읽는 다는것을 표시하자
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username,int age){
        log.info("username ={} age={}",username,age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true,defaultValue = "guest") String username,
            @RequestParam(required = false,defaultValue = "-1") int age
    ){
        return "ok";
    }

    //Http 요청 파라미터 @ModelAttribute
    //실제 개발을 하면 요청 파라미터를 받아서 필요한 객체를 만들고 그 객체에 값을 넣어줘야한다.
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String StringmodelAttributeV1(@ModelAttribute HelloData helloData){
        log.info("username = {} age ={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }



}
