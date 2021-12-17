# Design Doc
**[Click Here](./Design.md)**

# Run

- Open the project by IDEA best and run the main function in Main.java

# Database Setup

- Install Docker Desktop for (Mac)[https://docs.docker.com/desktop/mac/install/]
- Start Docker Desktop and run:
```
docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=bankatm postgres:latest
```