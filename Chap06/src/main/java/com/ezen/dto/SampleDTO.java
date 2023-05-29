package com.ezen.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //=lombok의 기능 Getter(),Setter(), ToString(), equals(), hashCode()
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleDTO {
	private Long sno;
	private String first;
	private String last;
	private LocalDateTime regTime;
}
