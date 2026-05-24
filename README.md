# 🚆 Train Booking API

A RESTful API for managing train bookings, built with Spring Boot. Supports user authentication, train/station management, seat allocation, and ticket booking.

**Base URL:** `http://localhost:8080`  
**API Version:** v0  
**OpenAPI:** 3.1.0

---

## Table of Contents

- [Authentication](#authentication)
- [User Management](#user-management)
- [Trains](#trains)
- [Stations](#stations)
- [Train Stops](#train-stops)
- [Seats](#seats)
- [Bookings](#bookings)
- [Data Models](#data-models)

---

## Authentication

All protected endpoints require a Bearer token in the `Authorization` header:

```
Authorization: Bearer <access_token>
```

### Register

**POST** `/api/v1/auth/register`

Creates a new user account.

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "secret123",
  "confirmPassword": "secret123",
  "phoneNumber": "+91-9876543210"
}
```

| Field | Type | Required | Notes |
|---|---|---|---|
| firstName | string | ✅ | Min 1 char |
| lastName | string | ✅ | Min 1 char |
| email | string | ✅ | Valid email |
| password | string | ✅ | Min 5 chars |
| confirmPassword | string | ✅ | Must match password |
| phoneNumber | string | ✅ | Pattern: `^\+?[0-9. ()-]{7,25}$` |

---

### Login

**POST** `/api/v1/auth/login`

Authenticates a user and returns access and refresh tokens.

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "secret123"
}
```

**Response:**
```json
{
  "access_token": "eyJ...",
  "refresh_token": "eyJ...",
  "token_type": "Bearer"
}
```

---

### Refresh Token

**POST** `/api/v1/auth/refresh`

Issues a new access token using a valid refresh token.

**Request Body:**
```json
{
  "refresh_token": "eyJ..."
}
```

**Response:** Same as login response.

---

## User Management

### Update Profile

**PUT** `/api/v1/users/me`

Updates the authenticated user's profile information.

**Request Body:**
```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "dateOfBirth": "1995-06-15"
}
```

---

### Change Password

**PUT** `/api/v1/users/me/password`

Changes the authenticated user's password.

**Request Body:**
```json
{
  "currentPassword": "oldpassword",
  "newPassword": "newpassword",
  "confirmNewPassword": "newpassword"
}
```

---

### Deactivate Account

**POST** `/api/v1/users/me/deactivate`

Temporarily deactivates the authenticated user's account.

---

### Reactivate Account

**POST** `/api/v1/users/me/reactivate`

Reactivates a previously deactivated account.

---

### Delete Account

**DELETE** `/api/v1/users/me`

Permanently deletes the authenticated user's account.

---

## Trains

### Add Trains

**POST** `/api/v1/trains`

Adds one or more trains to the system.

**Request Body:**
```json
[
  { "name": "Rajdhani Express", "code": "12301" },
  { "name": "Shatabdi Express", "code": "12002" }
]
```

| Field | Type | Required | Notes |
|---|---|---|---|
| name | string | ✅ | Min 1 char |
| code | string | ✅ | Min 1 char |

---

### Get Seats by Train

**GET** `/api/v1/trains/{id}/seats`

Returns all seats for a given train.

**Path Parameters:**

| Parameter | Type | Description |
|---|---|---|
| id | integer (int64) | Train ID |

**Response:**
```json
[
  {
    "coachNumber": "B1",
    "seatNumber": 32,
    "seatType": "LOWER_BERTH"
  }
]
```

---

## Stations

### Add Stations

**POST** `/api/v1/stations`

Adds one or more stations.

**Request Body:**
```json
[
  { "name": "New Delhi", "code": "NDLS" },
  { "name": "Mumbai Central", "code": "BCT" }
]
```

| Field | Type | Required |
|---|---|---|
| name | string | ✅ |
| code | string | ✅ |

---

## Train Stops

### Add Train Stops

**POST** `/api/v1/train-stops`

Adds one or more stops to a train route.

**Request Body:**
```json
[
  {
    "trainId": 1,
    "stationId": 2,
    "stopOrder": 1,
    "arrivalTime": "2024-12-01T06:00:00",
    "departureTime": "2024-12-01T06:15:00"
  }
]
```

| Field | Type | Required |
|---|---|---|
| trainId | integer (int64) | ✅ |
| stationId | integer (int64) | ✅ |
| stopOrder | integer (int32) | ✅ |
| arrivalTime | datetime | ✅ |
| departureTime | datetime | ✅ |

---

### Update Train Stop

**PUT** `/api/v1/train-stops/{id}`

Updates the arrival/departure times for a specific stop.

**Path Parameters:**

| Parameter | Type | Description |
|---|---|---|
| id | integer (int64) | Train Stop ID |

**Request Body:**
```json
{
  "arrivalTime": "2024-12-01T07:00:00",
  "departureTime": "2024-12-01T07:10:00"
}
```

---

### Delete Train Stop

**DELETE** `/api/v1/train-stops/{id}`

Removes a train stop by ID.

---

## Seats

### Add Seats

**POST** `/api/v1/seats`

Adds one or more seats to a train.

**Request Body:**
```json
[
  {
    "coachNumber": "B1",
    "seatNumber": 32,
    "seatType": "LOWER_BERTH",
    "trainId": 1
  }
]
```

| Field | Type | Required | Notes |
|---|---|---|---|
| coachNumber | string | ✅ | e.g. "B1", "A2" |
| seatNumber | integer (int32) | ✅ | |
| seatType | enum | ✅ | See seat types below |
| trainId | integer (int64) | ✅ | |

**Seat Types:**
- `UPPER_BERTH`
- `LOWER_BERTH`
- `MIDDLE_BERTH`
- `SIDE_UPPER_BERTH`
- `SIDE_LOWER_BERTH`

---

### Delete Seat

**DELETE** `/api/v1/seats/{id}`

Deletes a seat by ID.

---

## Bookings

### Book a Ticket

**POST** `/api/v1/bookings`

Books a ticket for a user on a specified train and route.

**Request Body:**
```json
{
  "userId": 1,
  "trainId": 2,
  "sourceStationId": 3,
  "destinationStationId": 5,
  "journeyDate": "2024-12-25"
}
```

| Field | Type | Required | Notes |
|---|---|---|---|
| userId | integer (int64) | ✅ | Min 1 |
| trainId | integer (int64) | ✅ | Min 1 |
| sourceStationId | integer (int64) | ✅ | Min 1 |
| destinationStationId | integer (int64) | ✅ | Min 1 |
| journeyDate | date | ✅ | Format: `YYYY-MM-DD` |

**Response:**
```json
{
  "bookingId": 101,
  "trainName": "Rajdhani Express",
  "coachNumber": "B1",
  "seatNumber": 32,
  "seatType": "LOWER_BERTH",
  "sourceStation": "New Delhi",
  "destinationStation": "Mumbai Central",
  "journeyDate": "2024-12-25",
  "bookingStatus": "BOOKED",
  "fare": 1250,
  "bookedAt": "2024-12-01T10:30:00"
}
```

---

### Get My Bookings

**GET** `/api/v1/bookings/my`

Returns all bookings for the currently authenticated user.

---

### Get Booking by ID

**GET** `/api/v1/bookings/{id}`

Returns a specific booking by its ID.

**Path Parameters:**

| Parameter | Type | Description |
|---|---|---|
| id | integer (int64) | Booking ID |

---

### Get Bookings by User ID

**GET** `/api/v1/bookings/user/{userId}`

Returns all bookings for a specific user (admin use).

**Path Parameters:**

| Parameter | Type | Description |
|---|---|---|
| userId | integer (int64) | User ID |

---

### Cancel Booking

**DELETE** `/api/v1/bookings/{id}`

Cancels and deletes a booking by ID.

---

## Data Models

### AuthenticationResponse

| Field | Type |
|---|---|
| access_token | string |
| refresh_token | string |
| token_type | string |

### TicketResponse

| Field | Type | Notes |
|---|---|---|
| bookingId | integer | |
| trainName | string | |
| coachNumber | string | |
| seatNumber | integer | |
| seatType | enum | `UPPER_BERTH`, `LOWER_BERTH`, `MIDDLE_BERTH`, `SIDE_UPPER_BERTH`, `SIDE_LOWER_BERTH` |
| sourceStation | string | |
| destinationStation | string | |
| journeyDate | date | |
| bookingStatus | enum | `BOOKED`, `CANCELLED`, `WAITING`, `COMPLETED` |
| fare | integer | |
| bookedAt | datetime | |

### SeatResponse

| Field | Type |
|---|---|
| coachNumber | string |
| seatNumber | integer |
| seatType | enum |

---

## Error Handling

All endpoints return HTTP `200 OK` on success. Standard HTTP error codes apply for failures (e.g., `400 Bad Request`, `401 Unauthorized`, `404 Not Found`).

---

## Getting Started

1. **Register** a user via `POST /api/v1/auth/register`
2. **Login** via `POST /api/v1/auth/login` to get tokens
3. **Add stations**, **trains**, **train stops**, and **seats** (admin)
4. **Book tickets** via `POST /api/v1/bookings`
5. **View bookings** via `GET /api/v1/bookings/my`
