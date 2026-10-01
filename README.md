# GAYAN GANGA COCHING CENTER

Native Android application built with Kotlin + Jetpack Compose.

## Project structure

```text
gayan-ganga-coching-center/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/
│           └── res/
├── .github/
│   └── workflows/
│       └── build-apk.yml
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## App features in v1

- GAYAN GANGA COCHING CENTER branding
- Firebase Authentication dependency
- Login / Register UI
- Home dashboard
- Paid Classes
- Free Courses
- Free Weekly Test
- Books
- PDF Class Notes
- Paid Test Series
- Syllabus / Previous Year
- Create Test
- Daily Quiz
- Courses / Tests / Profile navigation
- GitHub Actions APK build

## Firebase

Create a Firebase Android app with package:

`com.gayangangacoachingcenter.app`

Download the real `google-services.json` and put it here:

`app/google-services.json`

The Google Services Gradle plugin should be enabled after the real Firebase file is added.

Do NOT upload private Firebase service-account keys.

## Build

Open this project in Android Studio and sync Gradle.

GitHub Actions can build a debug APK using the workflow in:

`.github/workflows/build-apk.yml`
