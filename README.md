# 🚘 Driver Behavior Monitor (AAOS)

Driver Behavior Monitor is an **Android Automotive OS (AAOS) app** that provides monitoring features for in-car systems, and authentication, user profile integration.
The app demonstrates **Google OAuth2 login**, token management, and displays user profile data such as name and avatar inside an AAOS environment.

---

## ✨ Features

- 📊 **Driver Behavior Monitoring UI** (custom AAOS screens)
- 🔑 **Google OAuth2 Authentication** (Device Code Flow)
- 🔄 **Secure Token Storage** with `SharedPreferences`
- 👤 **User Profile**: name, avatar, and email loaded from Google API
- 🖼️ **Screenshots** of every screen for demo

---

## 📱 Screenshots

| Login Screen                    | Verification Code                             | User Profile                        |
| ------------------------------- | --------------------------------------------- | ----------------------------------- |
| ![Login](screenshots/login.png) | ![Verification](screenshots/verification.png) | ![Profile](screenshots/profile.png) |

| Settings                              | Driving Stats                   | Logout                            |
| ------------------------------------- | ------------------------------- | --------------------------------- |
| ![Settings](screenshots/settings.png) | ![Stats](screenshots/stats.png) | ![Logout](screenshots/logout.png) |

---

## 🛠️ Tech Stack

- **Android Automotive OS (AAOS)**
- **Kotlin + Jetpack Compose**
- **ViewModel + Coroutines + Flow**
- **OkHttp + Gson** for networking & parsing
- **Coil** for image loading
