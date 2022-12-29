# uniquegen
Java based unique ID Generator

## How to install with Gradle

### Using jitpack

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```
dependencies {
    implementation 'com.github.mrppa:uniquegen:<Version>'
}
```

Maven and more
[![](https://jitpack.io/v/mrppa/uniquegen.svg)](https://jitpack.io/#mrppa/uniquegen)

```Version``` - Refer to releases for the latest version. https://github.com/mrppa/uniquegen/releases

## Usage

Syntex
```
final IDGeneratorContext idGeneratorContext = new ContextBuilder()
                ...
                .build();
                
 final IDGenerator idGenerator = IDGenProvider.getGenerator(<IDProviderType>, idGeneratorContext);
 String generatedId = idGenerator.generateId();
```

example
```
final IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "inst1")
                .build();

 final IDGenerator idGenerator = IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED, idGeneratorContext);
 String generatedId = idGenerator.generateId(); 
```

```IDProviderType``` - Type of the id provider

```idGeneratorContext ``` - Context with required variables for each type

Refer to each ID Provider types for required context variables

## ID provider type

### DATE_SEQUENCE_BASED

Generate distributed id based on the instance id, date and a running sequence. 
Length is 29 digits

#### Format
```
[Date][Sequence][Instance id]
```

```Date``` - Date formatted in yyyyMMddHHmmssSSS . 17 digits

```Sequence``` - A running sequence number . 6 digits left padded. Recycled after every 999,999 records

```Instance id``` - Instance Id left padded . 6 digits


sample id
```
202212280002261240000010inst1
```

#### Example usage
```
final IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "inst1")
                .build();

 final IDGenerator idGenerator = IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED, idGeneratorContext);
 String generatedId = idGenerator.generateId(); 
```

#### Context variables
| Variable      |Desc                       | Data type | Mandatory                 |
|---------------|---------------------------|-----------|---------------------------|
| INSTANCE_ID   |Instance id of the service | String    | No. Default set to 000000 |


### JDBC_SEQUENCE_BASED

Generate sequence backed by JDBC sequences . Length is 24 digits

#### Format
```
[Date][Sequence]
```

```Date``` - Date formatted in yyyyMMddHHmmss . 14 digits

```Sequence``` - database sequence number . 10 digits left padded. Recycled based on the database 


sample id
```
202212291707590000000001
```

#### Example usage
```
final IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.JDBC_CONNECTION, connection)
                .add(DateSequenceIDGenerator.SEQUENCE_NAME, "test_sequence")
                .build();

 final IDGenerator idGenerator = IDGenProvider.getGenerator(GenerateType.JDBC_SEQUENCE_BASED, idGeneratorContext);
 String generatedId = idGenerator.generateId(); 
```

#### Context variables
| Variable        | Desc                                                      | Data Type           | Mandatory                            |
|-----------------|-----------------------------------------------------------|---------------------|--------------------------------------|
| JDBC_CONNECTION | JDBC connection object                                    | java.sql.Connection | Yes                                  |
| SEQUENCE_NAME   | DB sequence name compatible with JDBC naming conventions  | String              | No. Value default to uniquegen_jdbc  |

#### Supported databases
- PostgreSQL
- MariaDB
- H2

## Development/Extention Guideline
- Checkout the repository and open the project with the ide
- ```gradle build``` for build the project


### Classes
#### IDGenerator.java
The main interface for any id generation logic. 

#### GenerateType.java
Enum to hold the generated types

#### IDGenProvider
Factory to return the IDGenerator implementation by GenerateType

#### BaseIDGeneratorTest
Base unit test to for each IDGenerator implementation

#### IDGeneratorContext
Hold the context for each generators