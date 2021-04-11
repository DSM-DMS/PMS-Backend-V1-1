package com.dms.pms.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.OptBoolean;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentPointListResponse {
    @JsonProperty("points")
    private List<Point> points = new ArrayList<>();

    public void addPoint(Point point) {
        points.add(point);
    }

    public static class Point {
        public Point(String reason, Integer point, LocalDate date, boolean type) {
            this.reason = reason;
            this.point = point;
            this.date = date;
            this.type = type;
        }

        @ApiModelProperty(example = "<이유>", value = "이유")
        @JsonProperty("reason")
        private String reason;

        @ApiModelProperty(value = "상점")
        @JsonProperty("point")
        private Integer point;

        @ApiModelProperty(example = "2020-01-29", value = "날짜")
        @JsonFormat(lenient = OptBoolean.FALSE)
        private LocalDate date;

        @ApiModelProperty(example = "false", value = "상점: true, 벌점: false")
        @JsonProperty("type")
        private boolean type;
    }
}
