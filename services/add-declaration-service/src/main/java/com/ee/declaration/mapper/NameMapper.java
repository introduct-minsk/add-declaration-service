package com.ee.declaration.mapper;

import com.ee.declaration.mapper.config.TranslatableStringMapperConfig;
import com.ee.declaration.persistance.model.Name;
import com.ee.declaration.web.dto.NameDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        config = TranslatableStringMapperConfig.class,
        uses = TranslatableStringMapperConfig.class
)
public abstract class NameMapper {

    @InheritConfiguration(name = "toDomain")
    public abstract Name toDomain(NameDto nameDto);

}
