#!/bin/bash
set -e  # Stop immediately if a command exits with a non-zero status

# ===============================================================
#  ATM Application Startup Script
# ===============================================================

echo "🔧 Cleaning previous build..."
mvn clean > /dev/null

echo "📦 Building project..."
mvn -q package

# ===============================================================
#  Run the ATM CLI application
# ===============================================================

echo "🚀 Starting ATM CLI..."
echo "-------------------------------------------------------------"
echo "Welcome to the ATM application!"
echo "Type your commands below (e.g., 'login Alice', 'deposit 100', etc.)"
echo "-------------------------------------------------------------"
echo ""

# Find the built JAR (assuming it's in target/)
JAR_FILE=$(ls target/*.jar | head -n 1)

# If you have a main class instead of jar packaging, uncomment:
# mvn exec:java -Dexec.mainClass="com.dkatalis.exercise.atm.ATMApp"

# Run the JAR with a fresh data state
java -jar "$JAR_FILE"
