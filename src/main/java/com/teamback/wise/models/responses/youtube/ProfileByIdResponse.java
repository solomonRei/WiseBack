package com.teamback.wise.models.responses.youtube;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileByIdResponse {

    private String youtubeChannelId;

    private String youtubeChannelName;

    private String profilePictureUrl;

    private String youtubeHandle;

    private String youtubeRegistrationDate;

    private String youtubeFirstVideoDate;

}
