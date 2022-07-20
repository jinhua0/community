package com.newcoder.community;

import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.DiscussRepository;
import com.newcoder.community.entity.DiscussPost;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: ElasticsearchTests
 * @author: jinhua
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticsearchTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussRepository discussRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;



    @Test
    public void testInsert() {
        discussRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussRepository.save(discussPostMapper.selectDiscussPostById(243));
    }

    @Test
    public void testInsertList() {
        /*discussRepository.saveAll(discussPostMapper.selectDiscussPosts(101, 0, 100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(102, 0, 100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(103, 0, 100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(111, 0, 100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(112, 0, 100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(131, 0, 100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(132, 0, 100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(133, 0, 100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(134, 0, 100));*/
        for (int i = 109; i <= 287; i++) {
            DiscussPost post = discussPostMapper.selectDiscussPostById(i);
            if (post != null) {
                discussRepository.save(post);
            }
        }
    }

    @Test
    public void testUpdate() {
        DiscussPost post = discussPostMapper.selectDiscussPostById(231);
        post.setContent("我是新人,使劲灌水.");
        discussRepository.save(post);
    }

    @Test
    public void testSearchByRepository() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        SearchHits<DiscussPost> search = elasticsearchRestTemplate.search((Query) searchQuery, DiscussPost.class);

        // 得到查询结果，返回内容
        List<SearchHit<DiscussPost>> searchHits = search.getSearchHits();
        System.out.println(searchHits);
        // 设置一个实体类集合
        List<DiscussPost> discussPosts = new ArrayList<>();
        // 遍历返回内容进行处理
        for (SearchHit<DiscussPost> searchHit : searchHits) {
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            // 将高亮内容进行填充
            searchHit.getContent().setTitle(highlightFields.get("title") == null ? searchHit.getContent().getTitle() : highlightFields.get("title").get(0));
            searchHit.getContent().setTitle(highlightFields.get("content") == null ? searchHit.getContent().getContent() : highlightFields.get("content").get(0));

            discussPosts.add(searchHit.getContent());
        }
        System.out.println(searchHits);
    }

    @Test
    public void testSearchByTemplate() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();


    }
}
