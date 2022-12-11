package com.apos.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CelebrityDto {

    private String name;
    private String dob;
    private String gender;
    private Set<String> job;
    private String imageUri;

}
