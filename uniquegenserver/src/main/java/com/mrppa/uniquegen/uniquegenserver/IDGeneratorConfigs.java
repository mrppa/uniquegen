package com.mrppa.uniquegen.uniquegenserver;

import com.mrppa.uniquegen.model.GenerateType;
import com.mrppa.uniquegen.IDGenProvider;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.uniquegenserver.service.IDGeneratorContextBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class IDGeneratorConfigs {

    @Autowired
    IDGeneratorContextBridge idGeneratorContextBridge;
    @Value(value = "${uniquegenserver.idgenerator.generatorTypes}")
    List<GenerateType> generateTypes;

    @Bean
    @ConditionalOnProperty(name = "uniquegenserver.idgenerator.date_sequence_based.enabled", havingValue = "true")
    public IDGenerator dateSequenceBased() {
        IDGeneratorContext idGeneratorContext = idGeneratorContextBridge.generatorContext(GenerateType.DATE_SEQUENCE_BASED);
        return IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED, idGeneratorContext);
    }

    @Bean
    @ConditionalOnProperty(name = "uniquegenserver.idgenerator.jdbc_sequence_based.enabled", havingValue = "true")
    public IDGenerator jdbcSequenceBased() {
        IDGeneratorContext idGeneratorContext = idGeneratorContextBridge.generatorContext(GenerateType.JDBC_SEQUENCE_BASED);
        return IDGenProvider.getGenerator(GenerateType.JDBC_SEQUENCE_BASED, idGeneratorContext);
    }

    @Bean
    @ConditionalOnProperty(name = "uniquegenserver.idgenerator.jdbc_table_sequence_based.enabled", havingValue = "true")
    public IDGenerator jdbcTableSequenceBased() {
        IDGeneratorContext idGeneratorContext = idGeneratorContextBridge.generatorContext(GenerateType.JDBC_TABLE_SEQUENCE_BASED);
        return IDGenProvider.getGenerator(GenerateType.JDBC_TABLE_SEQUENCE_BASED, idGeneratorContext);
    }
}
