package com.mrppa.uniquegen.uniquegenserver.api;

import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.exception.IDGeneratorException;
import com.mrppa.uniquegen.model.GenerateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class IDGenRestAPI {
    private static final Logger logger = LoggerFactory.getLogger(IDGenRestAPI.class);

    @Autowired
    List<IDGenerator> idGenerators = new ArrayList<>();

    @PostMapping("/getNextId/{generateType}")
    public List<String> getNextIds(@PathVariable GenerateType generateType,
                                   @RequestParam(value = "numberOfIds", defaultValue = "1") int numberOfIds) {
        logger.debug("Get next {} ids for {} ", numberOfIds, generateType);
        Optional<IDGenerator> optIdGenerator = idGenerators.stream()
                .filter(idGen -> generateType == idGen.getGenerateType())
                .findAny();
        if (optIdGenerator.isEmpty()) {
            throw new IDGeneratorException("ID Generator for type " + generateType + " not found");
        }
        return optIdGenerator.get().generateIds(numberOfIds);
    }

    @ExceptionHandler({IDGeneratorException.class})
    public ResponseEntity<String> handleIDGeneratorException(IDGeneratorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
