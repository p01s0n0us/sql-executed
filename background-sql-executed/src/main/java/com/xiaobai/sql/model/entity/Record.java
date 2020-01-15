package com.xiaobai.sql.model.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

/**
 * @author XinHuiChen
 */
@Data
@Entity
@Table(name = "t_record")
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Slf4j
public class Record{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "openid")
    private String openid;

    @Column(name = "qid")
    private Integer qid;

    @Column(name = "status")
    private Integer status;

    @Column(name = "submit_sql")
    private String submitSql;

    @Column(name = "time")
    private Long time;

    @Column(name = "create_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @PrePersist
    protected void prePersist() {
        Date now = new Date();
        log.info("persist time: [{}]", now);
        if (createTime == null) {
            createTime = now;
        }
    }
}
