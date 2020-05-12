package com.Mtkn.umutt.eruyemekhane.library

class ZBackUP {
    companion object {
        // Her iki listeninde dolu olduğu örnek html verisi
        const val fullList = "<div class=\"HizmetStandartHeader\">\n" +
                "    <a href=\"https://web.archive.org/web/20190208202127/http://hastaneler.erciyes.edu.tr/Sayfa/yemeklistesi/3010\" target=\"_blank\"><i class=\"fa fa-arrow-right\"></i>Erciyes Üniversitesi Tıp Fakültesi Hastanesi Yemek Listesi</a>\n" +
                "    <a href=\"javascript:void(0)\" class=\"btnPersonel\"><i class=\"fa fa-arrow-down\"></i>Erciyes Üniversitesi Personel Yemek Listesi</a>\n" +
                "    <div class=\"Personel\">\n" +
                "        <div class=\"ListeSatir\"> <div class=\"YemekTarih\">8 Şubat 2019 Cuma</div> <div class=\"YemekListe\"> <ul>   <li>TARHANA ÇORBASI</li> <li>ADANA KÖFTE-PİYAZ Kalori</li> <li>MERCİMEKLİ BULGUR PİLAVI</li> <li>RUS SALATASI</li> <li>MEYVE</li></ul> </div> </div> <div class=\"ListeSatir\"> <div class=\"YemekTarih\">11 Şubat 2019 Pazartesi</div> <div class=\"YemekListe\"> <ul>   <li>KAŞARLI DOMATES ÇORBASI</li> <li>ETLİ MEVSİM TÜRLÜ</li> <li>PEYNİRLİ MİLFÖY BÖREĞİ</li> <li>HAVUÇ SALATASI</li> <li>AYRAN</li></ul> </div> </div> <div class=\"ListeSatir\"> <div class=\"YemekTarih\">12 Şubat 2019 Salı</div> <div class=\"YemekListe\"> <ul>   <li>YAYLA ÇORBA</li> <li>YUMURTALI ISPANAK</li> <li>PEYNİRLİ MAKARNA</li> <li>PATATES SALATASI</li> <li>AYVA KOMPOSTO</li></ul> </div> </div> <div class=\"ListeSatir\"> <div class=\"YemekTarih\">13 Şubat 2019 Çarşamba</div> <div class=\"YemekListe\"> <ul>   <li>MERCİMEK ÇORBA</li> <li>TAVUK BAGET</li> <li>BULGUR PİLAVI</li> <li>MEVSİM SALATA</li> <li>SÜTLAÇ</li></ul> </div> </div> <div class=\"ListeSatir\"> <div class=\"YemekTarih\">14 Şubat 2019 Perşembe</div> <div class=\"YemekListe\"> <ul>   <li>DÜĞÜN ÇORBASI</li> <li>KADINBUDU KÖFTE-PİYAZ Kalori</li> <li>ERİŞTE PİLAVI</li> <li>ZEYTİNYAĞLI FASULYE</li> <li>MEYVE</li></ul> </div> </div> <div class=\"ListeSatir\"> <div class=\"YemekTarih\">15 Şubat 2019 Cuma</div> <div class=\"YemekListe\"> <ul>   <li>ŞEHRİYE ÇORBASI</li> <li>PİLAVÜSTÜ ÇOBAN KAVURMA</li> <li>AYRAN</li> <li>HAŞHAŞLI REVANİ</li> <li>MEVSİM SALATA</li></ul> </div> </div> \n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "        <a href=\"javascript:void(0)\" id=\"MainContent_ContentPlaceHolder1_ctl00_OgrenciLink\" class=\"btnOgrenci\"><i class=\"fa fa-arrow-down\"></i>Erciyes Üniversitesi Öğrenci Yemek Listesi</a>\n" +
                "        <div id=\"MainContent_ContentPlaceHolder1_ctl00_OgrenciListe\" class=\"Ogrenci\">\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">8 Şubat 2019 Cuma / Öğle Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>TERBİYELİ KÖFTE (521 KAL)</li> <li>ERİŞTE PİLAVI (278 KAL)</li> <li>MEVSİM SALATA (45 KAL)</li> <li>ŞEKERPARE TATLISI (183 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">11 Şubat 2019 Pazartesi / Öğle Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>MERCİMEK ÇORBA (225 KAL)</li> <li>CORDON BLUE-PİYAZ (454 KAL) Kalori</li> <li>MAKARNA (282 KAL)</li> <li>YOĞURT (70 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">11 Şubat 2019 Pazartesi / Akşam Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>MERCİMEK ÇORBA (225 KAL)</li> <li>CORDON BLUE-PİYAZ (454 KAL) Kalori</li> <li>MAKARNA (282 KAL)</li> <li>YOĞURT (70 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">12 Şubat 2019 Salı / Öğle Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>TAS KEBABI (661 KAL)</li> <li>PEYNİRLİ MİLFÖY BÖREK (319 KAL)</li> <li>AYRAN (46 KAL)</li> <li>ŞEKERPARE TATLISI (183 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">12 Şubat 2019 Salı / Akşam Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>TAS KEBABI (661 KAL)</li> <li>PEYNİRLİ MİLFÖY BÖREK (319 KAL)</li> <li>AYRAN (46 KAL)</li> <li>ŞEKERPARE TATLISI (183 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">13 Şubat 2019 Çarşamba / Öğle Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>EZOGELİN ÇORBA (164 KAL)</li> <li>TAVUK BUT (710 KAL)</li> <li>MEVSİM SALATA (45 KAL)</li> <li>PORTAKAL (150 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">13 Şubat 2019 Çarşamba / Akşam Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>EZOGELİN ÇORBA (164 KAL)</li> <li>TAVUK BUT (710 KAL)</li> <li>MEVSİM SALATA (45 KAL)</li> <li>PORTAKAL (150 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">14 Şubat 2019 Perşembe / Öğle Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>İZMİR KÖFTE (393 KAL)</li> <li>PİRİNÇ PİLAVI (300 KAL)</li> <li>KIRMIZI LAHANA SALATA (45 KAL)</li> <li>KREM ŞOKOLA (275 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">14 Şubat 2019 Perşembe / Akşam Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>İZMİR KÖFTE (393 KAL)</li> <li>PİRİNÇ PİLAVI (300 KAL)</li> <li>KIRMIZI LAHANA SALATASI (45 KAL)</li> <li>KREM ŞOKOLA (275 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">15 Şubat 2019 Cuma / Öğle Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>YAYLA ÇORBA (183 KAL)</li> <li>KIYMALI ISPANAK (280 KAL)</li> <li>PEYNİRLİ MAKARNA (390 KAL)</li> <li>AYVA KOMPOSTO (100 KAL)</li> </ul> </div> </div> <div class=\"ListeSatirOgr col-lg-5 col-md-5 col-sm-5 col-xs-12\"> <div class=\"YemekTarih\">15 Şubat 2019 Cuma / Akşam Menüsü</div> <div class=\"YemekListe\"> <ul>   <li>YAYLA ÇORBA (183 KAL)</li> <li>KIYMALI ISPANAK (280 KAL)</li> <li>PEYNİRLİ MAKARNA (390 KAL)</li> <li>AYVA KOMPOSTO (100 KAL)</li> </ul> </div> </div> \n" +
                "\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "    \n" +
                "</div>"

        // Sadece personel listesinin dolu olduğu örnek html verisi
        const val halfList = "<div class=\"HizmetStandartHeader\">\n" +
                "    <a href=\"http://hastaneler.erciyes.edu.tr/Sayfa/yemeklistesi/3010\" target=\"_blank\"><i class=\"fa fa-arrow-right\"></i>Erciyes Üniversitesi Tıp Fakültesi Hastanesi Yemek Listesi</a>\n" +
                "    <a href=\"javascript:void(0)\" class=\"btnPersonel\"><i class=\"fa fa-arrow-down\"></i>Erciyes Üniversitesi Yemek Listesi</a>\n" +
                "    <div class=\"Personel\">\n" +
                "        <div class=\"ListeSatir\"> <div class=\"YemekTarih\">8 Mayıs 2020 Cuma</div> <div class=\"YemekListe\"> <ul>   <li>YEMEKLER KUMANYA ŞEKLİNDE VERİLECEKTİR.</li> <li>ETLİ PATATES YEMEĞİ</li> <li>MİLFÖY BÖREK</li> <li>YOĞURT</li> <li>ELMA</li></ul> </div> </div> \n" +
                "\n" +
                "    </div>\n"
    }
}