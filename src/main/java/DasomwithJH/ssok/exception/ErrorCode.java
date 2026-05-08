package DasomwithJH.ssok.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    //String name();
    int getCode();
    HttpStatus getStatus();
    String getMessage();

}
