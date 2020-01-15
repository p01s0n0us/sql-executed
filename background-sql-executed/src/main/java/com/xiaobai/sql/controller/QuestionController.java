package com.xiaobai.sql.controller;

import com.xiaobai.sql.model.dto.QuestionDTO;
import com.xiaobai.sql.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author XinHuiChen
 */
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {this.questionService = questionService;}

    @ApiOperation(value = "获取给定页码的题目")
    @GetMapping(value = "/page/{pageId}")
    public List<QuestionDTO> questions(@RequestParam String openid, @PathVariable Integer pageId, HttpServletResponse response) {
        Long totalCount = questionService.count();
        response.setHeader("Total-Count", totalCount.toString());
        return questionService.questions(openid, PageRequest.of(pageId, 20));
    }

    @ApiOperation(value = "获取指定id的题目")
    @GetMapping(value = "/{id}")
    public QuestionDTO question(@RequestParam String openid, @PathVariable Integer id) {
        return questionService.question(openid, id);
    }
}
