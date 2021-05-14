package com.example.demo.dto;

import com.example.demo.entities.Comment;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;
    @NotBlank
    @Size(min = 1, max = 200)
    @JsonAlias("body")
    private String comment;
    private Integer deleted;
    private Long commenterId;
    private Long commentDate;

    public CommentDto(@NonNull Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment() != null ? comment.getComment() : null;
        this.deleted = comment.getDeleted() != null ? comment.getDeleted() : 0;
        this.commenterId = comment.getCommenterId() != null ? comment.getCommenterId() : null;
        this.commentDate = comment.getCommentDate() != null ? comment.getCommentDate().getTime() : null;
    }

}
