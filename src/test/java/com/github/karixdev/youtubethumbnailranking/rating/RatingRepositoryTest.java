package com.github.karixdev.youtubethumbnailranking.rating;

import com.github.karixdev.youtubethumbnailranking.thumnail.Thumbnail;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RatingRepositoryTest {
    @Autowired
    RatingRepository underTest;

    @Autowired
    TestEntityManager em;

    User user;

    Thumbnail thumbnail;

    Rating rating;

    @BeforeEach
    void setUp() {
        user = em.persist(User.builder()
                .email("abc@abc.pl")
                .username("username")
                .password("secret-password")
                .userRole(UserRole.ROLE_USER)
                .isEnabled(Boolean.TRUE)
                .build());

        thumbnail = em.persist(Thumbnail.builder()
                .url("thumbnail-url")
                .youtubeVideoId("youtube-id")
                .addedBy(user)
                .build());

        rating = em.persistAndFlush(Rating.builder()
                .points(new BigDecimal(1400))
                .user(user)
                .thumbnail(thumbnail)
                .build());
    }

    @Test
    void GivenThumbnailAndValidUser_WhenFindByThumbnailAndUser_ThenReturnsOptionalWithCorrectEntity() {
        // When
        Optional<Rating> result = underTest.findByThumbnailAndUser(
                thumbnail,
                user
        );

        // Then
        assertThat(result).isPresent();

        Rating resultRating = result.get();

        assertThat(resultRating.getUser()).isEqualTo(rating.getUser());
        assertThat(resultRating.getPoints()).isEqualTo(rating.getPoints());
        assertThat(resultRating.getThumbnail()).isEqualTo(rating.getThumbnail());
    }

    @Test
    void GivenUserAndOtherThumbnail_WhenFindByUserAndThumbnailNot_ThenReturnsCorrectList() {
        // Given
        Thumbnail otherThumbnail = em.persist(Thumbnail.builder()
                .url("thumbnail-url-2")
                .youtubeVideoId("youtube-id-2")
                .addedBy(user)
                .build());

        // When
        List<Rating> result = underTest.findByUserAndThumbnailNot(otherThumbnail, user);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(rating);
    }
}