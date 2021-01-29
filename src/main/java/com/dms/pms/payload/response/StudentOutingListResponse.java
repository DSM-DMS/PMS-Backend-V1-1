package com.dms.pms.payload.response;

import com.dms.pms.entity.pms.outing.OutingType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentOutingListResponse {
    @JsonProperty("outings")
    private List<Outing> outings = new ArrayList<>();

    public void addOuting(Outing outing) {
        outings.add(outing);
    }

    public static class Outing {

        public Outing(LocalDate date, String reason, String place, OutingType type) {
            this.date = date;
            this.reason = reason;
            this.place = place;
            this.type = type.toString();
        }

        @ApiModelProperty(example = "2020-01-29", value = "날짜")
        @JsonFormat(lenient = OptBoolean.FALSE)
        private LocalDate date;

        @ApiModelProperty(example = "<이유>", value = "이유")
        @JsonProperty("reason")
        private String reason;

        @ApiModelProperty(example = "<장소>", value = "장소")
        @JsonProperty("place")
        private String place;

        @ApiModelProperty(example = "DISEASE", value = "외출 종류(NORMAL: 일반 외출, DISEASE: 질병 외출)")
        @JsonProperty("type")
        private String type;
    }
}
