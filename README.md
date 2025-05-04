# IRCTC Train Reservation System - Low Level Design

## System Architecture

This document outlines the low-level design of a Java-based train reservation system modeled after IRCTC (Indian Railway Catering and Tourism Corporation). The system implements a service-oriented architecture with persistent JSON-based data storage.

### Component Overview

```
com.example.IRCTC/
├── Entities/            # Domain objects representing core business concepts
├── Service/             # Business logic implementation
├── Util/                # Helper utilities and common functionality  
└── LocalDb/             # JSON-based persistent storage
```

## Core Components Design

### Entity Layer

The system defines the following domain objects:

1. **User**
   - Represents a registered system user
   - Contains authentication information and booking history
   - Properties: name, hashedPassword, ticketList

2. **Train**
   - Represents a train with routing and seating information
   - Properties: trainId, stations (route), seats (availability matrix)

3. **Ticket**
   - Represents a booking made by a user
   - Properties: ticketId, trainId, seatNumber

### Service Layer

#### UserService
Responsible for user authentication, registration, and ticket management operations.

**Key Methods:**
- `loginUser()`: Authenticates user credentials against stored records
- `signUp(User)`: Registers a new user and persists to storage
- `fetchBookings()`: Retrieves user's booked tickets
- `cancelBooking(String)`: Removes a booking by ticket ID
- `getTrains(String, String)`: Searches for trains between locations
- `fetchSeats(Train)`: Retrieves current seat availability
- `bookTrainSeatByNumber(Train, int)`: Books a specific seat

**Implementation Details:**
- Uses ObjectMapper for JSON serialization/deserialization
- Implements secure password checking via UserServiceUtil
- Contains necessary validation logic for booking operations
- Handles seat matrix operations with row/column calculations

#### TrainService
Manages train data and search operations.

**Implementation Details:**
- Validates train routes for source-destination relationship
- Handles file I/O operations for train data persistence
- Implements efficient train search using Java streams

### Utility Layer

#### UserServiceUtil
Provides helper methods for user management.

**Key Methods:**
- `checkPassword(String, String)`: Validates password against stored hash

### Data Persistence

The system uses a lightweight JSON-based persistence approach:

1. **Users.json**
   - Schema: Array of User objects
   - Path: `src/main/java/com/example/IRCTC/LocalDb/Users.json`

2. **Trains.json**
   - Schema: Array of Train objects
   - Path: `src/main/java/com/example/IRCTC/LocalDb/Trains.json`

**Implementation Notes:**
- Jackson ObjectMapper used for JSON processing
- File operations wrapped in try-catch blocks for error handling
- In-memory caching of data for improved performance

## Key Algorithms

### Train Search Algorithm
```
1. Load all trains from persistent storage
2. Filter trains by matching source and destination stations
3. Verify that source station appears before destination station in route
4. Return filtered list to caller
```
### Seat Matrix
Trains use a 2D list representation for seat availability:
- 5×5 matrix (25 seats total)
- Values: 0 (available), 1 (booked)
- Access pattern: `seats.get(row).get(col)`

### Train Route
Routes are represented as ordered lists of station names (lowercase):
- Position in list determines station order
- Source must appear before destination for valid routes

### Environment Requirements
- Java 8+
- Jackson library for JSON processing
- File system access for data persistence

---
