package moodbuddy.moodbuddy.global.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageCustom<T> {
    private final List<T> content;
    private final int totalPages;
    private final long totalElements;
    private final int size;
    private final int number;
}