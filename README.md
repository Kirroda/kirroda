# KRcalc - Android Calculator App

A modern, dark-themed calculator application for Android built with Kotlin.

## Features

- **Dark Theme**: Sleek dark interface with custom color scheme
- **BODMAS/PEMDAS**: Proper order of operations evaluation
- **Brackets Support**: Full parentheses support for complex expressions
- **Percentage Calculations**: Built-in percentage functionality
- **Smart Input Validation**: Prevents invalid expressions
- **Delete & Clear Functions**: 
  - Delete: Removes last input character
  - Clear: Removes all input
- **Portrait Mode**: Optimized for portrait orientation
- **State Persistence**: Maintains calculator state across device rotations

## Color Scheme

- **Number Buttons**: Neon Green (#00FF41) text on dark grey background
- **Operator Buttons**: Neon Red (#FF073A) text on dark grey background
- **Display**: Black text on mild cyan background (#87CEEB)
- **Background**: Dark grey theme throughout

## Technical Details

- **Package**: `com.kirroda.krcalc`
- **Target SDK**: 34 (Android 14)
- **Min SDK**: 24 (Android 7.0)
- **Language**: Kotlin
- **Architecture**: MVVM pattern
- **Expression Evaluation**: exp4j library for mathematical expressions

## Project Structure

```
app/
├── src/main/
│   ├── java/com/kirroda/krcalc/
│   │   ├── MainActivity.kt          # Main activity with UI logic
│   │   └── CalculatorEngine.kt      # Core calculation logic
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml    # Main calculator layout
│   │   ├── values/
│   │   │   ├── colors.xml           # Color definitions
│   │   │   ├── strings.xml          # String resources
│   │   │   └── themes.xml           # App themes
│   │   ├── drawable/
│   │   │   ├── button_background.xml
│   │   │   └── display_background.xml
│   │   └── xml/
│   │       ├── backup_rules.xml
│   │       └── data_extraction_rules.xml
│   └── AndroidManifest.xml
└── build.gradle
```

## Build Instructions

### Prerequisites

- Android Studio Arctic Fox or later
- Kotlin 1.9.10+
- Android SDK 34
- Gradle 8.1.2+

### Setup

1. **Clone/Download** the project files
2. **Open in Android Studio**:
   ```bash
   # If using git:
   git clone <repository-url>
   cd KRcalc
   
   # Open the project in Android Studio
   ```

3. **Sync Gradle**: Android Studio will automatically sync dependencies

4. **Build the project**:
   ```bash
   # Command line build:
   ./gradlew assembleDebug
   
   # Or use Android Studio Build menu
   ```

### Dependencies

The app uses the following key dependencies:

```gradle
implementation 'androidx.core:core-ktx:1.12.0'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.10.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'net.objecthunter:exp4j:0.4.8'  // Expression evaluation
```

### Running

1. **Connect an Android device** or start an **Android Emulator**
2. **Run the app** from Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## Usage

### Basic Operations
- Tap number buttons to input numbers
- Tap operator buttons (+, −, ×, ÷) for basic arithmetic
- Tap "=" to evaluate the expression
- Tap "C" to clear all input
- Tap "⌫" to delete the last character

### Advanced Features
- Use "(" and ")" for grouping expressions
- Use "%" for percentage calculations (e.g., "50%" becomes 0.5)
- Support for decimal numbers using "."
- Automatic BODMAS/PEMDAS evaluation

### Example Calculations
```
Basic: 2 + 3 × 4 = 14
Brackets: (2 + 3) × 4 = 20
Percentage: 50% × 200 = 100
Complex: (10 + 5) × 2 ÷ 3 = 10
```

## Release Build

To create a release APK:

1. **Generate a signing key** (first time only):
   ```bash
   keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Configure signing** in `app/build.gradle`:
   ```gradle
   android {
       signingConfigs {
           release {
               storeFile file('path/to/my-release-key.keystore')
               storePassword 'your-store-password'
               keyAlias 'alias_name'
               keyPassword 'your-key-password'
           }
       }
       buildTypes {
           release {
               signingConfig signingConfigs.release
               // ... other release config
           }
       }
   }
   ```

3. **Build release APK**:
   ```bash
   ./gradlew assembleRelease
   ```

The APK will be generated in `app/build/outputs/apk/release/`

## License

This project is created for demonstration purposes. Feel free to use and modify as needed.

## Version History

- **v1.0**: Initial release with basic calculator functionality, dark theme, and BODMAS support
