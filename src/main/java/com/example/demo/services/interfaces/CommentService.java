package com.example.demo.services.interfaces;

import com.example.demo.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getCommentsDtoNotDelete(List<CommentDto> input);
}
