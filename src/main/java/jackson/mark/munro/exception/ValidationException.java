package jackson.mark.munro.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
    private HttpStatus httpStatus;
    public ValidationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
