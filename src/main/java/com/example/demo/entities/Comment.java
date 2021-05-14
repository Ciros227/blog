package com.example.demo.entities;

import com.example.demo.dto.CommentDto;
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

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Integer deleted;

    @CreatedDate
    @Column(name = "comment_date", updatable = false)
    private Timestamp commentDate;

    @LastModifiedDate
    @Column(name = "modify_time")
    private Long modifyTime;

    @CreatedBy
    @Column(name = "commenter_id", updatable = false)
    private Long commenterId;

    @LastModifiedBy
    @Column(name = "modfiy_by")
    private Long modifyBy;

    public Comment(@NonNull CommentDto commentDto) {
        this.id = commentDto.getId();
        this.comment = commentDto.getComment();
        this.deleted = commentDto.getDeleted() != null ? commentDto.getDeleted() : 0;
    }

    public Comment(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getLong("id");
        this.comment = resultSet.getString("comment");
        this.commentDate = resultSet.getTimestamp("comment_date");
        this.deleted = resultSet.getInt("deleted");
        this.commenterId = resultSet.getLong("commenter_id");
    }

}
