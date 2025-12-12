# 🖌️ Paint Application (CSC207)
A fully-featured paint/drawing application built with Java and JavaFX, for CSC207 at the University of Toronto Mississauga, following the Model-View-Controller (MVC) architecture with multiple design patterns.

---

## ✨ Features
Shape Drawing: Circle, Rectangle, Squiggle (freehand), and Polyline

File Operations: Save and load drawings in custom Paint Save File Format

Real-time Preview: Visual feedback while drawing shapes

Multiple Colors & Fill: Random colors with fill/unfill options

---

## 🏗️ Architecture
MVC Pattern: Clean separation of Model, View, and Controller components

Visitor Pattern: Drawing and serialization logic separated from data models

Strategy Pattern: Different drawing behaviors for each shape type

Observer Pattern: Automatic UI updates when model changes

Command Pattern: Each shape represented as a command object

---

## 🧩 Design Patterns Implemented
Model-View-Controller (MVC) - Core application structure

Visitor Pattern - For drawing and file serialization

Strategy Pattern - Shape-specific drawing behaviors

Observer Pattern - Model-view synchronization

Command Pattern - Representing drawable shapes

Factory Pattern - Creating shape strategies

---

## 📁 Project Structure
text
src/
├── model/          # Data models and commands
├── view/           # UI components and visitors
├── controller/     # Event handlers and strategies
├── persistence/    # File I/O and parsing
└── [tests]/        # Unit tests

---

## 🚀 Getting Started
Prerequisites: Java 11+, JavaFX

Build: mvn clean compile

Run: mvn javafx:run

Test: mvn test

---

## 📋 Requirements Implemented

✅ User Story 3.1: Polyline drawing with right-click completion

✅ User Story 3.2: Save drawings to file format

✅ User Story 3.3: Load drawings from file format

✅ Bug 3.4: Visitor pattern for drawing (separate model from view)

✅ Bug 3.5: Visitor pattern for saving (separate model from persistence)

---

## 🧪 Testing
Comprehensive test suite including:

File parsing tests (whitespace normalization, error handling)

Shape drawing tests

Save/load integration tests

Visitor pattern implementation tests

---

## 📸 Screenshots

Here are examples of the paint UI:


<img width="892" height="837" alt="image" src="https://github.com/user-attachments/assets/d13975d7-ec62-4c94-838a-75e7bc8cffe9" />


<img width="895" height="817" alt="image" src="https://github.com/user-attachments/assets/c737a3ee-fdbf-43f7-8add-97c73bbeae9b" />


---

## 🛠️ Technical Highlights
  - Custom file format parser with finite state machine

  - Regular expression-based file parsing

  - Proper error handling with user-friendly alerts

  - Observer-based real-time UI updates

  - Extensible architecture for adding new shapes

---

## 👤 Author

**Abdul Mohammed**  
University of Toronto Mississauga  
CSC207 Software Design

---


