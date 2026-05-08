package DasomwithJH.ssok.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import semothon.team4.clothesup.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorCode implements ErrorCode {

    DUPLICATE_RESOURCE(40901, HttpStatus.CONFLICT, "Duplicate resource."),
    OPERATION_NOT_ALLOWED(40310, HttpStatus.FORBIDDEN, "Operation not allowed."),
    INVALID_STATE(40020, HttpStatus.BAD_REQUEST, "Invalid state for operation."),

    STOCK_NOT_ENOUGH(40920, HttpStatus.CONFLICT, "Not enough stock."),
    PAYMENT_FAILED(40201, HttpStatus.PAYMENT_REQUIRED, "Payment failed."),
    LIMIT_EXCEEDED(42901, HttpStatus.TOO_MANY_REQUESTS, "Limit exceeded."),
    RATE_LIMITED(42902, HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded.");

    private final int code;
    private final HttpStatus status;
    private final String message;
}

