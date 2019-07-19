package com.monolithiot.iot.templates.service.impl;

import com.monolithiot.iot.commons.utils.DateTimeUtils;
import com.monolithiot.iot.commons.utils.RandomUtils;
import com.monolithiot.iot.templates.Vo.SimplePageRequest;
import com.monolithiot.iot.templates.entity.MeasureTemplate;
import com.monolithiot.iot.templates.repository.es.TemplateRepository;
import com.monolithiot.iot.templates.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * Create By leven ont 2019/7/18 22:53
 * Class Name :[TemplateServiceImpl]
 * <p>
 * 模板相关业务行为实现
 *
 * @author leven
 */
@Slf4j
@Component
public class TemplateServiceImpl implements TemplateService {
    private static final HighlightBuilder HIGHLIGHT_BUILDER = new HighlightBuilder().preTags("<em>").postTags("</em>");
    private static final HighlightBuilder.Field[] SEARCH_HIGHLIGHT_FIELDS = {
            new HighlightBuilder.Field("title"),
            new HighlightBuilder.Field("title.pinyin"),
            new HighlightBuilder.Field("description"),
            new HighlightBuilder.Field("description.pinyin"),
            new HighlightBuilder.Field("authorName"),
            new HighlightBuilder.Field("authorName.pinyin"),
            new HighlightBuilder.Field("industry"),
            new HighlightBuilder.Field("industry.pinyin"),
            new HighlightBuilder.Field("fields.description"),
            new HighlightBuilder.Field("fields.description.pinyin"),
            new HighlightBuilder.Field("fields.displayText"),
            new HighlightBuilder.Field("fields.displayText.pinyin")
    };
    private final TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public MeasureTemplate save(MeasureTemplate measureTemplate) {
        val id = RandomUtils.randomPrettyUUIDString();
        val now = DateTimeUtils.now();
        measureTemplate.setCreateTime(now);
        measureTemplate.setModifyTime(now);
        measureTemplate.setId(id);
        log.debug("Save template to ElasticSearch id={}", id);
        return templateRepository.save(measureTemplate);
    }

    @Override
    public Page<MeasureTemplate> search(String q, Integer page, Integer rows) {
        val nestQuery = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("fields.description", q))
                .should(QueryBuilders.matchQuery("fields.description.pinyin", q))
                .should(QueryBuilders.matchQuery("fields.description.pinyin", q))
                .should(QueryBuilders.matchQuery("fields.displayText", q))
                .should(QueryBuilders.matchQuery("fields.displayText.pinyin", q));
        val mainQuery = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("title.pinyin", q))
                .should(QueryBuilders.matchQuery("title", q))
                .should(QueryBuilders.matchQuery("description", q))
                .should(QueryBuilders.matchQuery("description.pinyin", q))
                .should(QueryBuilders.matchQuery("authorName", q))
                .should(QueryBuilders.matchQuery("authorName.pinyin", q))
                .should(QueryBuilders.matchQuery("industry", q))
                .should(QueryBuilders.matchQuery("industry.pinyin", q))
                .should(nestQuery);
        val query = new NativeSearchQueryBuilder()
                .withQuery(mainQuery)
                .withHighlightFields(SEARCH_HIGHLIGHT_FIELDS)
                .withHighlightBuilder(HIGHLIGHT_BUILDER)
                .withPageable(new SimplePageRequest(page, rows))
                .build();
        return templateRepository.search(query);
    }
}
