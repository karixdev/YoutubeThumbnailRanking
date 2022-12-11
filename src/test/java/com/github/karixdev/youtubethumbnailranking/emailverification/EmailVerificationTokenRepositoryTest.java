package com.github.karixdev.youtubethumbnailranking.emailverification;

import com.github.karixdev.youtubethumbnailranking.user.User;
import com.github.karixdev.youtubethumbnailranking.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmailVerificationTokenRepositoryTest {
    @Autowired
    EmailVerificationTokenRepository underTest;

    @Autowired
    TestEntityManager em;

    EmailVerificationToken eToken;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .email("abc@abc.pl")
                .username("username")
                .password("secret-password")
                .userRole(UserRole.ROLE_USER)
                .isEnabled(Boolean.FALSE)
                .build();

        em.persist(user);

        eToken = EmailVerificationToken.builder()
                .user(user)
                .token("token")
                .expiresAt(LocalDateTime.now().plusHours(1))
                .createdAt(LocalDateTime.now())
                .build();

        em.persistAndFlush(eToken);
    }

    @Test
    void GivenNonExistingToken_WhenFindByToken_ThenReturnsEmptyOptional() {
        // Given
        String token = "i-do-not-exist";

        // When
        var result = underTest.findByToken(token);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void GivenExistingToken_WhenFindByToken_ThenReturnsOptionalWithCorrectEmailVerificationToken() {
        // Given
        String token = "token";

        // When
        var result = underTest.findByToken(token);

        // Then
        assertThat(result).isNotEmpty();

        var resultToken = result.get();

        assertThat(resultToken).isEqualTo(eToken);
    }

}
