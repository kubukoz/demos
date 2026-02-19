# Debug Adapter Protocol Server for Cats-Effect

## What DAP is

DAP is a protocol (like LSP but for debugging) that sits between an IDE and a debug runtime. It defines messages for breakpoints, stepping, stack frames, variable inspection, etc. VS Code, IntelliJ, and others speak DAP natively.

## The core challenge

Cats-Effect fibers are **logical threads** that don't map 1:1 to JVM threads. A single JVM thread may execute many fibers over time, and a single fiber may hop between threads. Standard JVM debuggers (which Scala DAP servers like Metals use via JDI) know nothing about this - they see threads, stack frames, and monitors.

## Architecture options

### Option 1: Standalone DAP server (separate from Scala DAP)

- Implement a DAP server that connects to the JVM via JDI (or the newer JDWP) just like a normal debugger, but **interprets** what it sees through a fiber-aware lens.
- It would need to understand CE's internal data structures (`IOFiber`, work-stealing pool queues, etc.) to reconstruct the logical fiber view.
- Problem: you can't easily run two DAP servers attached to the same JVM debug port simultaneously. JDI typically expects a single debugger connection.

### Option 2: DAP proxy / middleware (most practical)

- Sit **between** the IDE and an existing Scala DAP server (e.g. Metals/Bloop's debug server).
- Intercept and enrich DAP messages. For example:
  - When the IDE requests threads, also synthesize "virtual threads" representing fibers.
  - When showing a stack frame for a fiber, reconstruct the logical stack from CE's `IOFiber.tracingEvents` or `Tracing` infrastructure.
  - Add custom fiber-aware breakpoints (e.g., "break when fiber X resumes").
- This **composes** with the existing Scala debugger - you get normal Scala debugging plus fiber awareness.

### Option 3: CE runtime instrumentation + custom DAP

- Instrument the CE runtime (via a Java agent, or a custom CE build) to expose fiber state over a side-channel (socket, JMX, etc.).
- A dedicated DAP server reads this side-channel for fiber info and uses JDI for everything else.
- This is how some Kotlin coroutine debuggers work - the Kotlin runtime has explicit coroutine debugger support baked in.

## What a useful CE DAP would need to expose

1. **Fiber listing** - all live fibers, their states (running, suspended, waiting on `Deferred`, etc.)
2. **Logical stack traces** - CE already has `IOFiber` tracing; surface this through DAP's `StackTrace` response
3. **Fiber-aware stepping** - "step over" means "next meaningful operation in this fiber," not "next JVM instruction on this thread"
4. **Resource/scope visualization** - show `Resource` scopes, what's been acquired, the supervision tree
5. **Breakpoints on fiber events** - break on fork, break on cancel, break on error

## Can it run alongside a Scala DAP?

**Proxy model: yes.** This is the most viable approach. The proxy wraps the existing Metals DAP session and adds fiber-specific capabilities. The IDE sees a single DAP server. Internally:

```
IDE <--DAP--> CE Proxy <--DAP--> Metals debug server <--JDI--> JVM
                 |
                 +--- reads CE internals via JDI/instrumentation
```

**Two separate DAP servers: not really.** Only one debugger can attach to a JDWP port at a time. You could technically run two JVMs or use a debug multiplexer, but it's painful.

## Precedent

- **Kotlin coroutines**: IntelliJ has coroutine-aware debugging built into their debugger. It works by having the Kotlin runtime cooperate (there's a `kotlinx.coroutines.debug` module that registers coroutines for inspection). Closest to Option 3.
- **Go goroutines**: Delve understands goroutines natively because it's the only Go debugger and it understands the runtime.
- **Project Loom virtual threads**: JDI is being extended to understand virtual threads, which is the JVM-native version of this problem.

## Recommended starting point

1. Start with CE's existing **fiber tracing** (`cats.effect.tracing`) - it already captures logical stack traces.
2. Build a **DAP proxy** that wraps Metals' debug adapter.
3. Use JDI to read `IOFiber` internals at breakpoints to reconstruct fiber state.
4. Long-term, consider adding a **debug agent** to CE itself (like `kotlinx-coroutines-debug`) that maintains a fiber registry for the debugger to query efficiently.

The proxy approach lets you ship something useful without forking CE or replacing the existing Scala debugging story.
