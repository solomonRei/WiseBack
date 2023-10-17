package com.teamback.wise.models.responses.youtube;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StatisticsByIdResponse {

    private String viewCount;

    private String subscriberCount;

    private String hiddenSubscriberCount;

    private String videoCount;

    private String commentCount;
}
