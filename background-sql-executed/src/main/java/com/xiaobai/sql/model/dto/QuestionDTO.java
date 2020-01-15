package com.xiaobai.sql.model.dto;

import com.xiaobai.sql.model.dto.base.OutputConverter;
import com.xiaobai.sql.model.entity.Question;
import lombok.Data;

/**
 * @author XinHuiChen
 */
@Data
public class QuestionDTO implements OutputConverter<QuestionDTO, Question> {
    private Integer id;

    private String type;

    private String content;

    private Integer hard;

    private Integer status;
}
