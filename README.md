# SystemIQ-CMP
SystemIQ is a comprehensive cross-platform system utility agent that monitors and displays critical device statistics in real time. Originally designed for Android, it has been fully unified using Kotlin Multiplatform (KMP) to deliver a seamless and native-feeling experience on both iOS and Android.

By leveraging Compose Multiplatform for the user interface and clean architecture principles for data sources, SystemIQ provides deep visibility into:

⚡ Performance: Real-time CPU, RAM, and hardware utilization metrics.
🔋 Battery: Detailed battery health, charging status, and capacity insights.
🛡️ Privacy: Security metrics and sensitive sensor/permission monitoring.
Tech Stack
Core Architecture:

Kotlin Multiplatform (KMP): Used to share business logic, state management, and view models across Android and iOS, reducing code duplication.
Clean Architecture Principles: Separation of concerns using platform-specific data sources and common state holders (e.g., PerformanceStateHolder, BatteryStateHolder, PrivacyStateHolder).
UI & Presentation:

Compose Multiplatform (CMP): Declarative UI framework used to build a single, shared UI for both Android and iOS targets.
Material Design 3: Utilizing compose.material3 for modern, adaptive UI components, theming, and clean navigation (Scaffold & NavigationBar).
Jetpack Compose Animation: Fluid screen transitions and state-based animations.
Concurrency & Asynchronous Operations:

Kotlinx Coroutines: Asynchronous flow handling, lifecycle awareness, and background task execution (e.g., querying hardware metrics).
Platform-Specific (Expected & Implemented):

Android: Direct integration with Android SDK (e.g., BatteryManager, ActivityManager) using androidMain.
iOS: Direct integration with iOS SDK (e.g., UIDevice, ProcessInfo, OS Security Frameworks) utilizing Kotlin/Native interop in iosMain.

