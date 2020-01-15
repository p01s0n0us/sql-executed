package com.xiaobai.sql.service.impl;

import com.xiaobai.sql.model.dto.QuestionDTO;
import com.xiaobai.sql.model.entity.Question;
import com.xiaobai.sql.repository.QuestionRepository;
import com.xiaobai.sql.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XinHuiChen
 */
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<QuestionDTO> questions(String openid, Pageable pageable) {
        log.info("openid: [{}], page: [{}]", openid, pageable);
        List<Object[]> questions = questionRepository.getQuestionsByPage(openid, pageable);
        List<QuestionDTO> questionDTOs = questions.stream().map(this::getQuestionDTO).collect(Collectors.toList());
        log.info("questionDTOs: [{}]", questionDTOs);
        return questionDTOs;
    }

    @Override
    public QuestionDTO question(String openid, Integer id) {
        log.info("openid: [{}], id: [{}]", openid, id);
        Object[] objects = (Object[]) questionRepository.getQuestion(openid, id);
        return getQuestionDTO(objects);
    }

    @Override
    public String answerSql(Integer id) {
        return questionRepository.getAnswerSql(id);
    }


    @Override
    public Long count() {
        long count = questionRepository.count();
        log.info("question count: [{}]", count);
        return count;
    }


    private QuestionDTO getQuestionDTO(Object[] objects) {
        Question question = new Question();
        question.setId((Integer) objects[0]);
        question.setType((String) objects[1]);
        question.setContent((String) objects[2]);
        question.setHard((Integer) objects[3]);
        question.setStatus(objects[4] == null ? Integer.valueOf(0) : (Integer) objects[4]);
        return new QuestionDTO().convertFrom(question);
    }
}
