package bg.tyordanovv.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class CustomHttpError {
    private final HttpStatus status;
    private final ZonedDateTime time;
    private final String message;
    private final String path;

    public CustomHttpError(){
        this.time = null;
        this.message = null;
        this.path = null;
        this.status = null;
    }

    public CustomHttpError(HttpStatus status, String message, String path){
        this.time = ZonedDateTime.now();
        this.path = path;
        this.status = status;
        this.message = message;
    }
}
