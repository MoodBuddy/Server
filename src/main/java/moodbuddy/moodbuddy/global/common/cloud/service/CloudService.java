package moodbuddy.moodbuddy.global.common.cloud.service;

import moodbuddy.moodbuddy.global.common.cloud.dto.response.CloudResUrlDTO;

public interface CloudService {
    CloudResUrlDTO generatePreSignedUrl();
}
