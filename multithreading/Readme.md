# 🏦 Multithreaded Banking System

This assignment simulates a real-world banking system where multiple users (threads) concurrently perform operations on shared bank accounts. The objective is to **write thread-safe code**, **understand race conditions**, and **apply synchronization primitives effectively**.

---

## 🚧 Problem Statement

Build a banking system that manages multiple accounts, each of which supports:

- Deposits
- Withdrawals
- Transfers to other accounts

These operations must be performed by multiple threads running concurrently, and the **data integrity** of the system must be guaranteed. At no point should any account have a **negative balance** due to concurrency issues. 

---

## 🧩 Functional Requirements

### 1. Account
- Has an `id` (String) and a `balance` (Int).
- Supports:
  - `deposit(amount: Int)`
  - `withdraw(amount: Int)`
  - `transferTo(target: Account, amount: Int)`

### 2. Bank
- Manages a collection of `Account`s.
- Provides methods for:
  - Fetching accounts
  - Initiating transfers between accounts

### 3. Multithreaded Simulation
- Multiple threads (users) operate on shared accounts.
- Each thread performs a mix of deposits, withdrawals, and transfers.
- You must avoid:
  - Race conditions
  - Deadlocks
  - Inconsistent state

---

## 🧪 Test Plan (Kotlin)

| Component | Test Case | Description |
|----------|------------|-------------|
| `Account` | Deposit & Withdraw | Test for correct balance update in single-threaded context |
| `Account` | Overdraw prevention | Ensure withdrawal fails when insufficient funds |
| `Account` | Concurrent deposit | Spawn 10 threads depositing in parallel. Assert final balance. |
| `Account` | Concurrent withdraw | Same as above with concurrent withdrawals |
| `Bank` | Transfer between accounts | Verify balances are updated correctly and atomically |
| `Bank` | Concurrent transfers | Simulate multiple transfers between same/different accounts |
| `System` | Total balance consistency | Assert that total money in system remains constant |
| `Deadlock` | Lock acquisition order | Verify that deadlock does not occur during circular transfers |

---

## 🔐 Constraints

- You **must not use** global `synchronized` on the entire bank system.
- You **must** use fine-grained locking per `Account`.
- Prefer `ReentrantLock` over `synchronized` blocks for more control.
- Locks should always be acquired in a **consistent order** to avoid deadlocks.

---

## ⚙️ Example

```kotlin
val bank = Bank()
val acc1 = bank.createAccount("A", 1000)
val acc2 = bank.createAccount("B", 1000)

val threads = List(100) {
    Thread {
        repeat(10) {
            acc1.transferTo(acc2, 10)
            acc2.transferTo(acc1, 10)
        }
    }
}

threads.forEach { it.start() }
threads.forEach { it.join() }

println("Total balance: ${acc1.balance + acc2.balance}")
```

## 🧠 Concepts to Study Before Attempting
 - Thread creation in Kotlin
 - Shared mutable state and visibility issues
 - Synchronized blocks and methods
 - Deadlocks and strategies to avoid them
 - ReentrantLock vs synchronized
 - Atomic operations (AtomicInteger)
 - Executors and thread pools (optional)
 - Race conditions and consistency checks

## 🛠 Stretch Goals
 - Implement thread-safe logging of operations
 - Add timeout-based locking
 - Introduce a fraud detection system that alerts when large transfers happen frequently
 - Implement Executors and simulate a fixed thread pool of bank workers

## ✅ Completion Checklist
  - Implement Account class with thread-safe deposit/withdraw
  - Implement thread-safe transferTo
  - Build Bank class that holds accounts
  - Simulate multi-threaded operations
  - Write unit tests for each critical scenario
  - Ensure total balance is consistent at the end
  - Handle and prevent deadlocks
