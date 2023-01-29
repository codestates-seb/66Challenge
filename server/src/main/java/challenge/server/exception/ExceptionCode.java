package challenge.server.exception;

import lombok.Getter;

public enum ExceptionCode {
    USER_EXISTS(409, "User exists"),
    REVIEW_EXISTS(409, "Review exists"),
    AUTH_EXISTS(409, "Auth exists"),
    CHALLENEGE_EXISTS(409,"Challenge exists"),
    USER_NOT_FOUND(404, "User not found"),
    AUTH_NOT_FOUND(404, "Auth not found"),
    CHALLENGE_NOT_FOUND(404, "Challenge not found"),
    REVIEW_NOT_FOUND(404, "Review not found"),
    WILDCARD_NOT_FOUND(404, "Wildcard not found"),
    AUTHENTICATION_NOT_FOUND(404, "Authentication not found in SecurityContextHolder"),
    FAILED_TO_UPLOAD_FILE(400, "Failed to upload file"),
    FAILED_TO_DELETE_FILE(400, "Failed to delete file"),
    UNAUTHORIZED_USER(403, "Unauthorized user"),
    EXTENSION_IS_NOT_VAILD(400, "Extension is not valid"),
    AUTH_NOT_TIME(400, "Auth not time"),
    BOOKMARK_NOT_FOUND(404, "Bookmark not found"),
    CATEGORY_NOT_FOUND(404, "Category not found"),
    HABIT_NOT_FOUND(404, "Habit not found"),
    REPORT_NOT_FOUND(404, "Report not found"),
    TOO_MANY_REPORTS(400, "Too many reports"),
    DUPLICATED_REPORT(400,"Duplicated report"),
    BOOKMARK_EXISTS(409, "Bookmark exists"),
    EMAIL_VERIFICATION_FAILED(404, "Email verification failed"),
    REFRESH_TOKEN_NOT_VALID(400, "Refresh token not valid"),
    LOGOUT_USER(403, "Logout user"),
    ACCESS_TOKEN_NOT_VALID(400, "Access token not valid");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
