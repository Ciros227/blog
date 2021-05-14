package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.services.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/blog/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/getAllPosts")
    public List<PostDto> getAllPosts() {
        return postService.getAllPostDtos();
    }

    @PostMapping("/insert")
    public PostDto insertPost(@Valid @RequestBody PostDto input) {
        return postService.insertPost(input);
    }

    @PutMapping("/update")
    public PostDto updatePost(@RequestBody PostDto input) {
        return postService.updatePost(input);
    }

    @PostMapping("/exclude/{id}")
    public Integer setDeletedPost(@PathVariable(value = "id") Long id) {
        return postService.excludePost(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable(value = "id") Long id) {
        return postService.deletePost(id);
    }


}
