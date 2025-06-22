# 🛠️ SupportApp - Destek Talep Sistemi

Bu proje, kullanıcıların destek talepleri oluşturabildiği ve adminlerin bu talepleri görüntüleyip yanıtlayabildiği bir **Help Desk / Destek Sistemi** uygulamasıdır.

## 📦 Proje Teknolojileri

- **Backend:** Java 17 + Spring Boot
- **Frontend:** React + TypeScript (ayrı projede)
- **Veritabanı:** H2 (in-memory)
- **JWT (JSON Web Token)** ile kimlik doğrulama
- **Spring Security** ile yetkilendirme (ROLE_USER, ROLE_ADMIN)

## 📁 Backend Katmanları

- `User`: Kullanıcı yönetimi (login, roller)
- `Ticket`: Destek talepleri
- `Category`: Taleplerin kategorileri
- `Auth`: JWT üretimi ve doğrulaması
- `Exception`: Global hata yönetimi
- `Config`: Security ve JWT yapılandırmaları

## 🚀 Başlangıç

### 1. Projeyi Çalıştırmak

```bash
./mvnw spring-boot:run
