# Database Setup

- Install Docker Desktop for (Mac)[https://docs.docker.com/desktop/mac/install/]
- Start Docker Desktop and run:
```
docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=bankatm postgres:latest
```