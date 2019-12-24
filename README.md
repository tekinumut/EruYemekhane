Uygulamaya Google Play üzerinden erişmek için [tıklayınız.](https://play.google.com/store/apps/details?id=com.Mtkn.umutt.eruyemekhane)

EruYemekhane Erciyes Üniversitesinin yemekhane listelerini gösteren bir Android uygulamasıdır.                                         
Öğrenci ve Personel yemek listesini ayrı ayrı gösterir.     
Uygulama internete bağlı iken aldığı son kayıtları hafızasında tutar. Böylece daha sonra bu verilere çevrimdışı erişilebilir.

### Öğrenci Yemek Listesi

Akşam ve Öğle menüleri aynı olduğundan bu bölümde sadece öğle menüleri kullanıcıya gösterilir.

![ogrenci](https://user-images.githubusercontent.com/33953921/71407512-424e0200-264c-11ea-837f-168b2b0afe55.png)

### Öğrenci Gece Yemek Listesi

![ogrenci_gece](https://user-images.githubusercontent.com/33953921/71407514-424e0200-264c-11ea-9034-34984d33eaac.png)

### Personel Yemek Listesi

![2personel](https://user-images.githubusercontent.com/33953921/53304514-550a7580-3887-11e9-94f2-f1cd280008a0.png)

### Ayarlar

![ayarlar](https://user-images.githubusercontent.com/33953921/71407510-424e0200-264c-11ea-837b-96a3bd2c9d2b.png)

### Hakkında

![hakkinda](https://user-images.githubusercontent.com/33953921/71407511-424e0200-264c-11ea-8cf0-0ea07f3a3fe9.png)

### Teknik Detaylar
Veriler https://www.erciyes.edu.tr/kategori/KAMPUSTE-YASAM/Yemek-Hizmetleri/22/167 adresinden alınmaktadır.
Veriler jsoup kütüphanesi ile çekilmektedir.
Background Thread gereksinimleri için Coroutine kullanılmıştır.
İnternet yokken son kayıtlı verileri kullanıcıya göstermek için ROOM kullanıldı.
Aradaki bağlantılar MVVM üzerinde lifecycle ile yaptıldı.
Tüm listeler recyclerView ile gösterilmektedir.
