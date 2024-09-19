package moodbuddy.moodbuddy.global;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "HealthCheck", description = "헬스 체 API")
@RequiredArgsConstructor
@Slf4j
public class HealthCheckApiController
{
}
