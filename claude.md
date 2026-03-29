# TracingCoroutineInterceptor

## What we're building
A Kotlin coroutine tracer using ContinuationInterceptor that:
- Intercepts every resumeWith() call
- Tracks parent-child Job relationships
- Logs thread, timestamp, coroutine name per resume event

## Key design decisions
- Use System.identityHashCode(job) as stable coroutine ID
- Track parentId via job.parent at intercept time
- CoroutineEvent data class: id, parentId, name, thread, timestamp, type

## Project setup
- Plain Kotlin JVM, Gradle KTS
- kotlinx-coroutines-core:1.8.1
- kotlinx-coroutines-test:1.8.1 for runTest
