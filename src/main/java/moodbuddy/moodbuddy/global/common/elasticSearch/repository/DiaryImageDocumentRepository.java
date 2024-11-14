package moodbuddy.moodbuddy.global.common.elasticSearch.repository;

import moodbuddy.moodbuddy.global.common.elasticSearch.diary.domain.DiaryImageDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DiaryImageDocumentRepository extends ElasticsearchRepository<DiaryImageDocument, Long> {
}
