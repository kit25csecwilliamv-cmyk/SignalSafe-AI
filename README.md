# SignalSafe AI

Emergency communication when internet/mobile networks fail.

## Current MVP
- Java Android application
- SOS interface
- Bluetooth permission and device availability checks
- Nearby-device scan entry point
- Location sharing entry point
- Offline-first UI structure

## Next hackathon implementation
1. BLE advertising/discovery and message relay
2. Wi-Fi Direct transport
3. SOS message model with TTL/message ID to prevent duplicates
4. GPS location capture
5. Firebase sync when internet returns
6. Gemini API for emergency-priority classification
7. Rescue-center dashboard

## Build
Open this folder in Android Studio and let Gradle sync.
Run on two Android phones for device-to-device testing.

## Important
Do not put API keys or google-services.json in GitHub.
