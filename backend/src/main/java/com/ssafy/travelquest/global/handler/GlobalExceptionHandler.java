package com.ssafy.travelquest.global.handler;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ssafy.travelquest.global.handler.exception.DungeonCreationException;
import com.ssafy.travelquest.global.handler.exception.QuestGenerationException;
import com.ssafy.travelquest.domain.user.exception.NoSuchUserException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// DTO 필드 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    // 단일 파라미터 제약 위반
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // 타입 변환 실패
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("파라미터 '" + e.getName() + "'는 " + e.getRequiredType().getSimpleName() + " 타입이어야 합니다.");
    }

    // JSON 파싱 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleUnreadable(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청 본문(JSON) 입니다.");
    }
    
    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleNoSuchUser(NoSuchUserException e){
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    			.body("아이디 혹은 비밀번호가 올바르지 않습니다.");
    }
    
	@ExceptionHandler( value = {
			Exception.class
	})
	protected ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
	
	// 던전 생성 중 서버 오류
    @ExceptionHandler(DungeonCreationException.class)
    public ResponseEntity<String> handleDungeonCreation(DungeonCreationException e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("던전 생성 실패: " + e.getMessage());
    }

    // 퀘스트 생성 중 서버 오류
    @ExceptionHandler(QuestGenerationException.class)
    public ResponseEntity<String> handleQuestGeneration(QuestGenerationException e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("퀘스트 생성 실패: " + e.getMessage());
    }

}
