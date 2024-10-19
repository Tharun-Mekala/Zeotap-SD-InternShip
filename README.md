# Zeotap Software Development Internship Projects

This repository contains two main projects: `rule_engine` and `weather_app`. Below are the instructions for setting up and running these applications.

## Table of Contents

- [Importing and Running the Backend](#importing-and-running-the-backend)
- [Running the Frontend (rule_engine_ui)](#running-the-frontend-rule_engine_ui)
- [Running the Weather App](#running-the-weather-app)
- [Additional Notes](#additional-notes)

---

## Repository Structure

The repository consists of two main projects organized in separate folders:

### 1. **Rule Engine Project**
   - This project includes both the backend and UI components.
   - It is structured into two parts:
     - **rule_engine_backend**: Contains the Java Spring Boot backend for handling the business logic and rule processing.
     - **rule_engine_ui**: Contains the React.js frontend for interacting with the rule engine.

### 2. **Weather App Project**
   - A standalone project designed to process and monitor weather data in real-time.
   - It retrieves weather information from an external API and provides summarized insights based on various weather parameters such as temperature, humidity, and wind speed.

## Folder Structure

- `rule_engine_backend/` – Contains the backend logic for the rule engine.
- `rule_engine_ui/` – Contains the frontend UI for interacting with the rule engine.
- `weather_app/` – The separate project for weather monitoring and data aggregation.

---

## 1. Importing and Running the Backend

### Importing the project into Eclipse:

1. Open Eclipse IDE.
2. Go to `File > Import > Maven > Existing Maven Projects`.
3. Browse to the `rule_engine_backend` folder in the cloned repository.
4. Click **Finish** to import the project.

### Running the Spring Boot application:

1. Navigate to `Zeotap-SD-InternShip/rule_engine_backend/src/main/java/com/yourpackage/Application.java`.
2. Right-click on `Application.java` and select **Run As > Spring Boot App**.
3. The backend server should now be running at [http://localhost:8080](http://localhost:8080).

### Testing the API:

- You can test the APIs using tools like Postman or `curl`.

---

## 2. Running the Frontend (rule_engine_ui)

### Prerequisites:

- [Node.js](https://nodejs.org/) and npm should be installed.
- [Visual Studio Code (VS Code)](https://code.visualstudio.com/) should be installed.

### Steps:

1. Open the `rule_engine_ui` folder in VS Code.
2. From the terminal or VS Code, navigate to the `rule_engine_ui` folder:

   ```bash
   cd Zeotap-SD-InternShip/rule_engine_ui
3. Install the dependencies:

   ```bash
   npm install
4. Start the development server:

   ```bash
   npm start
- [The frontend React application should now be running at](http://localhost:3000) [by default].

## Note:
Ensure that the rule_engine_backend is running simultaneously for the frontend to interact with the APIs.
## 3. Running the Weather App
### Prerequisites:
- [Node.js](https://nodejs.org/) and npm should be installed.
- [Visual Studio Code (VS Code)](https://code.visualstudio.com/) should be installed.
### Steps:
1. Open the `weather_app` folder in VS Code.
2. From the terminal or VS Code, navigate to the `weather_app` folder:

   ```bash
   cd Zeotap-SD-InternShip/weather_app

3. Install the dependencies:

   ```bash
   npm install
4. Start the development server:

   ```bash
   npm start
- [The frontend React application should now be running at](http://localhost:3000) [by default].

### Additional Notes:
   - rule_engine_backend and rule_engine_ui are tightly coupled and should run together.
   - weather_app is an independent project and can be run separately.
   - Ensure you have appropriate internet access for the weather_app to fetch data from external weather APIs.
