# 🏦 ATM Command-Line Application

A simple **ATM CLI simulation** project built in **Java** using **Maven**.  


## ⚙️ Prerequisites

Before running the project, ensure you have the following installed:

| Tool | Version | Description |
|------|----------|-------------|
| **Java** | 17 or higher | Used to compile and run the ATM app |
| **Maven** | 3.8+ | For building and managing dependencies |

---

## 🚀 Setup & Build

### 1️⃣ Open project with your favorite idea

💡 *(In this documentation, the author uses IntelliJ IDEA)*

### 1️⃣ Build the project using Maven

```bash
mvn clean package
```
### ▶️ Running the ATM Application via maven (non JAR)
```bash
mvn exec:java
```

### ▶️ Running the ATM Application (from JAR)
```bash
cd target
java -jar atm-cli.jar
```
### ▶️ Running the ATM Application from start script
```bash
./start.sh
```

### 🧪 Running Unit Tests
```bash
mvn test
```
