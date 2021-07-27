package by.logoped.logopedservice.dto;

public class ValidationConstant {
    public static final int MIN_PASSWORD_LENGTH=4;
    public static final int MAX_PASSWORD_LENGTH=14;
    public static final int MIN_PHONE_LENGTH=11;
    public static final int MAX_PHONE_LENGTH=13;
    public static final String PASSWORD_MSG="Password must contain from " + MIN_PASSWORD_LENGTH +" to " + MAX_PASSWORD_LENGTH + " characters";
    public static final String PHONE_MSG="Phone number must contain from " + MIN_PHONE_LENGTH +" to " + MAX_PHONE_LENGTH + " numbers and may be start with +";
    public static final String EMAIL_REGEXP="\\b[a-zA-Z0-9._%-]+@[a-zA-Z]+\\.[a-zA-Z]{2,3}\\b";
}
