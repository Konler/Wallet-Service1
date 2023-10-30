package ru.ylab.task1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.task1.dto.HistoryItemDto;
import ru.ylab.task1.model.HistoryItem;

@Mapper
public interface HistoryMapper {

    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "playerId", target = "playerId")
    @Mapping(source = "action", target = "action")
    @Mapping(target = "time", expression = "java(item.getTime().toString())")
    HistoryItemDto historyItemToDto(HistoryItem item);
}
