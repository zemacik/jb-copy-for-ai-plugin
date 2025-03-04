# Copy for AI Assistant Plugin for Jetbrains products

A plugin that helps you organize and copy content from multiple files with paths and names to your clipboard. Perfect for providing context to AI assistants for more accurate and efficient responses.

**🛑 DISCLAIMER: The repository is provided as is. It is not intended to be used as a reference for best practices or as a reference for how to write good code. If you find any bugs please try to fix them :-)** I'm a .NET developer, this is my first Kotlin project, which was built (with a help of AI) to solve a problem I had.

## Features

- Copy selected files with file paths and syntax highlighting markers
- Copy opened files with file paths and syntax highlighting markers
- Available in Project View context menu, Edit menu, and Tools menu
- Automatically detects file type and adds appropriate language identifier for code blocks

## Requirements

- IntelliJ IDEA 2024.1 or newer
- Java 17+

## Building from Source

1. Clone the repository
   ```bash
   git clone https://github.com/zemacik/jb-copy-for-ai-plugin.git
   cd jb-copy-for-ai-plugin
   ```

2. Build the plugin
   ```bash
   ./gradlew build
   ```

   This will generate a plugin ZIP file in `build/distributions/`.

## Installation

This plugin is not published to the JetBrains Plugin Marketplace. To install:

1. In IntelliJ IDEA, go to `File` > `Settings` > `Plugins`
2. Click the gear icon (⚙️) and select `Install Plugin from Disk...`
3. Navigate to the `build/distributions/` directory and select the ZIP file
4. Click `OK` and restart IntelliJ IDEA when prompted

## Usage

### Copy Selected Files

1. Select one or more files in the Project View
2. Right-click and select `Copy for AI Assistant (Selected Files)`
3. Paste the content into your AI assistant

### Copy Opened Files

1. With files open in the editor
2. Go to `Edit` > `Copy for AI Assistant (Opened Files)`
3. Paste the content into your AI assistant

### Copy From Editor

1. Select text in the editor
2. Right-click and select `Copy for AI Assistant (Editor)`
3. Paste the content into your AI assistant

## Development

The project uses Gradle and the IntelliJ Platform Plugin Template:

- Kotlin 1.9.25
- IntelliJ Plugin Gradle Plugin 1.17.4
- Targeting IntelliJ IDEA 2024.1+

To run the plugin in a development instance:

```bash
./gradlew runIde
```

## License

See [LICENSE](LICENSE) for licensing information.
```