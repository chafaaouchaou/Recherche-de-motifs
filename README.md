# 🚀 Guide de Démarrage du Projet  

## 📥 Cloner le projet  
```sh
git clone https://gitlab-dpt-info-sciences.univ-rouen.fr/m1gil/dungeon-master.git
cd "projet anuel"
```

## 🏗️ Lancer les services  

### 1️⃣ Démarrer le Frontend (React)  
```sh
cd MD
docker-compose up --build
```

### 2️⃣ Démarrer le Backend (Spring Boot)  
```sh
cd ../Backend
docker-compose up --build
```

## 🔄 Hot Reload & Développement  
Les deux applications (React & Spring Boot) sont configurées avec **hot reload**, ce qui signifie que :  
- **Toute modification dans le code** sera automatiquement prise en compte **sans reconstruire l'image Docker**.  
- Vous pouvez développer sans interruption et voir les mises à jour instantanément. 🚀  

Bon dev ! 😃

