package com.example.demo.entities;

import com.example.demo.dto.PostDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postTitle;
    private String postBody;
    private Integer deleted;

    @Column(nullable = true)
    private String commentId;

    @CreatedDate
    @Column(name = "post_date", updatable = false)
    private Timestamp postDate;

    @LastModifiedDate
    @Column(name = "modify_time")
    private Long modifyTime;

    @CreatedBy
    @Column(name = "owner_id", updatable = false)
    private Long ownerId;

    @LastModifiedBy
    @Column(name = "modify_by")
    private Long modifyBy;

    public Post(@NonNull PostDto postDto) {
        this.id = postDto.getId();
        this.postTitle = postDto.getPostTitle();
        this.postBody = postDto.getPostBody();
        this.deleted = postDto.getDeleted() != null ? postDto.getDeleted() : 0;
    }

    public Post(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getLong("id");
        this.postTitle = resultSet.getString("post_title");
        this.postBody = resultSet.getString("post_body");
        this.postDate = resultSet.getTimestamp("post_date");
        this.deleted = resultSet.getInt("deleted");
        this.ownerId = resultSet.getLong("owner_id");
        this.commentId = resultSet.getString("comment_id");
    }

}
