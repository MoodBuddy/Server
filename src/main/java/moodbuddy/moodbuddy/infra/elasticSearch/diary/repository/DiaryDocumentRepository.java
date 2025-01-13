package moodbuddy.moodbuddy.infra.elasticSearch.diary.repository;

import moodbuddy.moodbuddy.infra.elasticSearch.diary.domain.DiaryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DiaryDocumentRepository extends ElasticsearchRepository<DiaryDocument, Long> {
}
