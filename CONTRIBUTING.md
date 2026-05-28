# Contributing to ERP Nexa

First off, thank you for considering contributing to ERP Nexa! It's people like you that make open source and enterprise software such a great community.

## Development Workflow
1. **Branching Strategy:** We use GitFlow. Create a feature branch off of `develop` (e.g., `feature/HR-123-add-payroll`).
2. **Local Environment:** Ensure you have Java 17, Node 20+, and Docker installed. Spin up dependencies using `docker-compose up -d`.
3. **Commit Messages:** Follow the Conventional Commits specification (e.g., `feat: added caching to inventory`).
4. **Testing:** All new features must include JUnit tests for backend code. Run `mvn clean verify` before opening a Pull Request.

## Code Style
- Java: Follow Google Java Style Guide.
- TypeScript/React: Follow standard ESLint rules configured in the Next.js project.

## Pull Requests
- PRs must pass the Jenkins CI pipeline before they can be merged.
- PRs require at least 1 approval from a core maintainer.
