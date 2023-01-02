package com.mrppa.uniquegen.uniquegenserver.api;

import com.mrppa.uniquegen.*;
import com.mrppa.uniquegen.uniquegenserver.service.IDGeneratorContextBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IDGenRestAPI {
    private static final Logger logger = LoggerFactory.getLogger(IDGenRestAPI.class);

    @Autowired
    IDGeneratorContextBridge idGeneratorContextBridge;

    @PostMapping("/getNextId/{generateType}")
    public List<String> getNextIds(@PathVariable GenerateType generateType,
                                   @RequestParam(value = "numberOfIds", defaultValue = "1") int numberOfIds) {
        logger.debug("Get next {} ids for {} ", numberOfIds, generateType);
        IDGeneratorContext idGeneratorContext = idGeneratorContextBridge.generatorContext(generateType);
        IDGenerator idGenerator = IDGenProvider.getGenerator(generateType, idGeneratorContext);
        return idGenerator.generateIds(numberOfIds);
    }

    @ExceptionHandler({IDGeneratorException.class})
    public ResponseEntity<String> handleIDGeneratorException(IDGeneratorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
