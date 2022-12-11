package com.apos.imdb.mapper;

import com.apos.imdb.dto.CelebrityDto;
import com.apos.imdb.model.Celebrity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CelebrityMapper {

  Celebrity sourceToDestination(CelebrityDto source);
  CelebrityDto destinationToSource(Celebrity destination);
  List<Celebrity> sourceToDestination(List<CelebrityDto> source);
  List<CelebrityDto> destinationToSource(List<Celebrity> destination);

}
