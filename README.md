# Vehicle HMI Ride Screen

## Overview

This project is designed to simulate a Human-Machine Interface (HMI) for a vehicle. It focuses on creating a dynamic and interactive ride screen that displays essential ride information, such as speed, ride progress, estimated time of arrival (ETA), and vehicle controls like indicator blinking and motor status. The project uses **Jetpack Compose** for UI development, with smooth animations and real-time updates to simulate a realistic driving experience.

## Features

- **Ride Progress Display**: Visual feedback showing the distance covered and remaining.
- **Speedometer**: A digital speed indicator with animated transitions.
- **Motor On/Off**: Toggle the motor on or off and simulate speed changes.
- **Indicators**: Left and right indicator controls with blinking animation.
- **Dynamic Background**: The background color changes based on the speed of the vehicle.
- **Milestone Tracking**: Tracks and displays milestones such as 10 km ridden.
- **Estimated Time of Arrival (ETA)**: Displays the estimated time to reach the destination based on current speed.

## Technologies Used

- **Kotlin**: Main programming language.
- **Jetpack Compose**: UI framework for building responsive and dynamic UIs.
- **Material Design 3**: For UI components and design consistency.

## Usage

- The app displays the current speed and distance covered, simulating a ride.
- The motor can be toggled on or off, and the ride progress is displayed accordingly.
- Users can interact with the left and right indicator buttons to toggle indicator states.
- The speed changes dynamically and will trigger different background gradients based on speed levels.
- Milestones (e.g., 10 km) are shown once they are reached, along with the ETA for the destination.

## Acknowledgements

- Thanks to [Jetpack Compose](https://developer.android.com/jetpack/compose) for providing a flexible UI toolkit.
- [Kotlin](https://kotlinlang.org/) for a powerful programming language.
- [Material Design 3](https://material.io/) for UI guidelines and components.
