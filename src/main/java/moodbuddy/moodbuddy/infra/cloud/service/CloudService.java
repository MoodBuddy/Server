package moodbuddy.moodbuddy.infra.cloud.service;

import moodbuddy.moodbuddy.infra.cloud.dto.response.CloudResUrlDTO;

public interface CloudService {
    CloudResUrlDTO generatePreSignedUrl();
}
