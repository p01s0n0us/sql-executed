package com.xiaobai.sql.controller;

import com.xiaobai.sql.model.dto.RecordDTO;
import com.xiaobai.sql.service.RecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author XinHuiChen
 */
@RestController
@RequestMapping(value = "/record")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {this.recordService = recordService;}

    @ApiOperation(value = "获取指定页数的记录")
    @GetMapping(value = "/page/{id}")
    public List<RecordDTO> records(@RequestParam String openid, @PathVariable Integer id, HttpServletResponse response){
        Long totalCount = recordService.count(openid);
        response.setHeader("Total-Count", totalCount.toString());
        return recordService.records(openid, PageRequest.of(id,20, new Sort(Sort.Direction.DESC,"createTime")));
    }
}
