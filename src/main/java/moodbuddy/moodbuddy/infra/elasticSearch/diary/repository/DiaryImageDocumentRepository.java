package moodbuddy.moodbuddy.infra.elasticSearch.diary.repository;

import moodbuddy.moodbuddy.infra.elasticSearch.diary.domain.DiaryImageDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DiaryImageDocumentRepository extends ElasticsearchRepository<DiaryImageDocument, Long> {
}
