package moodbuddy.moodbuddy.global.common.elasticSearch.repository;

import moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain.DiaryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DiaryDocumentRepository extends ElasticsearchRepository<DiaryDocument, Long> {
}
