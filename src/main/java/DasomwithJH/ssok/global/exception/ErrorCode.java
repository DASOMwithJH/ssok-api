package DasomwithJH.ssok.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    //String name();
    int getCode();
    HttpStatus getStatus();
    String getMessage();

}
