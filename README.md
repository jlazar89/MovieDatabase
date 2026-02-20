# The Movie Database

This is a simple Android project that demonstrates modern Android development practices.

## Architecture

The project follows the Model-View-Intent (MVI) architecture pattern.

- **Model:** Represents the state of the application.
- **View:** A function that transforms a state into a UI.
- **Intent:** An event that represents a user's intention to change the state.

## Libraries Used

This project uses the following libraries:

- **Core & UI:**
    - Compose
    - Material Icons Extended
- **Unit Testing:**
    - JUnit
- **Instrumented Testing:**
    - AndroidX Test JUnit
    - Espresso Core
    - Compose UI Test JUnit 4
- **Image Loading:**
    - Coil
- **Lifecycle + Navigation:**
    - Lifecycle ViewModel Compose
    - Navigation Compose
- **Hilt:**
- **Retrofit:**
- **Coroutines:**
- **Mockito:**
- **Turbine:**
- **Paging 3:**

## Setup

To build and run this project, you will need to provide your own TMDB API keys.

1. Create a file named `secret.properties` in the root of the project.
2. Add the following lines to the file, replacing the placeholder values with your actual TMDB credentials:

```properties
TMDB_ACCOUNT_ID="YOUR_TMDB_ACCOUNT_ID"
TMDB_ACCESS_TOKEN_KEY="YOUR_TMDB_ACCESS_TOKEN_KEY"
```

3. Sync the project with Gradle.
4. Build and run the app.
