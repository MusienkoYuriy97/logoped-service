package by.logoped.logopedservice.test;

import static by.logoped.logopedservice.util.StringGenerator.generate;

public class TestConstant {
    //user
    public static final Long USER_ID = 3l;
    public static final String FIRST_USER_NAME = "Юрий";
    public static final String LAST_USER_NAME = "Мусиенко";
    public static final String USER_EMAIL = "musienko@gmail.com";
    public static final String USER_PHONE_NUMBER = "+375291111111";
    public static final String USER_PASSWORD = "1111";


    //logoped
    public static final Long LOGOPED_ID = 2l;
    public static final String FIRST_LOGOPED_NAME = "Алина";
    public static final String LAST_LOGOPED_NAME = "Сасанович";
    public static final String LOGOPED_EMAIL = "sasanovich@gmail.com";
    public static final String LOGOPED_PHONE_NUMBER = "+375296213287";
    public static final String LOGOPED_PASSWORD = "1111";
    public static final String LOGOPED_EDUCATION = "БГПУ";
    public static final String LOGOPED_WORK_PLACE = "Десткий сад № 1";
    public static final int LOGOPED_WORK_EXPERIENCE = 5;

    //admin
    public static final String ADMIN_EMAIL = "97musienko@gmail.com";
    public static final Long ADMIN_ID = 1l;
    public static final String ADMIN_PASSWORD = "1111";
    public static final String ADMIN_PHONE_NUMBER = "+375298344491";

    //activate
    public static final String ACTIVATE_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY3RpdmF0ZSBrZXkiLCJzaW1wbGVLeSI6IjBHNXRqajdQbnk4byIsImlzc3VlZEF0IjoiMjAyMS0wNy0yOFQxMzowODo1MC4zNDYxOTEzNDMiLCJleHBpcmF0aW9uIjoiMjAyMS0wNy0yOVQxMzowODo1MC4zNDYxOTEzNDMifQ.yDqaZJae9nJBvY0xtEEmrMmE57vioNYU0U7R9lI6684";
    public static final String SIMPLE_ACTIVATE_KEY = generate(10);

}
