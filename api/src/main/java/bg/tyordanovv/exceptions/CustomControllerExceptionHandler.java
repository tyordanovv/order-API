package bg.tyordanovv.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerExceptionHandler {

    private final static Logger LOG = LoggerFactory.getLogger(CustomControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody CustomHttpError handleBadRequestException(ServerHttpRequest request, BadRequestException e){
        return createCustomHttpError(HttpStatus.BAD_REQUEST, request, e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody CustomHttpError handleNotFoundException(ServerHttpRequest request, BadRequestException e){
        return createCustomHttpError(HttpStatus.NOT_FOUND, request, e);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody CustomHttpError handleUnprocessableEntityException(ServerHttpRequest request, BadRequestException e){
        return createCustomHttpError(HttpStatus.UNPROCESSABLE_ENTITY, request, e);
    }

    private CustomHttpError createCustomHttpError(HttpStatus httpStatus, ServerHttpRequest request, BadRequestException e) {
        String path = request.getPath().pathWithinApplication().value();
        String message = e.getMessage();

        LOG.debug("HTTP status: {}, path: {}, message: {}", httpStatus, path, message );

        return new CustomHttpError(httpStatus, path, message);
    }
}
