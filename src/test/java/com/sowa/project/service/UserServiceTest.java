package com.sowa.project.service;

import com.sowa.project.model.dto.RegisterRequest;
import com.sowa.project.model.User;
import com.sowa.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

    @Test
    void registerHashesPassword() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);

        Mockito.when(encoder.encode(Mockito.any()))
                .thenReturn("hashed");

        UserService service = new UserService(repo, encoder);

        RegisterRequest req = new RegisterRequest();
        req.setUsername("test");
        req.setEmail("test@test.com");
        req.setPassword("password123");

        service.register(req);

        Mockito.verify(repo).save(Mockito.argThat(
                (User u) -> u.getPassword().equals("hashed")
        ));
    }
}
