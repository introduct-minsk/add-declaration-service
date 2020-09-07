# INTEGRATSIOON JA KOORMUSTESTIMINE

Integratsiooni ja koormustestimise jaoks teeme ettepaneku kasutada JMeter platvormi.
Antud juhend lähtub eeldusest, et lugupeetud lugeja on tuttav JMeter platvormi üldpõhimõtetega ning oskab selle vaikeväärtustes paigaldada.

Selleks on lähtekoodi arhiivi ülemisel tasemel sisalduv valmis kirjutatud testide kogum ning JMeter platvormi jaoks mõeldud script, mis neid ka käivitab. Script asub failis run_target.jmx lähtekoodi arhiivi ülemisel tasemel.

## INTEGRATSIOONI JA KOORMUSTESTIDE KÄIVITAMINE

1. Installige Jmeter vaikeväärtustega ametlikust repositaariumist siit: http://jmeter.apache.org/download_jmeter.cgi, kuid ärge kasutage koormustestide käivitamise jaoks GUI-režiimi – see on üleliigne. GUI režiimi JMeter platvormis kasutame ainult testide loomiseks ja testimiseks.

2. Muutke HOST ja PORT muutujate väärtusi vastavalt koormusjaoturi välise IP aadressi ning pordi väärtusteks failis run_target.jmx (vt. paigaldusjuhendit rohkemate detailide jaoks):

```
<Arguments guiclass="ArgumentsPanel" testclass="Arguments" testname="User Defined Variables Server" enabled="true">

        <collectionProp name="Arguments.arguments">

          <elementProp name="HOST" elementType="Argument">

            <stringProp name="Argument.name">HOST</stringProp>

            <stringProp name="Argument.value">localhost</stringProp>

            <stringProp name="Argument.metadata">=</stringProp>

          </elementProp>

          <elementProp name="PORT" elementType="Argument">

            <stringProp name="Argument.name">PORT</stringProp>

            <stringProp name="Argument.value">7070</stringProp>

            <stringProp name="Argument.metadata">=</stringProp>

          </elementProp>
```

3. Muutke päringute intensiivsust sekundis vastavalt vajadusele samas failis.

4. Vastavalt valitud päringute intensiivsusele, on üsna tõenäoline, et tuleb suurendada Java heap-i väärtuse sobivamaks. (JMeteri enda jaoks, mitte API teenuses. JMeter on ka Java rakendus)
Selleks, muutke praegust env muutujat JMeteri enda pakettfailis, mida installisite selle juhendi punktis 1:
`HEAP = "- Xms1g -Xmx1g -XX: MaxMetaspaceSize = 256m"`

5. Integratsiooni ja koormustesti käivitamiseks, andke konsoolist käsk (olles samas kataloogis failiga run_target.jmx):
`<teekond jmeter-ile>/jmeter -n -t run_target.jmx -l <teekond failini kuhu salvestada tulemuste aruanne> -e -o <teekond veebiserveri kaustani, kuhu salvestada graafiline tulemuste aruanne>`

**TÄHELEPANU: ÄRGE KASUTAGE JMETER GUI-REŽIIMI KOORMUSE/INTEGRATSIOONI TESTIMISEKS, KÄIVITAGE TESTI KONSOOLIST. GUI REŽIIM ON MÕELDUD TESTIDE TESTIMISEKS JA ARENDAMISEKS.**

6. Peale testi lõpetamist, käivitage brauser ning avage veebiserveri aadres, mis teenindab jmeter käivitamisel määratud veebiaruande salvestamiseks mõeldud kausta.