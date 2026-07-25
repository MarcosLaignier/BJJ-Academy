package br.com.bjjacademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    ProblemDetail tratarAutenticacao() {
        return criarProblema(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos");
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    ProblemDetail tratarNaoEncontrado(RegistroNaoEncontradoException exception) {
        return criarProblema(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(RegraNegocioException.class)
    ProblemDetail tratarRegraNegocio(RegraNegocioException exception) {
        return criarProblema(HttpStatus.UNPROCESSABLE_CONTENT, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail tratarValidacao(MethodArgumentNotValidException exception) {
        String detalhe = exception.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return criarProblema(HttpStatus.BAD_REQUEST, detalhe);
    }

    private ProblemDetail criarProblema(HttpStatus status, String detalhe) {
        ProblemDetail problema = ProblemDetail.forStatus(status);
        problema.setDetail(detalhe);
        return problema;
    }
}
