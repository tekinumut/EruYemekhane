Uygulamaya Google Play üzerinden erişmek için [tıklayınız.](https://play.google.com/store/apps/details?id=com.Mtkn.umutt.eruyemekhane)

EruYemekhane Erciyes Üniversitesinin yemekhane listelerini gösteren bir Android uygulamasıdır.                                         
Öğrenci ve Personel yemek listesini ayrı ayrı gösterir.     
Uygulama internete bağlı iken aldığı son kayıtları hafızasında tutar. Böylece daha sonra bu verilere çevrimdışı erişilebilir.

### Öğrenci Yemek Listesi

Akşam ve Öğle menüleri aynı olduğundan bu bölümde sadece öğle menüleri kullanıcıya gösterilir.

![1ogrenci](https://user-images.githubusercontent.com/33953921/53304512-550a7580-3887-11e9-8b5a-0137adde608b.png)

### Personel Yemek Listesi

![2personel](https://user-images.githubusercontent.com/33953921/53304514-550a7580-3887-11e9-94f2-f1cd280008a0.png)

### Hakkında

![3hakkinda](https://user-images.githubusercontent.com/33953921/53304515-550a7580-3887-11e9-8def-3bc59f66b0ae.png)


### Teknik Detaylar
Veriler üniversitenin sitesinden jsoup kütüphanesi ile GetValuesWithAsync sınıfı tarafından çekilmiştir. Daha sonra bu veriler belirli bir düzende RecylerView'a aktarılır ve kullanıcıya gösterilir.
Uygulamaya çevrimdışı girildiğinde verilerin kayıt edilip kullanıcıya gösterilebilmesi için ROOM kütüphanesi kullanılmıştır. 