package com.newcoder.community.dao;

import com.newcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussRepository extends ElasticsearchRepository<DiscussPost, Integer> {
}
