package com.ee.declaration.mapper;

import com.ee.declaration.mapper.config.TranslatableStringMapperConfig;
import com.ee.declaration.persistance.model.Description;
import com.ee.declaration.web.dto.DescriptionDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        config = TranslatableStringMapperConfig.class,
        uses = TranslatableStringMapperConfig.class
)
public abstract class DescriptionMapper {

    @InheritConfiguration(name = "toDomain")
    public abstract Description toDomain(DescriptionDto descriptionDto);

}
