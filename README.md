# Weather Dashboard

This project was created for my Mobile App Development midterm project. It displays simulated weather that changes every three seconds.

## Project walkthrough

The project walkthrough is available [in this video](https://youtu.be/g1BHIhSLcKM).

## How it works

- A Kotlin `WeatherData` data class holds a temperature in Fahrenheit and a weather condition.
- A `MutableStateFlow` starts with a default reading and emits a randomly generated reading every three seconds.
- Jetpack Compose observes the flow with `collectAsState()` and updates the dashboard when a new reading arrives.
- Alerts are based on the current temperature: **Heat Wave** at 100°F or above, **Freeze Warning** at 32°F or below, and **Normal** otherwise.
- `LaunchedEffect` runs the update loop and recalculates the alert when the weather state changes.