# MMCP (Minecraft MCP)

![CodeRabbit Pull Request Reviews](https://img.shields.io/coderabbit/prs/github/RispyCZ/MMCP?utm_source=oss&utm_medium=github&utm_campaign=RispyCZ%2FMMCP&labelColor=171717&color=FF570A&link=https%3A%2F%2Fcoderabbit.ai&label=CodeRabbit+Reviews)

**🚧 Work in Progress - Early Development Stage**

MMCP is a Minecraft plugin that implements the Model Context Protocol (MCP) server, enabling Large Language Models (LLMs) to assist with server administration tasks. This plugin bridges the gap between AI assistants and Minecraft server management, automating boring administrative work.

## ✨ Features

Currently available MCP tools:
- **`read_file`** - Read server configuration files and logs
- **`write_file`** - Modify configuration files and create new ones
- **`send_command`** - Execute server commands programmatically

### What can LLMs help with?
- Server configuration management
- Log file analysis and monitoring
- Automated command execution
- Configuration troubleshooting
- Routine administrative tasks

## 🔧 Installation

1. Download the latest release from the [Releases](../../releases) page
2. Place `MMCP-x.x.x.jar` in your server's `plugins/` directory
3. Restart your server
4. Configure the plugin (see Configuration section)

## 📋 Requirements

- **Minecraft Version:** 1.21.x
- **Server Software:** Paper
- **Java Version:** 17+

## ⚙️ Configuration

After first run, edit `plugins/MMCP/main.conf`:

```hocon
plugin {
    http {
        address="0.0.0.0"
        port=8090
        apiToken="<Random API Token>"
        whitelistedIps=[]
    }
}
```

## 🚀 Usage

### Connecting an LLM Client

1. Start your Minecraft server with MMCP installed
2. Connect your MCP-compatible LLM client to `http://localhost:8090`
3. Authenticate using your configured API key
4. Start using the available tools!

## 🛠️ Available MCP Tools

### `read_file`
Read contents of server files
- **Parameters:** `path` (string) - File path relative to server directory
- **Returns:** File contents as text

### `write_file`
Write or modify server files
- **Parameters:**
    - `path` (string) - File path relative to server directory
    - `content` (string) - Content to write
- **Returns:** Success confirmation

### `send_command`
Execute server console commands
- **Parameters:** `command` (string) - Command to execute (without /)
- **Returns:** Command output/result

## 🔒 Security Considerations
- **Authentication:** API key required for all requests

## 🗺️ Roadmap

Planned features for future releases: [TODO.md](./TODO.md)

## 🤝 Contributing

This project is open source and contributions are welcome!

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the [GNU GPLv3 License](COPYING) - see the COPYING file for details.

## 🐛 Bug Reports & Feature Requests

Please use the [GitHub Issues](../../issues) page to:
- Report bugs
- Request new features
- Ask questions about usage

## ⚠️ Disclaimer

**This plugin is in active development.** Features may change, and there might be bugs. Use in production environments at your own risk. Always backup your server before testing new versions.

## 📞 Support

- **Issues:** [GitHub Issues](../../issues)
- **Discussions:** [GitHub Discussions](../../discussions)
---

**Made with ❤️ for the Minecraft community**