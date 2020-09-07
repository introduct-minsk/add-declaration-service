# Kirjeldus

Tegu on prooviülesande teostusega Riigi Infosüsteemi Ameti riigihanke ["Nõusolekuteenuse terviklahenduse arendus"](https://riigihanked.riik.ee/rhr-web/#/procurement/1642354/general-info) raames.  
Prooviülesanne on teostatud vastavalt Riigi Infosüsteemi Ameti poolt avaldatud [spetsifikatsioonile](https://riigihanked.riik.ee/rhr-web/#/procurement/1642354/documents/source-document?group=B&documentOldId=13200689) ning võttes arvesse kõik spetsifikatsioonis toodud nõudmised.  

Tegu on addServiceDeclaration API mikroteenusega, mis võimaldab teenuse deklarandil (kliendil) deklareerida oma teenust registris.  
Käesoleva koodi näol, on tegemist **tervikliku** lahendusega, mis sisaldub endas:  

*   addServiceDeclaration API mikroteenust, Docker konteinerisse pakkituna.
*   PostgreSQL andmebaasi, Docker konteinerisse pakkituna - ühe õla konfiguratsioonis (*)
*   NGinx koormusjaoturi, Docker konteinerisse pakkituna - ühe õla konfiguratsioonis (*)

Kogu kood paigaldusel on pakitud Docker konteineritesse, ning pannakse kokku paigaldusel automaatselt Docker compose skriptidega.  
Teisisõnu, koodi paigaldus, jooksutamine ja kasutamine ei vaja midagi, peale seda, mis on jagatavas pakendis olemas.  
Kõik vajalikud komponendid on antud tarnes kaasa pandud.  

_(*) - Vaikekonfiguratsioonis, Docker compose paigaldab addServiceDeclaration mikroteenuse kahe-õla konfiguratsioonis, kus paigaldatav Nginx koormusjaotur hakkab jagama koormust kahe instantsi vahel lihtsal round-robin põhimõttel. Andmebaasi ning koormusjaoturi paigaldatakse ühe instantsi konfiguratsioonis, mis teeb neist definitsiooni järgi SPoF-i. See on teadlik valik, kuna tegemist on siiski prooviülesandega, ning me ei seadnud endale eesmärki teha paigaldust keerulisemaks või raskemini arusaadavaks. Tootmiskonfiguratsioonis paigaldatava andmebaasi ning koormusjaoturi konfiguratsioone annab väga lihtsasti modifitseerida, et omada 2 või enam instantsi koormusjaoturist ning andmebaasist: addServiceDeclaration mikroteenus on karbist võimeline töötama connection pool loogikaga toetamas klasterdatud andmebaaside konfiguratsiooni.  
Siiski, me teadlikult ei ole tarninud ei andmebaasi, ega ka koormusjaoturi klasterdatud konfiguratsioonis, selleks et võimaldada võimalikult lihtne ja straightforward rakenduse paigaldus. **Mikroteenus ise, on välja ehitatud stateless arhitektuuris ning ka vaikepaigalduses töötab kahe-õla konfiguratsioonis.** Soovi korral, paigalduse hetkel, käitudes vastavalt paigaldusjuhendile - võib mikroteenuse instantside arvu tõsta mistahes numbrile - koormusjaotur paigaldamisel automaatselt võtab kõik instantsid kasutusele ning jagab koormust nende vahel round-robin põhimõttel._


# TLS

Kõik rakenduse komponendid suhtlevad omavahel turvatud TLS ühenduste abil. Vaikimisi kasutatakse iseväljastatud (self-signed) sertifikaate. Sertifikaatide konfiguratsiooni määratakse rakenduse ehitamisel konfiguratsiooni failides. (vt. paigaldjuhendit täpsemate juhiste saamiseks)


# CI/CD

Rakendus tuleb automaatse CI/CD konfiguratsiooniga karbist, ning on võimeline olla Jenkins-iga automaatselt ehitatud, ning paigaldatud arenduskeskkonda. Jenkins-i juhiste fail asub koodi puu kõige ülemisel tasemel. (käesoleva failiga samal tasemel) 


# Monitooring

Kõik rakenduse konteinerid omavad heartbeat lehekülge, mis on iga konteineri instantsi küljest kättesaadav URL-ist: /api/v1/actuator/health.
Näide meie testkeskkonnas: [https://3.249.32.190:31524/api/v1/actuator/health](https://3.249.32.190:31524/api/v1/actuator/health)
Heartbeat lehekülg väljastab ühe välja, milleks on "status", ning kui välja väärtuseks on mistahes muu väärtus, kui "UP", tegu on probleemiga teenuse instantsis ning instantsi tuleb pool-ist eemaldada. Samu heartbeat lehekülge kasutab teenuse kontrollimiseks vaikimisi paigaldatav Nginx koormusjaotur.

# Logimine

Rakenduse paigaldusjuhendis, konfiguratsiooni seadistamisel on kohustuslik märkida syslog server, kuhu rakendus peaks oma loge striimina kirjutama.  
Oma testkeskkonnas, me kasutame ELK stackil põhineva serveri. Kuna me usume, et lugupeetud kliendil on olemas oma logide keskserver, me otsustasime, et selle tarnimine koos lahenduse oleks ebamõistlik, seega teeme ettepaneku rakenduse paigaldusel, spetsifitseerida rakendusele logiserveri asukohta, kuhu rakendus hakkaks loge kirjutama. (vt. paigaldusjuhendi täpsemate juhiste saamiseks)  

Rakenduse paigaldusjuhendis, konfiguratsiooni seadistamisel on kohustuslik märkida syslog server, kuhu rakendus peaks oma loge striimina kirjutama.  
Oma testkeskkonnas, me kasutame ELK stackil põhineva serveri. Kuna me usume, et lugupeetud kliendil on olemas oma logide keskserver, me otsustasime, et selle tarnimine koos lahenduse oleks ebamõistlik, seega teeme ettepaneku rakenduse paigaldusel, spetsifitseerida rakendusele logiserveri asukohta, kuhu rakendus hakkaks loge kirjutama. (vt. paigaldusjuhendi täpsemate juhiste saamiseks)  

# Testimine

Testimise protsess ja testjuhtumid on detailselt kirjeldatud testimisjuhendis. Palun kasutage testimisjuhendus toodud testjuhtumeid teenuse testimiseks.  

# Integratsiooni ja koormustestimine

Integratsiooni ja koormustestimiseks, kujul, mis on nõutud prooviülesande spetsifikatsioonis teeme ettepaneku kasutada JMeter platvormi.  
Kuigi JMeter ei ole mõeldud otseselt integratsiooni testimiseks, ja pigem koormustestide teostamiseks, siiski näeme ta täitmas kõiki, integratsioonitestide osas prooviülesande spetsifikatsioonis nõutud tingimusi ning sellise püstituse puhul, arvame, et JMeter võiks olla parim lahendus. Toode on tarnitud koos valmis kirjutatud JMeter testidega ning ka juhistega, kuidas ja mis moodi neid käivitada ja testida. Palun leidke kõik juhised vastavast peatükkist testimisjuhendis.  

# Viited test keskondadele ning API dokumentatsioonile

Allpool toome viiteid meie enda keskkondadele, ning täiendavale dokumentatsioonile tarnitud lahenduse osas.  

*   Test keskkond Amazon AWS avalikus pilves: [https://3.249.32.190:31524/api/v1/declarations](https://3.249.32.190:31524/api/v1/declarations)
*   Ligipääs test keskkonna ELK logiserveri vaaterežiimile (Kibana): [https://52.31.16.246:41513/app/kibana#/discover?_g=()](https://52.31.16.246:41513/app/kibana#/discover?_g=())
    Kasutajatunnus: dev.user  
    Parool: flXC232mQQ1g
*   OpenAPI v3 formaadis API kirjeldus ja dokumentatsioon kättesaadav Swaggeri keskkonnas: [https://3.249.32.190:31524/api/v1/api-documentation.html](https://3.249.32.190:31524/api/v1/api-documentation.html)
*   Lähtekoodi repositaarium kättesaadav: [http://52.18.46.203:7990/projects/EES/repos/add-declaration-service/browse](http://52.18.46.203:7990/projects/EES/repos/add-declaration-service/browse)
    Kasutajatunnus: dev.user  
    Parool: flXC232mQQ1g

_Meeldivat kasutamist!_