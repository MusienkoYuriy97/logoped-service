package by.logoped.logopedservice.util;

import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class WhiteList{
    private static String apiPath = "/api/v1";
    public static Set<String> paths = Set.of(
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-ui/**",
            apiPath + "/auth/login",
            apiPath + "/auth/registration/**",
            apiPath + "/logoped/activate/**"
    );

    public static String[] get(){
        return paths.toArray(new String[0]);
    }
}