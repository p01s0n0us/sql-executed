package com.xiaobai.sql.service;

import com.xiaobai.sql.model.dto.RecordDTO;
import com.xiaobai.sql.model.entity.Record;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordService {
    Record addRecord(String openid, boolean correct, Integer id,String submitSql,Long time);

    List<RecordDTO> records(String openid, Pageable pageable);

    Long count(String openid);
}
