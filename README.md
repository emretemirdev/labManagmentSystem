
# Laboratuvar Raporlama Sistemi

---

## Giriş

Bu proje, basit düzeyde bir laboratuvar raporlama uygulamasıdır. **Spring, JPA, Maven ve React** teknolojilerini kullanarak geliştirilmiş olup, laboratuvar çalışanlarının hastalar üzerinde yapılan testlerin sonuçlarını sistematik bir şekilde kaydetmelerini ve yönetmelerini sağlar.

Sistem, her bir rapor için hastanın adı, soyadı, kimlik numarası, konulan tanı, tanı detayları ve rapor tarihi gibi bilgileri içeren detaylı bir veritabanı yapısına sahiptir. Ayrıca, laboratuvar çalışanlarının raporları kolayca sıralayabilmeleri, arayabilmeleri ve gerekli güncellemeleri yapabilmeleri için arayüzler sunar.

Bu README dosyası, projenin nasıl kurulacağı ve kullanılacağı hakkında temel bilgileri, sistem gereksinimlerini ve kurulum adımlarını içermektedir. Ayrıca, projede yapılan teknik seçimler ve bu seçimlerin gerekçeleri hakkında bilgiler de bulabilirsiniz.

Bu uygulama, açık kaynak bir projedir ve GitHub üzerinde herkese açık olarak geliştirilmektedir, böylece katkıda bulunmak isteyen herkes bu projeyi fork ederek kendi geliştirmelerini yapabilir.


---

## İçindekiler
- [Giriş](#giriş)
- [Sistem Gereksinimleri](#sistem-gereksinimleri)
- [Projeyi Klonlamak](#projeyi-klonlamak)
- [Derleme ve Çalıştırma](#derleme-ve-çalıştırma)
  - [Docker ile Derleme](#docker-ile-derleme)
  - [Java & Node.js ile Derleme](#java--nodejs-ile-derleme)
- [Uygulamaya Erişim](#uygulamaya-erişim)
- [Katkıda Bulunma](#katkıda-bulunma)
---

## Sistem Gereksinimleri

**Docker ile Ayağa Kaldırma**

* Docker: Docker kurulu ve en az v20.10.3 sürümü kullanıyor olmalı.

**React ve Spring Boot ile Ayağa Kaldırma**

* Java: Java 17 veya üstü kurulu olmalı. 
* Node.js: Node.js 14.17.3 veya üstü kurulu olmalı.

---

## Projeyi Klonlamak

Proje deposunu klonlamak için aşağıdaki komutu kullanın:
```bash
git clone https://github.com/emretemirdev/labManagmentSystem.git
```


---

## Derleme Ve Çalıştırma

Projeyi lokal ortamınızda derlemek ve çalıştırmak için iki yöntem sunuyorum: Docker kullanarak ve doğrudan Java ve Node.js ortamlarını kullanarak. Aşağıdaki adımları takip ederek projeyi kurabilir ve çalıştırabilirsiniz.



> 💡 Seçeceğiniz yönteme göre hangi sistem gereksinimlerine ihtiyacınız olduğunu kontrol edin. <a href="#sistem-gereksinimleri">Sistem Gereksinimleri</a> bölümüne bakarak gerekli kurulumları yapın.

### `Docker` ile derleme 

<details open><summary title="Göstermek / gizlemek için tıklayın ">Göstermek / gizlemek için tıklayın <code>Docker ile derleme</code>.</summary><br/>

Docker, uygulamayı herhangi bir sistemde kolayca kurmanıza ve izole bir ortamda çalıştırmanıza olanak tanır. Docker'ın bilgisayarınızda kurulu olduğundan emin olun.

docker-compose.yml dosyası projenin hem derleme (build) hem de çalıştırma (up) işlemlerini yönetmek için kullanılır. Aşağıdaki komutu kullanarak Docker imajını oluşturabilir ve uygulamayı başlatabilirsiniz:
Projenin ana dizinindeyken lütfen aşağıdaki kodu çalıştırın.

```bash
docker-compose up --build
```

Bu komut Docker imajlarını otomatik olarak oluşturacak ve ardından uygulamanın tüm servislerini çalıştıracaktır. Uygulamaya http://localhost:3000 adresinden erişebilirsiniz.
</details>

### `Java & Node.js` ile derleme

<details open><summary title="Göstermek / gizlemek için tıklayın ">Göstermek / gizlemek için tıklayın <code>Docker ile derleme</code>.</summary><br/>
1. Bağımlılıkları Yükleyin:
Backend ve frontend için gerekli olan bağımlılıkları yüklemek üzere aşağıdaki komutları çalıştırın:

Backend için:

```bash
cd backend
mvn install
```
Frontend için:

```bash
cd frontend
npm install
```
2. Uygulamayı Çalıştırın: Her iki kısmı ayrı ayrı çalıştırmanız gerekmektedir. İlk olarak backend sunucusunu başlatın:

```bash
cd api
mvn spring-boot:run
```
```bash
cd frontend
npm start
```
Bu komutlar, backend ve frontend sunucularını başlatacak ve uygulamayı http://localhost:3000 adresinden erişilebilir hale getirecektir.

</details>

---
## Uygulamaya Erişim

Admin olarak giriş yapmak için aşağıdaki bilgileri kullanabilirsiniz:
```
kullanıcı adı: admin
şifre: 1234
```
Laborant olarak giriş yapmak için aşağıdaki bilgileri kullanabilirsiniz:

```
kullanıcı adı: test
şifre: 1234
```
---

## Katkıda Bulunma

Projemize katılın ve yardımcı olun:
* Yardıma ihtiyacımız olan [açık sorunlar](https://github.com/emretemirdev/labManagmentSystem/issues?q=is%3Aissue+is%3Aopen) listesine göz atın.
* Yeni özelliklere ihtiyacınız varsa, lütfen [yeni bir sorun](https://github.com/emretemirdev/labManagmentSystem/issues) açın.
* Bir çekme isteği (pull request) oluştururken, lütfen inceleme ve test süreçlerinde geçen zamanı göz önünde bulundurun ve uygun kodlama stilini koruyun.


---

