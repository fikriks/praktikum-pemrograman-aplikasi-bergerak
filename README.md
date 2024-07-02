# Ngabsen App

Ngabsen is a basic attendance management application developed using Kotlin in Android Studio, paired with a Laravel backend. This project serves as a template for starting a new attendance application with Kotlin and Laravel.

## Table of Contents

- [Ngabsen App](#ngabsen-app)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
      - [Backend (Laravel)](#backend-laravel)
      - [Frontend (Kotlin)](#frontend-kotlin)
  - [Firebase Configuration](#firebase-configuration)
    - [Backend (Laravel)](#backend-laravel-1)
    - [Frontend (Kotlin)](#frontend-kotlin-1)
  - [Building and Running](#building-and-running)
    - [Backend](#backend)
    - [Frontend](#frontend)
  - [Contributing](#contributing)

## Introduction

Ngabsen is an application that allows users to record daily attendance, manage journal entries, and monitor attendance history. It is built with Kotlin for the Android app and Laravel for the backend services.

## Getting Started

These instructions will help you set up and run the project on your local machine for development and testing purposes.

### Prerequisites

- Android Studio (latest version recommended)
- Kotlin (included in Android Studio)
- JDK 8 or higher
- PHP 8.3 or higher
- Composer
- Firebase Account

### Installation

#### Backend (Laravel)

1. **Clone the repository:**

   ```sh
   git clone https://github.com/fikriks/praktikum-pemrograman-aplikasi-bergerak.git
   cd praktikum-pemrograman-aplikasi-bergerak/backend-php-laravel
   ```

2. **Install dependencies:**

   ```sh
   composer install
   ```

3. **Copy the example environment file and set the environment variables:**

   ```sh
   cp .env.example .env
   php artisan key:generate
   ```

4. **Edit the `.env` file and set your database and Firebase credentials.**

5. **Migrate the database:**

   ```sh
   php artisan migrate --seed
   ```

6. **Start the Laravel development server:**

   ```sh
   php artisan serve
   ```

#### Frontend (Kotlin)

1. **Clone the repository:**

   ```sh
   git clone https://github.com/fikriks/praktikum-pemrograman-aplikasi-bergerak.git
   cd praktikum-pemrograman-aplikasi-bergerak/frontend-kotlin
   ```

2. **Open the project in Android Studio:**
   - Start Android Studio.
   - Select "Open an existing Android Studio project".
   - Navigate to the `frontend-kotlin` directory and select it.

3. **Sync the project:**
   - Once the project is open in Android Studio, it will automatically start syncing. If it does not, select `File > Sync Project with Gradle Files`.

## Firebase Configuration

### Backend (Laravel)

1. **Set up Firebase Project:**
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project or select an existing one.

2. **Add a Web App to Firebase:**
   - Register your web app and follow the setup instructions.
   - Download the `private-key.json` file from the Firebase console.

3. **Place the `private-key.json` file:**
   - Place the `private-key.json` file in the `backend-php-laravel` directory.

4. **Configure Firebase in Laravel:**
   - Open the `.env` file and add your Firebase credentials:

     ```env
     FIREBASE_CREDENTIALS=private-key.json
     ```

### Frontend (Kotlin)

1. **Set up Firebase Project:**
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project or select an existing one.

2. **Add an Android App to Firebase:**
   - Register your Android app with the package name `com.example.absen`.
   - Download the `google-services.json` file.

3. **Place the `google-services.json` file:**
   - Place the `google-services.json` file in the `app` directory of your Android project (`frontend-kotlin/app`).

4. **Sync the project:** 
   - Sync your project with Gradle files to ensure all dependencies are correctly set up.

## Building and Running

### Backend

- Ensure your Laravel server is running with:

  ```sh
  php artisan serve
  ```

### Frontend

1. **Build the project:**
   - Select `Build > Make Project` or use the shortcut `Ctrl+F9` to build the project.

2. **Run the application:**
   - Connect an Android device or start an emulator.
   - Click the "Run" button or select `Run > Run 'app'`.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes.
