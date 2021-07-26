package by.logoped.logopedservice.configuration;

import java.util.Set;

public class WhiteList {
    public static Set<String> paths = Set.of(
            "/api/v1/login/**",
            "/api/v1/token/refresh/**",
            "/api/v1/reg/**"
    );

    public static String[] get(){
        return paths.toArray(new String[0]);
    }
}
