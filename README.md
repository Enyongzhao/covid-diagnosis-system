🧠 AI-Assisted Medical Diagnosis System

A Full-Stack Web Application with Machine Learning Integration

📌 Overview

This project is a full-stack web application designed to assist medical diagnosis using machine learning models integrated into a production-ready system.

It combines:

A Spring Boot backend for user management and API services
A Vue.js frontend for interactive user interface
A Flask-based ML service for model inference
Nginx reverse proxy for unified deployment and routing

The system demonstrates real-world architecture by integrating traditional web backend + AI model service.

🏗️ System Architecture
Client (Browser)
       ↓
   Nginx (Reverse Proxy)
       ↓
 ┌───────────────┬───────────────┐
 │ Spring Boot   │ Flask (ML)    │
 │ (API Server)  │ (Model API)   │
 └───────────────┴───────────────┘
✨ Features
👨‍⚕️ Core Features
User authentication (JWT-based login system)
Patient data management
Medical record handling
AI-assisted diagnosis (model inference via Flask API)
🤖 AI Integration
Machine learning model deployed using Flask
REST API for prediction
Integrated into frontend workflow
🌐 Frontend
Built with Vue.js
Responsive UI
API-driven data rendering
⚙️ Backend
Spring Boot RESTful API
JWT authentication & authorization
MyBatis for database operations
🚀 Deployment
Nginx reverse proxy:
/api → Spring Boot
/flask → Flask ML service
Linux server deployment (AWS EC2)
Background services using nohup
🛠️ Tech Stack
Layer	Technology
Frontend	Vue.js, Axios
Backend	Spring Boot, MyBatis, JWT
AI Service	Flask, Python
Deployment	Nginx, Linux (Ubuntu)
Database	MySQL
⚙️ Key Engineering Highlights
🔥 1. Reverse Proxy Architecture
Implemented Nginx reverse proxy to unify frontend and backend services
Eliminated CORS issues by routing APIs through /api
🔥 2. Microservice-style Integration
Decoupled ML inference service using Flask
Enabled independent scaling and deployment
🔥 3. Production Deployment
Deployed on cloud server (AWS EC2)
Managed services using:
nohup for background execution
Linux process management
🔥 4. Debugging & Problem Solving

Resolved multiple real-world deployment issues:

Static resource loading errors (JS/CSS path issues)
Linux file permission problems
CORS and cross-origin issues
Nginx reverse proxy misconfiguration
Backend service connectivity (502 errors)
📦 Project Structure
project-root/
│
├── backend/        # Spring Boot application
├── frontend/       # Vue.js application
├── flask-service/  # ML inference service
├── nginx/          # Deployment config
└── README.md
🚀 How to Run
1️⃣ Start Backend
java -jar springboot-0.0.1-SNAPSHOT.jar
2️⃣ Start Flask Service
cd flask-service
source venv/bin/activate
python app.py
3️⃣ Start Nginx
sudo systemctl start nginx
4️⃣ Access
http://<your-server-ip>
📊 Future Improvements
Docker containerization
CI/CD pipeline integration
Model optimization & retraining
Role-based access control enhancements
📈 What This Project Demonstrates

✔ Full-stack development
✔ Backend + AI integration
✔ Real-world deployment experience
✔ Debugging complex production issues
✔ Understanding of web architecture

👨‍💻 Author

Enyong Zhao
Software Engineering @ UWA
