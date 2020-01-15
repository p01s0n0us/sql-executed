package com.xiaobai.sql.repository;

import com.xiaobai.sql.model.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author XinHuiChen
 */
public interface QuestionRepository extends PagingAndSortingRepository<Question, Integer> {
    @Query(value = "select q.id, q.type, q.content, q.hard, r.status from t_question q left join (select openid, qid, max(status) status from t_record group by openid, qid) r on q.id = r.qid and r.openid = ?1 order by q.id", nativeQuery = true)
    List<Object[]> getQuestionsByPage(String openid, Pageable pageable);

    @Query(value = "select distinct q.id, q.type, q.content, q.hard, r.status from t_question q left join (select openid, qid, max(status) status from t_record group by openid, qid) r on q.id = r.qid and r.openid = ?1 where q.id=?2", nativeQuery = true)
    Object getQuestion(String openid, Integer id);

    @Query(value = "select q.answer_sql from t_question q where q.id = ?1", nativeQuery = true)
    String getAnswerSql(Integer id);
}
