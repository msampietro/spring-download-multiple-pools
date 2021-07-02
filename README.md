##Streaming database data to .csv file efficiently - A practical implementation using Spring Boot and HikariCP

Post: https://medium.com/p/a1cda85fb33b/edit

- Spring Boot 2.4.5
- Java 11
- PostgreSQL 12.3

####Database initialization

CREATE DATABASE moviesdb;

Initialize tables and data with: 

/resources/data/moviesdb_dump.sql

or if you wish just to insert data execute these scripts in the following order:
1) /resources/data/actors.sql
2) /resources/data/movies.sql
3) /resources/data/movies_actors.sql
   
EXECUTE __after_insert_sequence.sql__ (sets base_sequence to last inserted value) to avoid ID collisions interacting with API.

(Schema and Data initialization properties are disabled in application.properties)

####Required Env Vars:
- DATABASE_HOST (e.g. localhost)
- DATABASE_PORT (e.g. 5432)
- DATABASE_USERNAME (e.g. postgres)
- DATABASE_PASSWORD (e.g. postgres)


####Project Structure Details

```
com.msampietro.springdownloadmultiplepools
│
└───config (Datasources config)
│   │   MainDatasourceConfig.java (**important)
│   │   ProcessingDatasourceConfig.java (**important)
│   │   SerializableToStringConverter.java (IGNORE - Serializable error workaround)
│   │   WebMvcConfig.java (IGNORE - Serializable error workaround)
│   │
│   
└───exception (ControllerAdvice and custom exceptions)
│   │   ...
│ 
└───misc (Utils and the CSVWriterWrapper interface & impl)
│   │   Utils.java (IGNORE - base service class utilities)
│   │   CSVWriter.java (**important - csv interface)
│   │   CSVWriterWrapper.java (**important - csv impl with univocity parsers)
│
│  
└───module (Domain classes)
    │
    └───base (Base abstract classes and interfaces for controllers, services, models, dtos, etc.)
    │   │
    │   └───service
    │       │   ExportService.java (**important - the export interface)
    │       │   BaseExportService.java (**important - the export abstract class)
    │
    └───actor (actor domain classes and the ActorExportServiceImpl.java)
    │   │   ...
    │
    └───movie (movie domain classes and the MovieExportServiceImpl.java)
    │   │   ...
    │
    └───review (review domain classes - no download implemented)
        │   ...
    
```

