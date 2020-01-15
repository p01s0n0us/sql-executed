package com.xiaobai.sql.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaobai.sql.model.dto.base.OutputConverter;
import com.xiaobai.sql.model.entity.Record;
import lombok.Data;

import java.util.Date;

/**
 * @author XinHuiChen
 */
@Data
public class RecordDTO implements OutputConverter<RecordDTO, Record> {

    private Integer qid;

    private Integer status;

    private Long time;

    private String submitSql;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
