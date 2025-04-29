# FastStream Java Utilities - Architecture Overview

## Introduction

FastStream Java Utilities (fs-jutils) is a collection of utility classes designed to simplify common programming tasks in Java applications. The library provides a set of well-organized, reusable components that handle various operations such as string manipulation, date formatting, file operations, JSON processing, and more.

## High-Level Architecture

The library follows a modular architecture organized by functionality domains. Each domain is represented by a separate package containing specialized utility classes. The architecture is designed to be:

- **Modular**: Functionality is separated into distinct, focused utility classes
- **Stateless**: Most utility classes provide static methods without maintaining state
- **Exception-aware**: Custom exceptions provide clear error information
- **Consistent**: Naming conventions and method signatures follow a consistent pattern

## Package Structure

The library is organized into the following main packages:

```
com.rdruzhchenko.fsjutils
├── date        - Date manipulation utilities
├── exception   - Custom exception classes
├── file        - File and filesystem operations
├── json        - JSON serialization and deserialization
├── random      - Random number generation
└── string      - String manipulation utilities
```

### Package Details

#### date
Contains utilities for date manipulation, formatting, and parsing.
- `FSDateUtils`: Core date manipulation functionality

#### exception
Contains custom exception classes for the library.
- `FSException`: Base exception class for all library exceptions
- `FSDateException`: Date-related exceptions
- `FSFileException`: File-related exceptions
- `FSJsonException`: JSON-related exceptions
- `FSStringException`: String-related exceptions
- `FSValidationException`: Validation-related exceptions

#### file
Contains utilities for file and filesystem operations.
- `FSFileUtils`: File manipulation operations
- `FSFileSystemUtils`: Filesystem operations

#### json
Contains utilities for JSON processing.
- `FSJsonUtils`: JSON serialization and deserialization using Jackson

#### random
Contains utilities for random number generation.
- `FSRandomUtils`: Random number and string generation

#### string
Contains utilities for string manipulation.
- `FSStringUtils`: Core string manipulation functionality
- `FSNameUtils`: Name formatting and manipulation
- `FSPhoneNumberFormatter`: Phone number formatting
- `FSRnokppUtils`: Ukrainian tax identification number (RNOKPP) handling

## Design Patterns and Principles

The library employs several design patterns and principles:

1. **Utility Class Pattern**: Most classes follow the utility class pattern with static methods and private constructors.

2. **Singleton Pattern**: Some classes (like FSJsonUtils) use a singleton pattern for managing shared resources.

3. **Factory Method Pattern**: Used in classes that create and configure objects (e.g., ObjectMapper creation in FSJsonUtils).

4. **Exception Hierarchy**: A custom exception hierarchy with a common base class provides consistent error handling.

5. **Dependency Injection**: Some classes allow for configuration of dependencies (e.g., ObjectMapper in FSJsonUtils).

## Error Handling

The library uses a custom exception hierarchy for error handling:

- All exceptions extend from `FSException`, which extends `RuntimeException`
- Domain-specific exceptions (FSJsonException, FSStringException, etc.) extend FSException
- Exceptions include detailed error messages and can wrap underlying exceptions

## Thread Safety

Most utility classes are designed to be thread-safe by:
- Using stateless methods where possible
- Properly synchronizing access to shared resources
- Using thread-safe data structures when necessary

## Dependencies

The library has minimal external dependencies:
- Jackson for JSON processing
- Standard Java libraries for core functionality

## Extension Points

The library can be extended in several ways:
- Custom implementations of utility interfaces
- Configuration of underlying components (e.g., ObjectMapper in FSJsonUtils)
- Subclassing of utility classes for specialized behavior

## Future Directions

Potential areas for future development include:
- Adding more specialized utility classes
- Improving internationalization support
- Enhancing performance through caching and optimization
- Adding support for modern Java features