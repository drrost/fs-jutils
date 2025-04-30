# FastStream Java Utilities - Coding Standards

## Introduction

This document outlines the coding standards and best practices for the FastStream Java Utilities (fs-jutils) library. Following these standards ensures code consistency, maintainability, and quality across the project.

## General Principles

1. **Simplicity**: Keep code simple and readable. Avoid unnecessary complexity.
2. **Consistency**: Follow established patterns and conventions throughout the codebase.
3. **Modularity**: Design code to be modular, with clear separation of concerns.
4. **Documentation**: Document all public APIs thoroughly.
5. **Testing**: Write comprehensive tests for all functionality.

## Naming Conventions

1. **Classes**:
   - Use PascalCase (e.g., `FSStringUtils`)
   - All utility classes should have the "FS" prefix
   - Names should be descriptive and indicate functionality
   - Utility classes should end with "Utils" (e.g., `FSDateUtils`)

2. **Methods**:
   - Use camelCase (e.g., `mapToJson`)
   - Names should clearly indicate functionality
   - Boolean methods should start with "is", "has", or similar prefix (e.g., `isInteger`, `nullOrEmpty`)

3. **Variables**:
   - Use camelCase (e.g., `objectMapper`)
   - Use descriptive names that indicate purpose
   - Single-letter variables should only be used for simple loop counters or when conventional (e.g., `i`, `j` for loop indices)

4. **Constants**:
   - Use UPPER_SNAKE_CASE (e.g., `DEFAULT_BUFFER_SIZE`)

5. **Packages**:
   - Use lowercase with dots as separators (e.g., `com.rdruzhchenko.fsjutils.string`)
   - Organize by functionality domain (string, date, file, etc.)

## Code Organization

1. **Package Structure**:
   - Organize classes into packages by functionality domain
   - Keep related classes together
   - Follow the structure outlined in the architecture document

2. **Class Structure**:
   - Place static fields at the top of the class
   - Group related methods together
   - Place private helper methods after public methods
   - Keep classes focused on a single responsibility

3. **Method Organization**:
   - Keep methods short and focused on a single task
   - Extract complex logic into helper methods
   - Limit method length to improve readability (ideally under 30 lines)

## Documentation

1. **JavaDoc**:
   - All public classes and methods must have JavaDoc comments
   - Document parameters, return values, and exceptions
   - Include examples for complex methods
   - Describe thread-safety considerations when applicable

2. **Comments**:
   - Use comments to explain "why", not "what" (the code should be self-explanatory)
   - Keep comments up-to-date with code changes
   - Use TODO comments for planned improvements (format: `// TODO: description`)

## Error Handling

1. **Exceptions**:
   - Use custom exception classes from the `exception` package
   - Include descriptive error messages
   - Document exceptions in JavaDoc
   - Wrap and rethrow lower-level exceptions with appropriate context

2. **Null Handling**:
   - Document null behavior in JavaDoc
   - Perform explicit null checks for parameters
   - Be consistent in how null values are handled (return null, throw exception, etc.)

## Thread Safety

1. **Synchronization**:
   - Document thread-safety guarantees
   - Use proper synchronization for shared resources
   - Prefer immutable objects when possible
   - Use thread-safe collections when appropriate

2. **Static Fields**:
   - Be cautious with mutable static fields
   - Use proper synchronization or volatile keyword when needed
   - Document thread-safety considerations

## Testing

1. **Test Coverage**:
   - Write unit tests for all public methods
   - Include tests for edge cases and error conditions
   - Aim for high test coverage

2. **Test Structure**:
   - Use descriptive test names that indicate what is being tested
   - Follow the Given-When-Then format
   - Group related tests together

## Code Style

1. **Formatting**:
   - Use 4 spaces for indentation (not tabs)
   - Limit line length to 80 characters
   - Use consistent brace style (opening brace on same line)

2. **Imports**:
   - Organize imports alphabetically
   - Avoid wildcard imports
   - Remove unused imports

3. **Whitespace**:
   - Use blank lines to separate logical blocks of code
   - Use consistent spacing around operators and parentheses

## Performance Considerations

1. **Resource Management**:
   - Close resources properly (use try-with-resources)
   - Avoid unnecessary object creation
   - Be mindful of memory usage

2. **Optimization**:
   - Focus on correctness first, then optimize if needed
   - Document performance characteristics for critical methods
   - Use appropriate data structures for the task

## Version Control

1. **Commits**:
   - Write clear, descriptive commit messages
   - Keep commits focused on a single change
   - Reference issue numbers in commit messages when applicable

2. **Branches**:
   - Use feature branches for new development
   - Keep branches up-to-date with the main branch
   - Delete branches after merging

## Conclusion

Following these coding standards ensures that the FastStream Java Utilities library remains maintainable, consistent, and high-quality. All contributors should adhere to these standards when making changes to the codebase.