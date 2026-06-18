# A216765_NurAdlynaBatrisyia_CikguIzwan_Project2

## 📱 Project Overview
* **Application Name:** MoodSpace
* **Course:** TK2323 / TM2213 Mobile Programming
* **Development Period:** 2025–2026
* **Target Audience:** Young adults seeking accessible mental health tracking tools
* **Live e-Portfolio:** [View Live e-Portfolio](https://adlynabatrisyia.github.io/MoodSpace/)

### 👩‍💻 Student & Instructor Details
* **Student Name:** Nur Adlyna Batrisyia binti Rosmadi
* **Matric Number:** A216765
* **Programme:** Bachelor of Information Technology
* **Faculty:** Faculty of Information Science and Technology (FTSM), UKM
* **Instructor:** Cikgu Izwan 

---

## 🌿 Application Description
**MoodSpace** is a comprehensive, free mental health application designed to help users better understand and manage their psychological states [source: 2]. It integrates daily emotional tracking with cognitive behavioral therapy (CBT) techniques, a gratitude journal, sleep tracking, and personalized mental health insights [0.1.1, source: 2]. By assessing how lifestyle variables like physical activity and sleep impact overall mood, the app provides actionable methods for forming healthy daily routines [source: 2].

### 🎯 SDG Alignment
* **SDG Theme:** **SDG 3 - Good Health and Well-Being**
* **Core Mission:** Promoting mental health awareness through accessible daily check-in tools for everyone.

---

## 📺 Demonstration

* **Project 2 VSR Video:** [Watch on YouTube](https://youtu.be/8OhGs3nZuXY?si=Kt0dDg3HXmhHq-jJ)
* **Project 1 VSR Video:** [Watch on YouTube](https://youtu.be/OOnhLNYUy40?si=BNDLaiIe9PAbwjVO)

---

## 🛠 Features & The Four Technical Pillars
MoodSpace has been upgraded from a static, local application into a fully persistent, connected, and hardware-aware system built on the **MVVM architecture** pattern [0.1.1, source: 2].

### 1. Local Persistence (Room Database)
* **Implementation:** Off-line capability via local SQLite using Room .
* **Data Tracked:** Stores private records of user mood levels, gratitude entries, sleep durations, and personal activities [0.1.1, source: 2].
* **Components:** Configured with `ActivityEntity`, `ActivityDao`, `AppDatabase` (Singleton pattern), and an `ActivityRepository` layer.

### 2. Cloud Integration (Firebase Firestore)
* **Implementation:** Secure remote backups and data synchronization.
* **Data Tracked:** Cloud syncs mental health evaluation results once all daily goals are successfully achieved.

### 3. Data from the Internet (Web API via Retrofit)
* **Implementation:** Consumes live third-party endpoints asynchronously using Kotlin Coroutines (`suspend` functions) [0.1.1, source: 2].
* **Feature:** Fetches dynamic motivational and inspirational content from the public [Quotable REST API](https://quotable.io) displayed on a dedicated screen.

### 4. Sensor Integration (Accelerometer)
* **Implementation:** Detects physical device motion natively via `SensorManager` and `SensorEventListener`.
* **Feature:** Shaking the smartphone triggers an interactive breathing exercise popup overlay, built with careful lifecycle hooks (`onResume`/`onPause`) to eliminate battery drain.

---

## 🗺 Screen Navigation Flow
The application contains an expanded navigation graph comprising **8 interactive screens** mapped cleanly inside a single Jetpack Compose `NavHost` [0.1.1, source: 2]:
1. **Home Screen:** Summary dashboard showing daily goals, target progress bars, and basic state shortcuts.
2. **Mood Dialog / Picker:** Popup utilizing 5 emoji tiers (*Great, Good, OK, Poor, Bad*).
3. **Gratitude Page:** Dynamic journal containing text fields and up to 10 unique selection prompts.
4. **Sleep Tracker Screen:** Automatic sleep duration evaluator based on specific input parameters.
5. **Inbox Screen:** List layout delivering curated mental health resources.
6. **Inbox Detail Screen:** Deep-linked screen handling dynamic route string arguments for reading individual articles.
7. **History Screen:** Local record list displaying chronological historical inputs verified through the Room DB engine.
8. **Quotes Screen:** Connected page pulling online data to display live positive affirmations.

---

## 🚀 Tech Stack
* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Declarative UI layout modifiers)
* **Design System:** Material Design 3 (Custom mint-green & sage palette palettes)
* **Architecture:** MVVM Architecture Pattern (Clean separation of UI, StateFlows, ViewModels, and Repositories)
* **Asynchronous Engine:** Kotlin Coroutines & `viewModelScope`
* **Asynchronous Networking:** Retrofit HTTP Client
* **Databases:** Android Room (SQLite local storage) & Google Firebase Firestore (Cloud persistence)

---

## 📥 Setup and Installation Instructions
To run MoodSpace on your local computer or emulator, follow these steps:

### Prerequisites
* **Android Studio:** Ladybug (or newer version) [0.1.1]
* **Android SDK:** API Level 26 (Android 8.0) or higher
* **Java Development Kit:** JDK 17

### Steps to Run
1. **Clone this repository to your local machine:**
   ```bash
   git clone https://github.com
   ```
2. **Open the project inside IDE:**
   * Open Android Studio, click **File > Open**, and select the cloned root folder.
3. **Configure Google Services (Firebase setup):**
   * Go to the Firebase Console, create a placeholder project, and generate a `google-services.json` file.
   * Drop the `google-services.json` file directly into your app project's root directory (`/app`).
4. **Build and Sync:**
   * Allow Android Studio to sync the Gradle build dependency graph automatically.
5. **Deploy the application:**
   * Connect an Android device with **USB Debugging** enabled, or open a virtual Android Emulator device.
   * Click the green **Run (Play button)** icon in the top toolbar of Android Studio.

---

## 📖 Acknowledgments & References
* **Documentation Providers:** Google Android Developer Codelabs (Jetpack UI, ViewModel, Room Framework APIs).
* **AI Tooling Acknowledgement:** Developed with structural advice and debugging assistance from Claude (Anthropic), with all code independently reviewed and customized to fulfill strict oral defense Q&A validation rubrics [0.1.1, source: 2].
