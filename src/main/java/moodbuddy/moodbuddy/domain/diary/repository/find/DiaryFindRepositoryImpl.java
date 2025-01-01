package moodbuddy.moodbuddy.domain.diary.repository.find;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moodbuddy.moodbuddy.domain.diary.domain.type.DiaryEmotion;
import moodbuddy.moodbuddy.domain.diary.dto.request.find.DiaryReqFilterDTO;
import moodbuddy.moodbuddy.domain.diary.dto.response.find.DiaryResFindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DiaryFindRepositoryImpl implements DiaryFindRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DiaryFindRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<DiaryResFindDTO> findDiariesWithPageable(Long userId, Pageable pageable) {

        return null;
    }

    @Override
    public Page<DiaryResFindDTO> findDiariesByEmotionWithPageable(DiaryEmotion emotion, Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<DiaryResFindDTO> findDiariesByFilterWithPageable(DiaryReqFilterDTO filterDTO, Long userId, Pageable pageable) {
        return null;
    }
}
