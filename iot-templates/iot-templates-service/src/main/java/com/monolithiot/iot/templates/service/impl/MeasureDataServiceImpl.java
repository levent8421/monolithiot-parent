package com.monolithiot.iot.templates.service.impl;

import com.monolithiot.iot.commons.exception.ResourceNotFoundException;
import com.monolithiot.iot.commons.utils.DateTimeUtils;
import com.monolithiot.iot.commons.utils.RandomUtils;
import com.monolithiot.iot.templates.context.TemplatesConstants;
import com.monolithiot.iot.templates.entity.MeasureData;
import com.monolithiot.iot.templates.repository.es.MeasureDataRepository;
import com.monolithiot.iot.templates.repository.es.mapper.HighlightResultMapper;
import com.monolithiot.iot.templates.service.MeasureDataService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Create by 郭文梁 2019/8/20 16:09
 * MeasureDataServiceImpl
 * 测量数据相关业务行为实现
 *
 * @author 郭文梁
 * @data 2019/8/20 16:09
 */
@Service
@Slf4j
public class MeasureDataServiceImpl implements MeasureDataService {
    private final MeasureDataRepository measureDataRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final HighlightResultMapper highlightResultMapper;

    public MeasureDataServiceImpl(MeasureDataRepository measureDataRepository,
                                  ElasticsearchTemplate elasticsearchTemplate,
                                  HighlightResultMapper highlightResultMapper) {
        this.measureDataRepository = measureDataRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.highlightResultMapper = highlightResultMapper;
    }

    @Override
    public MeasureData save(MeasureData measureData) {
        identity(measureData);
        touch(measureData);
        log.debug("Save template to ElasticSearch id={}", measureData.getId());
        return measureDataRepository.save(measureData);
    }

    /**
     * 设置测量数据的创建时间和更新时间为当前时间
     *
     * @param measureData 测量数据
     */
    private void touch(MeasureData measureData) {
        val now = DateTimeUtils.now();
        measureData.setCreateTime(now);
        measureData.setModifyTime(now);
    }

    /**
     * 为测量数据生成ID
     *
     * @param measureData 测量数据
     */
    private void identity(MeasureData measureData) {
        val id = RandomUtils.randomPrettyUUIDString();
        measureData.setId(id);
    }

    @Override
    public MeasureData require(String id) {
        Optional<MeasureData> data = measureDataRepository.findById(id);
        if (data.isPresent()) {
            return data.get();
        }
        throw new ResourceNotFoundException(MeasureData.class, id);
    }

    @Override
    public Page<MeasureData> findByUserId(Integer userId, int page, int rows) {
        val mainQuery = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("authorId", userId));
        val query = new NativeSearchQueryBuilder()
                .withIndices(TemplatesConstants.ElasticSearch.MEASURE_DATA_INDEX)
                .withTypes(TemplatesConstants.ElasticSearch.MEASURE_DATA_TYPE)
                .withQuery(mainQuery)
                .withPageable(PageRequest.of(page - 1, rows))
                .build();
        return elasticsearchTemplate.queryForPage(query, MeasureData.class, highlightResultMapper);
    }
}
