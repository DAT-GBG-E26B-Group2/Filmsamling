# Filmsamling

Et Java CRUD-program til en filmsamling – 1. semester, datamatiker, EK.

## Gruppen



| Fornavn | GitHub-brugernavn |
|---|---|
| Dainis | DainisM |
| Mathis | Mazze0 |

## Sådan køres programmet

1. Klon GitHub-repositoriet, og åbn projektet i IntelliJ IDEA.
2. Sørg for, at **JDK 25** er installeret og valgt under Project SDK.
3. Åbn `src/main/java/Main.java`.
4. Kør `Main.main()` ved at trykke på den grønne Run-knap.
5. Følg menuen i konsollen.

**Main-klasse:** `Main`  
**Java-version:** JDK 25  
**Build-værktøj:** Maven  
**Test-framework:** JUnit 5.14.4

## Projektstruktur

- `src/main/java/ui` – brugerflade og menuer.
- `src/main/java/domainmodel` – film, filmsamling, Controller og forretningslogik.
- `src/main/java/datasource` – gemning og indlæsning af film.
- `src/test/java` – automatiserede JUnit-tests.
- `docs/klassediagram.md` – klassediagram.
- `docs/furps.md` – FURPS-krav.


## Tests

Projektet bruger JUnit 5.14.4 til automatiserede tests.

Kør testene i IntelliJ eller via terminalen:


## Git og dokumentation

Projektet bruger `.gitignore` til at udelukke lokale og genererede filer såsom `target/`, `*.class` og `movies.csv`.

Dokumentationen ligger i `docs/`:

- `klassediagram.md` – projektets klassediagram.
- `furps.md` – krav inden for Functionality, Usability, Reliability, Performance og Supportability.