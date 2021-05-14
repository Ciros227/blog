package com.example.demo.services;

import com.example.demo.dto.PostDto;
import com.example.demo.entities.Post;
import com.example.demo.repository.PostRepo;
import com.example.demo.services.interfaces.CommentService;
import com.example.demo.services.interfaces.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final CommentService commentService;

    @Override
    public List<PostDto> getAllPostDtos() {
        List<Post> outPost = new ArrayList<>();
        postRepo.findAllByDeleted(0).ifPresent(
                list -> outPost.addAll(list)
                );
        return outPost.stream()
                .map(PostDto::new)
                .map(this::enrichPostComment)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByExample(PostDto input) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Post> example = Example.of(new Post(input), matcher);
        return postRepo.findAll(example).stream()
                .map(PostDto::new)
                .map(this::enrichPostComment)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public PostDto updatePost(PostDto input) {
        Post toUpdPost = postRepo.findById(input.getId()).orElseThrow(() -> new NotFoundException("id: "+ input.getId()));
        if (input.getPostBody() != null)
            toUpdPost.setPostBody(input.getPostBody());
        if (input.getPostTitle() != null)
            toUpdPost.setPostTitle(input.getPostTitle());
        return enrichPostComment(new PostDto(postRepo.save(toUpdPost)));
    }

    @Override
    @SneakyThrows
    public Integer excludePost(Long id) {
        int output = postRepo.deletePostById(id);
        if (output == 0)
            throw new NotFoundException("id: " + id);
        return output;
    }

    @Override
    public PostDto insertPost(PostDto input) {
        input.setDeleted(0);
        return enrichPostComment(new PostDto(postRepo.save(new Post(input))));
    }

    private PostDto enrichPostComment(PostDto input) {
        if(!input.getCommentDtos().isEmpty())
            input.setCommentDtos(commentService.getCommentsDtoNotDelete(input.getCommentDtos()));
        return input;
    }

    @Override
    @SneakyThrows
    public String deletePost(Long id) {
        Optional<Post> post = Optional.ofNullable(postRepo.findById(id).orElseThrow(() -> new NotFoundException("id: " + id)));
        postRepo.deleteById(post.get().getId());

        return "Delete completed!";
    }

}
