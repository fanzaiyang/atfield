package com.example.test.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2300451262283040764L;

    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date today;
    private Date now;
    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;
    private String remarks;
}
