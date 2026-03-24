package com.example.demo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private Long generateCodeID() {
        return UUID.randomUUID().getMostSignificantBits();
    }

    //401
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        Long codeID = generateCodeID();
        log.error("400 Bad Request [{}]: {}", codeID, e.getMessage());
        return ResponseEntity.badRequest().body(
                new ErrorResponse(codeID, "Некорректный формат JSON", "Bad Request", 400,
                        "Повреждённый JSON в теле запроса")
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Long codeID = generateCodeID();
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        log.warn("422 Validation [{}]: {}", codeID, errors);
        return ResponseEntity.unprocessableEntity().body(
                new ErrorResponse(
                        codeID,
                        "Данные не прошли валидацию",
                        "Unprocessable Entity",
                        422,
                        errors.toString()
                )
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        Long codeID = generateCodeID();
        log.error("400 Type Mismatch [{}]: {}", codeID, e.getMessage());
        return ResponseEntity.badRequest().body(
                new ErrorResponse(codeID, "Неверный формат параметра", "Bad Request", 400,
                        "id передан не в том формате: " + e.getValue())
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        Long codeID = generateCodeID();
        log.error("400 Constraint Violation [{}]: {}", codeID, e.getMessage());
        return ResponseEntity.badRequest().body(
                new ErrorResponse(codeID, "Нарушение ограничений валидации", "Validation Error", 400,
                        e.getMessage())
        );
    }

    //401
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorized(Exception e) {
        Long codeID = generateCodeID();
        log.warn("401 Unauthorized [{}]: {}", codeID, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponse(codeID, "Пользователь не авторизован", "Unauthorized", 401,
                        "Отсутствует или неверный токен доступа")
        );
    }

    //403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        Long codeID = generateCodeID();
        log.warn("403 Forbidden [{}]: {}", codeID, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(codeID, "Доступ запрещён", "Forbidden", 403,
                        "Недостаточно прав для выполнения операции")
        );
    }

    //404
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException e) {
        Long codeID = generateCodeID();
        log.warn("404 Not Found [{}]: {}", codeID, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(codeID, "Ресурс не найден", "Not Found", 404,
                        "Обращение к несуществующему ресурсу: " + e.getRequestURL())
        );
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex) {
        Long codeId = UUID.randomUUID().getMostSignificantBits();

        Map<String, Object> body = Map.of(
                "codeId", codeId,
                "message", ex.getMessage(),
                "error", "Not Found",
                "status", 404,
                "details", "Сущность с указанным id не найдена"
        );

        return ResponseEntity.status(404).body(body);
    }

    //405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        Long codeID = generateCodeID();
        log.warn("405 Method Not Allowed [{}]: {}", codeID, e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ErrorResponse(codeID, "Метод не поддерживается", "Method Not Allowed", 405,
                        "Для ресурса вызван неподдерживаемый HTTP-метод: " + e.getMethod())
        );
    }

    //422
    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ErrorResponse> handleBusinessValidation(BusinessValidationException e) {
        Long codeID = generateCodeID();
        log.warn("422 Unprocessable Entity [{}]: {}", codeID, e.getMessage());
        return ResponseEntity.unprocessableEntity().body(
                new ErrorResponse(codeID, e.getMessage(), "Unprocessable Entity", 422,
                        e.getDetails() != null ? e.getDetails() : "Нарушение бизнес-правил")
        );
    }

    //500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        Long codeID = generateCodeID();
        String uuid = UUID.randomUUID().toString();
        log.error("500 Internal Server Error [{}]: {}", codeID, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(codeID,
                        "Что-то пошло не так, уже исправляем. Обратитесь с номером ошибки: " + uuid,
                        "Internal Server Error", 500, "Непредвиденная ошибка сервера")
        );
    }
}
