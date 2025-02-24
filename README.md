# ScanStream

ScanStream is an Android application developed to record barcode scans of factory production line packages into a database. The application collects package data using a device with a barcode scanner and synchronizes it with Firebase.

## Features
- **Barcode Scanning:** Supports barcode scanning using devices with built-in barcode scanners.
- **Real-Time Data Recording:** Instantly saves scanned barcode data to Firebase Firestore.
- **Authentication System:** User roles (operator and manager) with authentication support.
- **Factory Management:** Each user is assigned to a specific factory, and managers can oversee operators within their factory.
- **Location and Timestamp:** Every scanned barcode is recorded with the user's email, scan location, and timestamp.
- **User Management:** Managers can create, delete, or deactivate operator accounts.

## Usage
1. **Log In:** Sign in as an operator or manager using Firebase Authentication.
2. **Start Scanning:** Use a device with a built-in barcode scanner to scan barcodes.
3. **Data Recording:** Scanned barcodes are instantly saved in the Firestore database.
4. **Manager Panel:** Managers can create and manage operator accounts.

## Technologies
- **Programming Language:** Kotlin
- **UI Development:** Jetpack Compose
- **Database:** Firebase Firestore
- **Authentication:** Firebase Authentication
- **Connection & Data Synchronization:** Firebase SDK
- **Barcode Scanning:** Devices with built-in barcode scanners

## Installation
1. Clone the repository from GitHub:
   ```
   git clone https://github.com/salihaslantas/scanstream.git
   ```
2. Open the project in Android Studio and configure Firebase connection.
3. Deploy the application to an Android device.

## Contribution
To contribute, please submit a **pull request** or open an **issue** to provide feedback.

## License
This project is licensed under the MIT License.

