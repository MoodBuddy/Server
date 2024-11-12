package moodbuddy.moodbuddy.domain.diary.service;

import moodbuddy.moodbuddy.domain.diary.domain.Diary;
import moodbuddy.moodbuddy.domain.diary.domain.DiaryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiaryImageService {
    void saveDiaryImages(List<MultipartFile> diaryImgList, Diary diary) throws IOException;
    void deleteAllDiaryImages(Diary diary);
    List<DiaryImage> findImagesByDiary(Diary diary);
    String saveProfileImages(MultipartFile newProfileImg) throws IOException;
}

