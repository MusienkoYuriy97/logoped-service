package by.logoped.logopedservice.service;

import by.logoped.logopedservice.jwt.ActivateKeyJwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${root.path}")
    private String rootPath;

    public static final String EMAIL_VERIFICATION_TEMPLATE = "email";

    public static final String TEMPLATE_VARIABLE_NAME = "name";
    public static final String TEMPLATE_VARIABLE_LINK = "link";

    public static final String EMAIL_FROM = "yury.musienko@solbeg.com";
    public static final String EMAIL_SUBJECT_ACTIVATION_LINK   = "Activation Link";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final ActivateKeyJwtProvider activateKeyJwtProvider;

    public void sendEmailActivate(String to, String fullName, String simpleKey){
        log.info("Send email to {} {} ", to, fullName);
        try {
            Map<String, Object> variables = new HashMap<>();
            String jwtKey = activateKeyJwtProvider.toJwt(simpleKey);
            String link = rootPath + jwtKey;
            variables.put(TEMPLATE_VARIABLE_NAME, fullName);
            variables.put(TEMPLATE_VARIABLE_LINK, link);

            String body = buildTemplate(variables);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            messageHelper.setFrom(EMAIL_FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(EMAIL_SUBJECT_ACTIVATION_LINK);
            messageHelper.setText(body,true);
            javaMailSender.send(mimeMessage);
            log.debug("Send activated link to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildTemplate(Map<String, Object> variables){
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(EmailService.EMAIL_VERIFICATION_TEMPLATE, context);
    }
}
