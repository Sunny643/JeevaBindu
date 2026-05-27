# 🩸 Jeeva-Bindu: Rapid Blood Response Platform

![Platform](https://img.shields.io/badge/Platform-Android-green)
![Language](https://img.shields.io/badge/Language-Kotlin-purple)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-blue)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange)
![Database](https://img.shields.io/badge/Database-Room-red)
![Status](https://img.shields.io/badge/Status-Active-success)

---

## 🌟 Project Overview

**Jeeva-Bindu** is a production-grade Android application designed to bridge the gap between blood donors and recipients during emergencies.

By leveraging:

- 📍 Real-time location tracking
- 🚨 Emergency broadcasting
- 🩸 Live donor directories
- 📊 Health eligibility tracking

the app transforms the blood donation process into a rapid, community-driven response system.

---

# 🚀 Key Features

## 🔐 Secure Verification

- Phone-based OTP verification
- Secure onboarding system

## 📋 Donor Profiles

- Blood group registration
- Panchayat/Town location setup
- Donor contact management

## 📍 Live Donor Directory

- Filter donors by blood group
- Distance-based search (1km – 50km)
- Real-time donor discovery

## 🚨 Emergency Alert System

- Instant emergency request broadcasting
- Critical blood alert overlays
- Urgency-based notifications

## 🛣️ On-Route Tracking

- Track donors responding to emergencies
- Hospital contact integration
- Live status updates

## 📈 Health & Eligibility Tracking

- 90-day donation cycle monitoring
- Donation history records
- Eligibility calculation

## 📰 Community Feed

- Emergency post sharing
- Community-driven updates
- Critical/Stabilizing status labels

## 🎨 Modern UI

- Material 3 design system
- Jetpack Compose UI
- Dark/Light theme support

---

# 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Material Design | Material 3 |
| Architecture | MVVM + Repository Pattern |
| Database | Room Persistence Library |
| Local Storage | Preferences DataStore |
| Navigation | Compose Navigation |
| Async Programming | Kotlin Coroutines & Flow |
| Image Loading | Coil Compose |
| System UI | Accompanist |

---

# 🏗️ Architecture

The application follows the **MVVM (Model-View-ViewModel)** architecture with Repository Pattern.

```text
UI (Jetpack Compose)
        ↓
ViewModel
        ↓
Repository
        ↓
Room Database / DataStore
```

---

# 📁 Folder Structure

```text
app/src/main/java/com/jeevabindu/app/
├── data/
│   ├── local/
│   ├── model/
│   ├── repository/
│   └── SampleData.kt
├── navigation/
├── ui/
│   ├── components/
│   ├── screens/
│   └── theme/
├── viewmodel/
└── MainActivity.kt
```

---

# 📱 Screens & Modules

| Screen | Purpose |
|---|---|
| Splash Screen | App startup & branding |
| Phone Verification | OTP authentication |
| Donor Dashboard | Main user dashboard |
| Live Directory | Donor search system |
| Emergency Screen | Emergency blood requests |
| Community Feed | User-generated emergency posts |
| Health Tracker | Donation eligibility tracker |
| Settings | Privacy & configuration |

---

# 💾 Database Design

Room Database powers the local persistence layer.

## Entities

- `Donor`
- `EmergencyRequest`
- `DonationRecord`

## DAO Components

- `DonorDao`
- `EmergencyDao`

---

# 🛠️ Installation & Setup

## 1️⃣ Clone Repository

```bash
git clone https://github.com/Sunny643/JeevaBindu.git
```

## 2️⃣ Open in Android Studio

- Open Android Studio
- Click **Open Existing Project**
- Select project folder

## 3️⃣ Sync Gradle

Allow Gradle sync to complete.

## 4️⃣ Run Application

- Connect Android device/emulator
- Click ▶ Run

---

# 📦 Build Instructions

## Generate APK

```bash
./gradlew assembleDebug
```

APK Location:

```text
app/build/outputs/apk/debug/
```

## Run Unit Tests

```bash
./gradlew test
```

## Run UI Tests

```bash
./gradlew connectedAndroidTest
```

---

# 🔐 Permissions Used

| Permission | Purpose |
|---|---|
| INTERNET | Sync emergency/community data |
| POST_NOTIFICATIONS | Blood emergency alerts |
| CALL_PHONE | Call hospitals/coordinators |
| ACCESS_FINE_LOCATION | Distance calculations |

---

# 🔮 Future Improvements

- ☁️ Firebase/AWS cloud integration
- 🏥 Blood bank API integration
- 🏅 Donor reward/gamification system
- 🌐 Multilingual support
- 📡 Real-time notifications
- 🧠 AI-based donor prediction

---

# 📸 Screenshots

| Splash Screen | Dashboard | Directory |
|---|---|---|
| Screenshot Here | Screenshot Here | Screenshot Here |

---

# 🤝 Contribution Guidelines

1. Fork the repository

2. Create feature branch

```bash
git checkout -b feature/AmazingFeature
```

3. Commit changes

```bash
git commit -m "Add AmazingFeature"
```

4. Push branch

```bash
git push origin feature/AmazingFeature
```

5. Open Pull Request

---

# 📄 License

This project is licensed under the MIT License.

---

# 👨‍💻 Author

## Mohammed Fahad

- GitHub: https://github.com/Sunny643

---

# ❤️ Acknowledgement

Made with passion to support emergency blood response systems and save lives.