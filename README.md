# GAYAN GANGA COCHING CENTER — Native Android App

This is a native Kotlin + Jetpack Compose Android application.

## Current UI
- GAYAN GANGA COCHING CENTER branding
- Login / Register using Firebase Authentication
- Home dashboard inspired by the supplied reference screenshot
- 9 learning feature cards
- Bottom navigation: Home / Courses / Tests / Profile
- Firebase Firestore and Storage dependencies ready
- GitHub Actions APK build workflow
- Custom vector logo included

## Firebase setup
1. Create/select a Firebase project.
2. Add an Android app with package name:
   `com.gayangangacoachingcenter.app`
3. Download `google-services.json`.
4. Put it at:
   `app/google-services.json`
5. Enable Authentication > Email/Password.
6. Create Firestore Database.
7. Enable Storage if PDF/video/file uploads will be used.

Do not upload private service-account JSON keys. `google-services.json` is the normal Android client configuration file.

## Build
Open in Android Studio, sync Gradle, then:
- Build > Make Project
- Build > Build App Bundle(s) / APK(s) > Build APK(s)

GitHub Actions can build a debug APK after the project is pushed to a GitHub repository.

## Planned next modules
- Admin login/dashboard
- Firebase Firestore course management
- Live classes / video classes
- PDF notes and books
- Test series + timer + result
- Daily quiz
- Student profile
- Notifications
- Paid course purchase flow
- Teacher/admin content upload
- Release signed APK/AAB
