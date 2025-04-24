📦 Assignment: Intelligent Parcel Routing System
Aims
To implement and evaluate various data structures (queues, priority queues, hash maps, graphs).

To explore algorithm design, simulation, and performance benchmarking.

To apply real-world problem-solving skills in the context of logistics and routing.

Real-World Scenario:
Imagine a national postal service that manages thousands of parcels per day. Each parcel needs to be routed from a source warehouse to a destination through a network of sorting centers (nodes). Each sorting center has limited processing capacity and implements different queueing disciplines to manage the parcels waiting to be forwarded. The system must support:

FIFO, LIFO, and Priority queue routing

Dynamically routing parcels based on destination proximity, urgency, or size

Logging and statistics collection (wait times, hops, re-routing due to congestion)

Problem Breakdown
Phase 1: Core Data Structures (Checkpoint 1)
Implement a polymorphic ParcelQueue<T> interface:

kotlin
Copy
Edit
interface ParcelQueue<T> {
    fun enqueue(item: T)
    fun peek(): T?
    fun dequeue(): T?
    fun isEmpty(): Boolean
    fun size(): Int
}
Implement the following classes:

FifoParcelQueue<T>

LifoParcelQueue<T>

PriorityParcelQueue<T> using a natural ordering (e.g., smaller delivery time = higher priority)

➡ Checkpoint Deliverables:

Interfaces and three implementations

Unit tests for each using Int, String, and a custom Parcel class

Phase 2: Routing Network (Checkpoint 2)
Model:
A SortingCenter class that implements:

acceptParcel(parcel: Parcel)

forwardParcel()

A Router class that connects centers and determines the next hop

Use graphs (adjacency lists) to model the network and allow route lookups.

➡ Checkpoint Deliverables:

SortingCenter with pluggable queue discipline

Router that supports Dijkstra’s algorithm for shortest path routing

A method to simulate parcel traversal from source to destination

Phase 3: Simulation and Monitoring (Checkpoint 3)
Enhance the system with:

A Clock class for tracking virtual time

Metrics in SortingCenter: average wait time, max queue length, parcels processed

Parcel priority based on:

Delivery deadline (time-sensitive)

Size (smaller = prioritized for faster routes)

➡ Checkpoint Deliverables:

Simulation runner that spawns parcels at various centers

Report at end showing:

Total parcels delivered

Average time in system

Congested nodes

Phase 4: Extensions (Optional)
Add probabilistic routing (based on congestion)

Add a MeasurableParcelQueue to track dynamic queue lengths over time

Allow dynamic rerouting when a node’s queue exceeds a threshold

📋 Evaluation Criteria

Checkpoint	Criteria
1 - Data Structures	Correct interface usage, test coverage, discipline behavior
2 - Network Routing	Working node-to-node parcel forwarding, proper routing via graph
3 - Simulation	Accurate simulation logic, metrics, clear reports
