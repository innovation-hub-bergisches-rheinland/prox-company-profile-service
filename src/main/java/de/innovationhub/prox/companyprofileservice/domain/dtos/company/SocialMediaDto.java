package de.innovationhub.prox.companyprofileservice.domain.dtos.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class SocialMediaDto {
  @JsonProperty("facebook_handle")
  private String facebookHandle;
  @JsonProperty("twitter_handle")
  private String twitterHandle;
  @JsonProperty("instagram_handle")
  private String instagramHandle;
  @JsonProperty("xing_handle")
  private String xingHandle;
  @JsonProperty("linkedIn_handle")
  private String linkedInHandle;
}
