package com.example.demo.repository;

import com.example.demo.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByOwnerId(Long id);
    List<Post> findByPostTitleContaining(String postTitle);
    Post findByIdAndDeleted(Long id, int deleted);
    List<Post> findByPostDateAfter(Timestamp postDate);
    Optional<List<Post>> findAllByDeleted(int deleted);

    @Modifying
    @Transactional
    @Query(value = "update post set deleted = 1 where id = ?", nativeQuery = true)
    int deletePostById(Long id);

    @Query(value = "select * from post where id = ?", nativeQuery = true)
    Optional<Post> findById(Long id);

    @Query(value = "select * from post where deleted = 0 order by post_date desc limit 2", nativeQuery = true)
    Optional<List<Post>> getLastTwo();

    @Query(value = "select * from post where deleted = 1", nativeQuery = true)
    Optional<List<Post>> getAllDeleted();

    Optional<List<Post>> findAllByOrderByPostDateDesc();
    Optional<List<Post>> findByOwnerId(Long id , Sort sort);

    @Query(value = "select * from post where deleted = 0", countQuery = "select count(*) from post where deleted = 0", nativeQuery = true)
    Page<Post> getAllPaging(Pageable page);

}
