package com.example.demo.services;

import com.example.demo.dto.CommentDto;
import com.example.demo.repository.CommentRepo;
import com.example.demo.services.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

    @Override
    public List<CommentDto> getCommentsDtoNotDelete(List<CommentDto> input) {
        List<Long> ids = new ArrayList<>();
        input.forEach(dto -> ids.add(dto.getId()));

        return commentRepo.findAllById(ids).stream()
                .filter(comm -> comm.getDeleted() == 0)
                .map(comm -> new CommentDto(comm))
                .collect(Collectors.toList());
    }
}
