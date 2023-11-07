# Backend of my library app.

This is the repo of my backend. You can find the frontend at: [https://stately-starship-e19365.netlify.app](https://stately-starship-e19365.netlify.app)

## Implemented with Java Spring, PostgreSQL, AWS SDK S3 and Docker

This backend uses multiple features of Java Spring. Database used is Postgres. Every book has cover images and they are uploaded to AWS S3 -bucket. The image arrives from frontend to backend and then is uploaded to AWS by using AWS SDK Client.

## REST API

This backend uses Spring Boot structure
- Entities as data structures
- Controllers for handling incoming and returning HTTP-requests.
- Services for handling the business logic
- Repositories for persisting and accessing data.
- In case needed: Data Transfer Object to convert data to a correct form.

The user can:
- Log in and log out.
- Borrow book copies and return them

The admin can:
- Add new books, authors and copies
- Delete and update existing books, authors and copies

The backend makes sure that:
- Only authenticated users can borrow and return books
- That the database will not be broken (i.e. Backend checks that Admin won't remove rows that are foreign keys in another table row)

## Authentication
- Implemented with Spring Security. Passwords are saved into the database as encoded.
- Authentication is implemented with tokens saved in headers. JWT Filter and SecurityConfig will check that the session is not expired and the holder of the token is allowed to proceed the actions she is doing.