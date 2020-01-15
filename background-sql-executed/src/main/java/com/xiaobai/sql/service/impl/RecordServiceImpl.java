package com.xiaobai.sql.service.impl;

import com.xiaobai.sql.model.dto.RecordDTO;
import com.xiaobai.sql.model.entity.Record;
import com.xiaobai.sql.repository.RecordRepository;
import com.xiaobai.sql.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    private final SimpleDateFormat simpleDateFormat;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public Record addRecord(String openid, boolean correct, Integer id, String submitSql, Long time) {
        log.info("openid: [{}], question id: [{}], correct: [{}]", openid, id, correct);
        Record record = new Record(null, openid, id, correct ? 2 : 1, submitSql, time, new Date());
        return recordRepository.save(record);
    }

    @Override
    public List<RecordDTO> records(String openid, Pageable pageable) {
        log.info("openid: [{}], page: [{}]", openid, pageable);
        return recordRepository.getRecordsByPage(openid, pageable).stream()
                               .map((Function<Record, RecordDTO>) record -> new RecordDTO().convertFrom(record))
                               .collect(Collectors.toList());
    }

    @Override
    public Long count(String openid) {
        return recordRepository.countByOpenid(openid);
    }
}
