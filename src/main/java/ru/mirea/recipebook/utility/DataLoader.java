package ru.mirea.recipebook.utility;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mirea.recipebook.domain.UserEntity;
import ru.mirea.recipebook.domain.UserRole;
import ru.mirea.recipebook.service.UserService;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private static final String DEFAULT_ADMIN_LOGIN = "DefaultAdmin";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${ysq.default-admin-password}")
    private String defaultAdminPassword;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userService.isRepoEmpty() && !defaultAdminPassword.isEmpty()) {
            logger.info(String.format("No users found, creating default admin with login: %s", DEFAULT_ADMIN_LOGIN));
            UserEntity defaultAdmin = new UserEntity();
            defaultAdmin.setLogin(DEFAULT_ADMIN_LOGIN);
            defaultAdmin.setPassword(passwordEncoder.encode(defaultAdminPassword));
            defaultAdmin.setUserRole(UserRole.ADMIN);
            userService.saveUser(defaultAdmin);
        }
    }
}
