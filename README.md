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
 final IDGenerator idGenerator = IDGenProvider.getGenerator(<IDProviderType>, <InstanceId>);
 String generatedId = idGenerator.generateId();
```

example
```
 final IDGenerator idGenerator = IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED, 'INS001');
 String generatedId = idGenerator.generateId(); 
```

```IDProviderType``` - Type of the id provider

```InstanceId``` - Instance Id . A String with maximum 6 characters in length


## ID provider type

### DATE_SEQUENCE_BASED

Generate distributed id based on the instance id, date and a running sequence

Format
```
[Date][Sequence][Instance id]
```

```Date``` - Date formatted in yyyyMMddHHmmssSSS . 17 digits

```Sequence``` - A running sequence number . 6 digits left padded. Recycled after every 999,999 records

```Instance id``` - Instance Id left padded . 6 digits


example
```
202212280002261240000010inst1
```


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

