package com.xiaobai.sql.repository;

import com.xiaobai.sql.model.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * @author XinHuiChen
 */
public interface RecordRepository extends PagingAndSortingRepository<Record, Integer> {
    @Query(value = " select new Record(r.id,r.openid,r.qid,r.status,r.submitSql,r.time,r.createTime) from Record r where r.openid = ?1")
    Page<Record> getRecordsByPage(String openid, Pageable pageable);

    @Query(value = "select count(*) from t_record where openid = ?1",nativeQuery = true)
    Long countByOpenid(String openid);
}
