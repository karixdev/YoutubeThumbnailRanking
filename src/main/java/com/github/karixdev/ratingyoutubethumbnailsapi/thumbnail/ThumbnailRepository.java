package com.github.karixdev.ratingyoutubethumbnailsapi.thumbnail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {
    @Query("""
            SELECT thumbnail
            FROM Thumbnail thumbnail
            WHERE thumbnail.youtubeVideoId = :youtubeVideoId
            """)
    Optional<Thumbnail> findByYoutubeVideoId(@Param("youtubeVideoId") String youtubeVideoId);

    @Query("""
            SELECT thumbnail
            FROM Thumbnail thumbnail
            LEFT JOIN FETCH thumbnail.ratings
            """)
    List<Thumbnail> findAllThumbnails();
}
