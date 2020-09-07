package com.ee.declaration.mapper;

import com.ee.declaration.persistance.model.Declaration;
import com.ee.declaration.web.dto.DeclarationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        uses = {
            NameMapper.class,
            DescriptionMapper.class,
            TechnicalDescriptionMapper.class
        },
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface DeclarationMapper {

    @Mapping(target = "id", ignore = true)
    Declaration toDomain(DeclarationDto dto);

}
