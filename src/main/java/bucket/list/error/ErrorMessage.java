package bucket.list.error;

public enum ErrorMessage {

    ALREADY_REGISTER(1111,"이미 가입된 회원 입니다"),
    NONEXISTENT_ID(1112,"존재하지 않는 회원 입니다"),
    PASSWORD_MISMATCH(1113, "기존 비밀번호와 입력하신 현재 비밀번호가 틀립니다.");

    private int code;
    private String message;

    ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
