package offTop.SessionHandlerService.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionHandlerController{

    @GetMapping("/ping")
    public String ping(){
        return "pong!";
    }
}