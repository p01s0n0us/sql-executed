package com.xiaobai.sql.service;

import com.xiaobai.sql.model.dto.QuestionDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> questions(String openid, Pageable pageable);

    QuestionDTO question(String openid, Integer id);

    String answerSql(Integer id);

    Long count();

}
