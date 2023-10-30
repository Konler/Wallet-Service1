package ru.ylab.task1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.task1.dto.TransactionDto;
import ru.ylab.task1.model.transaction.Transaction;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "playerId", source = "playerId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "type", expression = "java(TransactionType.valueOf(transactionDto.getType()))")
    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    @Mapping(target = "playerId", source = "playerId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "type", expression = "java(String.valueOf(transaction.getType()))")
    TransactionDto transactionToTransactionDto(Transaction transaction);

}
