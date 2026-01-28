# HytaleMultipleHUD

A lightweight Java library for managing multiple custom HUDs (Heads-Up Displays) in Hytale server plugins.

## 📌 Overview

**HytaleMultipleHUD** simplifies the management of multiple custom UI overlays on a single player. Instead of replacing the entire HUD each time you need to display a new interface, this library allows you to stack multiple HUDs and manage them individually with unique identifiers.

### Key Features

- 🎯 **Multiple HUD Management** - Display and manage multiple HUDs simultaneously on the same player
- 🏷️ **Flexible Identification** - Identify HUDs by class type or custom string identifiers
- 🔄 **Easy Switching** - Switch between HUDs without losing track of existing ones
- 🛡️ **Thread-Safe** - Uses concurrent data structures for safe multi-threaded operations
- 🪶 **Lightweight** - Minimal dependencies, focused solely on HUD management

## 🚀 Quick Start

### Basic Usage

```java
import fr.flash303.multiplehud.MultipleHud;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.universe.PlayerRef;

// Display a custom HUD with automatic identification
CustomUIHud myHud = new CustomUIHud(playerRef);
MultipleHud.displayCustomHud(player, playerRef, myHud);

// Display a custom HUD with a string identifier
MultipleHud.displayCustomHud(player, playerRef, "my-hud-id", myHud);

// Switch between multiple HUDs without losing them
MultipleHud.displayCustomHud(player, playerRef, secondaryHud);
```

## 📦 Dependencies

### Gradle

```gradle
dependencies {
    implementation 'com.github.Flash303:HytaleMultipleHUD:v1'
}
```

Add JitPack to your repositories:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```

### Maven

```xml
<dependency>
    <groupId>com.github.Flash303</groupId>
    <artifactId>HytaleMultipleHUD</artifactId>
    <version>v1</version>
</dependency>
```

Add JitPack to your repositories:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

## 📚 How It Works

The library works by wrapping multiple custom HUDs in a single `HudWrapper` that acts as a container:

1. **First Display**: When you display a HUD for the first time, a wrapper is created and set as the active HUD
2. **Subsequent Displays**: Additional HUDs are added to the wrapper's internal map with unique identifiers
3. **Active Hud**: The wrapper maintains which HUD is currently active and renders accordingly
4. **Thread-Safe Storage**: Uses `ConcurrentHashMap` to safely manage multiple HUDs across threads

### HUD Identification

The library supports three ways to identify HUDs:

- **Class-based** (`HudClassIdentifier`) - Uses the class name of the custom HUD
- **String-based** (`HudIdIdentifier`) - Uses a custom string identifier you provide
- **Custom** (`HudIdentifier`) - Implement your own identification logic

## 🏷️ HUD Identifiers

The library supports flexible HUD identification:

- **Class-based (Default)** - HUDs are identified by their class name. Only one HUD per class type can be active simultaneously.
  ```java
  MultipleHud.displayCustomHud(player, playerRef, myHud);
  // Identified as: "MyCustomHud"
  ```

- **String-based** - Use custom string identifiers to store multiple HUDs of the same class type.
  ```java
  MultipleHud.displayCustomHud(player, playerRef, "shop-tier-1", shopHud);
  MultipleHud.displayCustomHud(player, playerRef, "shop-tier-2", shopHud);
  // Both can coexist with different identifiers
  ```

## 🛠️ API Methods

```java
// Display with automatic class-based identification
MultipleHud.displayCustomHud(Player player, PlayerRef playerRef, CustomUIHud customHud)

// Display with custom string identifier
MultipleHud.displayCustomHud(Player player, PlayerRef playerRef, String identifier, CustomUIHud customHud)

// Display with custom HudIdentifier
MultipleHud.displayCustomHud(Player player, PlayerRef playerRef, HudIdentifier identifier, CustomUIHud customHud)
```

## ⚙️ Requirements

- Java 25+
- Hytale Server 2026.01.24-6e2d4fc36 or compatible

## 📄 License

This project is authored by Flash303.

## 🤝 Contributing

Contributions are welcome! Feel free to submit issues and pull requests.
