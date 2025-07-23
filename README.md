# Spring Data MongoDB - Custom Method Query Derivation Issue

This repository demonstrates a known issue in Spring Data MongoDB where the query derivation mechanism incorrectly interferes with custom repository implementations. When a custom method name contains query keywords (e.g., `By`, `Find`, `Exists`), Spring attempts to derive a query from its name, ignoring the provided custom implementation and causing a `PropertyReferenceException`.

Spring Data Mongo Github Issue: [5032](https://github.com/spring-projects/spring-data-mongodb/issues/5032) 

-----

## Problem Statement 📝

Spring Data MongoDB currently lacks an elegant way to **disable automatic query derivation** for custom repository methods whose names happen to contain query keywords (like `By`, `And`, `Or`, etc.). This limitation forces developers into unnatural naming conventions or package structures to avoid runtime errors, even when a perfectly valid custom implementation is provided.

The core issue is that Spring Data's query derivation takes precedence over the custom implementation lookup, leading to a `PropertyReferenceException` if the method name doesn't map to a valid property on the domain entity.

-----

## Example Code That Fails ❌

Here is a standard implementation using the custom fragment pattern that fails at runtime.

#### Custom Repository Fragment

```java
// Custom repository fragment
public interface CustomSnsRepository {
    boolean checkIfExistsByArn(String arn);  // Method name contains the keyword "By"
}
```

#### Custom Implementation

```java
// Custom implementation with custom logic
public class CustomSnsRepositoryImpl implements CustomSnsRepository {
    private final MongoTemplate mongoTemplate;

    public CustomSnsRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean checkIfExistsByArn(String arn) {
        Query query = new Query(Criteria.where("arn").is(arn));
        return mongoTemplate.exists(query, SnsTopicEntity.class);
    }
}
```

#### Main Repository

```java
@Repository
public interface SnsTopicRepository extends MongoRepository<SnsTopicEntity, String>, CustomSnsRepository {
}
```

-----

### Error Produced

When the application context loads, it fails with the following exception because Spring tries to parse `checkIfExistsByArn` as a derived query instead of using the custom implementation.

```
Caused by: org.springframework.data.mapping.PropertyReferenceException:
No property 'checkIfExistsByArn' found for type 'SnsTopicEntity'
```

-----

## Latest Discoveries and Workaround 🧐

Through further testing, a workaround was discovered:

* **The issue is resolved if the implementation class (`CustomSnsRepositoryImpl`) is moved into the same package as the repository interfaces (`CustomSnsRepository` and `SnsTopicRepository`).**

This suggests the problem lies in the component scanning and prioritization logic. While this workaround allows the application to function, it imposes rigid package structure requirements and doesn't address the underlying design flaw.

-----

## Proposed Solution: An Annotation-Driven Approach

A clean, explicit, and intuitive solution would be to introduce an annotation like `@NoQueryDerivation` to signal to Spring Data that it should not attempt to derive a query from the method's name.

```java
public interface CustomSnsRepository {
    @NoQueryDerivation // Proposed annotation
    boolean checkIfExistsByArn(String arn);
}
```

### Benefits of this Approach

* ✅ **Clear Intent**: Explicitly indicates a custom implementation is provided.
* ✅ **Natural Naming**: Allows descriptive method names without triggering unwanted behavior.
* ✅ **Self-Documenting**: Makes code intent obvious to other developers.
* ✅ **Backward Compatible**: Doesn't affect existing functionality for those who rely on query derivation.
* ✅ **Consistent**: Follows Spring's popular annotation-driven design philosophy.
* ✅ **Maintainable**: Requires no complex XML or programmatic configuration.

-----

## Environment

* **Spring Boot**: 3.5.3
* **Spring Data MongoDB**: Latest
* **Java**: 21