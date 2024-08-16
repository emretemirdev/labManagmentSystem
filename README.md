
# Laboratuvar Raporlama Sistemi

---

## Giriş
 https://github.com/users/omertemir08/emails/334627779/confirm_verification/72b8a97390fd8c4a13634f5a8ec784e76fc86ee1
Bu proje, başlangıç düzeyinde bir laboratuvar raporlama uygulamasıdır. **Spring, JPA, Maven ve React** teknolojilerini kullanarak geliştirilmiş olup, laboratuvar çalışanlarının hastalar üzerinde yapılan testlerin sonuçlarını sistematik bir şekilde kaydetmelerini ve yönetmelerini sağlar.

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
- [Use Cases](#use-cases)
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
## Use Cases

1. **Kullanıcı Yönetimi:**
   - **UC-1.1 Kullanıcı Kaydı:**
     - **Aktör:** Yeni kullanıcı
     - **Senaryo:** Yeni kullanıcı, sisteme kayıt olmak için gerekli bilgileri (ad, kullanıcı adı, şifre, hastane ID) girer. Sistem, kullanıcı bilgilerini doğrular ve kullanıcıyı veri tabanına kaydeder.
     - **Alternatif Senaryolar:**
       - Kullanıcı adı veya hastane ID daha önce kullanılmış ise, sistem bir hata mesajı döndürür.
       - Şifre belirli güvenlik kriterlerini karşılamıyorsa, sistem bir uyarı mesajı döndürür.
   
   - **UC-1.2 Kullanıcı Girişi:**
     - **Aktör:** Kayıtlı kullanıcı
     - **Senaryo:** Kullanıcı, kullanıcı adı ve şifresiyle sisteme giriş yapar. Sistem, bilgileri doğrular ve başarılı girişlerde JWT token üretir.
     - **Alternatif Senaryolar:**
       - Hatalı kullanıcı adı veya şifre girildiğinde, sistem bir hata mesajı döndürür.
   
2. **Rapor Yönetimi:**
   - **UC-2.1 Rapor Oluşturma:**
     - **Aktör:** Laborant
     - **Senaryo:** Laborant, yeni bir rapor oluşturmak için hasta bilgilerini, tanı bilgilerini, rapor tarihini ve isteğe bağlı olarak rapor resmi ekler. Sistem, raporu veri tabanına kaydeder ve bildirim oluşturur.
     - **Alternatif Senaryolar:**
       - Rapor resmi çok büyük ise, sistem resmi yeniden boyutlandırabilir veya bir hata mesajı döndürebilir.

   - **UC-2.2 Rapor Görüntüleme:**
     - **Aktör:** Laborant, Admin
     - **Senaryo:** Kullanıcı, belirli bir raporu ID'sine göre veya filtreleme seçeneklerini kullanarak görüntüleyebilir.
     - **Alternatif Senaryolar:**
       - Rapor bulunamadığında, sistem bir hata mesajı döndürür.

   - **UC-2.3 Rapor Güncelleme:**
     - **Aktör:** Laborant
     - **Senaryo:** Laborant, mevcut bir raporun bilgilerini güncelleyebilir. Sistem, güncellenen raporu kaydeder ve bildirim oluşturur.

   - **UC-2.4 Rapor Silme:**
     - **Aktör:** Admin
     - **Senaryo:** Admin, belirli bir raporu ID'sine göre silebilir. Sistem, raporu veri tabanından siler ve bildirim oluşturur.
     - **Alternatif Senaryolar:**
       - Rapor bulunamadığında, sistem bir hata mesajı döndürür.

3. **Bildirim Sistemi:**
   - **UC-3.1 Bildirim Oluşturma:**
     - **Aktör:** Sistem
     - **Senaryo:** Rapor oluşturma, güncelleme veya silme işlemi gerçekleştiğinde, sistem bir bildirim oluşturur ve admin kullanıcısına gösterir.

   - **UC-3.2 Bildirim Görüntüleme:**
     - **Aktör:** Admin
     - **Senaryo:** Admin, tüm bildirimleri görüntüleyebilir.

   - **UC-3.3 Bildirim Silme:**
     - **Aktör:** Admin
     - **Senaryo:** Admin, belirli bir bildirimi veya tüm bildirimleri silebilir.

---

## Katkıda Bulunma

Projemize katılın ve yardımcı olun:
* Yardıma ihtiyacımız olan [açık sorunlar](https://github.com/emretemirdev/labManagmentSystem/issues?q=is%3Aissue+is%3Aopen) listesine göz atın.
* Yeni özelliklere ihtiyacınız varsa, lütfen [yeni bir sorun](https://github.com/emretemirdev/labManagmentSystem/issues) açın.
* Bir çekme isteği (pull request) oluştururken, lütfen inceleme ve test süreçlerinde geçen zamanı göz önünde bulundurun ve uygun kodlama stilini koruyun.


---

