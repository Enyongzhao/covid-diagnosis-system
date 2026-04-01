# 🧠 AI-Assisted Medical Diagnosis System

A full-stack web application integrating machine learning into a real-world system using Spring Boot, Vue.js, Flask, and Nginx.

🚀 Live Demo: http://52.64.16.29/Login
---

## 📌 Overview

This project is a full-stack medical diagnosis support system that combines traditional web technologies with machine learning.

It includes:
- A Spring Boot backend for API services and authentication
- A Vue.js frontend for user interaction
- A Flask-based service for AI model inference
- Nginx reverse proxy for unified deployment

The system demonstrates how to integrate AI models into a production-ready web application.

---

## ✨ Features

### 🔐 Authentication
- JWT-based login system
- Secure API access control

### 📋 Core Functions
- User and patient management
- Medical record handling
- Data-driven UI

### 🤖 AI Integration
- Machine learning model deployed via Flask
- REST API for predictions
- Integrated into frontend workflow

---

## 🛠️ Tech Stack

- **Frontend:** Vue.js, Axios  
- **Backend:** Spring Boot, MyBatis, JWT  
- **AI Service:** Flask, Python  
- **Deployment:** Nginx, Linux (Ubuntu)  
- **Database:** MySQL  

---

## 🚀 Deployment

The system is deployed on a Linux server with the following setup:

- Nginx reverse proxy:
  - `/api` → Spring Boot backend
  - `/flask` → Flask ML service
- Backend services running in background using `nohup`
- Frontend served as static files via Nginx

---

## ⚙️ Key Highlights

- Designed reverse proxy architecture to eliminate CORS issues
- Integrated AI model service with backend system
- Deployed full system on cloud server (AWS EC2)
- Solved real-world issues including:
  - Static resource loading errors
  - Linux file permission problems
  - Nginx configuration issues
  - Backend connection failures (502 errors)

---

## 📈 Future Improvements

- Docker containerization
- CI/CD pipeline integration
- Model performance optimization
- Role-based access control

---

## 👨‍💻 Author

**Enyong Zhao**  
Software Engineering @ UWA

