package challenge.server.exception;

import lombok.Getter;

public enum ExceptionCode {
    USER_EXISTS(409, "User exists"),
    REVIEW_EXISTS(409, "Review exists"),
    USER_NOT_FOUND(404, "User not found"),
    AUTH_NOT_FOUND(404, "Auth not found"),
    CHALLENGE_NOT_FOUND(404, "Challenge not found"),
    REVIEW_NOT_FOUND(404, "Review not found"),
    WILDCARD_NOT_FOUND(404, "Wildcard not found"),
    FAILED_TO_UPLOAD_FILE(400, "Failed to upload file");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
