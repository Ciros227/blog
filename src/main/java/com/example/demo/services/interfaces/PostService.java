package com.example.demo.services.interfaces;

import com.example.demo.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPostDtos();
    List<PostDto> getPostsByExample(PostDto input);
    PostDto updatePost(PostDto input);
    Integer excludePost(Long id);
    //PostDto addComment(long id, CommentDto comm);
    PostDto insertPost(PostDto input);
    String deletePost(Long id);
}
