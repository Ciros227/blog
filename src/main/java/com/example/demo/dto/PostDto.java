package com.example.demo.dto;

import com.example.demo.entities.Post;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@XmlRootElement(name = "CommentDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostDto {

    private Long id;
    private String postTitle;
    private String postBody;
    private Integer deleted;
    private Long ownerId;
    private List<CommentDto> commentDtos;

    public PostDto(@NonNull Post post) {
        this.id = post.getId();
        this.postTitle = post.getPostTitle() != null ? post.getPostTitle() : null;
        this.postBody = post.getPostBody() != null ? post.getPostBody() : null;
        this.ownerId = post.getOwnerId() != null ? post.getOwnerId() : null;
        this.deleted = post.getDeleted() != null ? post.getDeleted() : null;
        List<String> commentIds = post.getCommentId() != null ?
                Arrays.asList(post.getCommentId().split(",")) :
                Collections.emptyList();
        this.commentDtos = commentIds.stream()
                .filter(comm -> !comm.equals(""))
                .map(comm -> {
                    CommentDto temp = new CommentDto();
                    temp.setId(Long.valueOf(comm));
                    return temp;
                }).collect(Collectors.toList());
    }

}
