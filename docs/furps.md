# FURPS – Filmsamling

FURPS bruges til at beskrive kvalitetskravene til vores filmsamlingsprogram.

| Kategori | Krav | Status | Dokumentation |
|---|---|---|---|
| **F – Functionality** | Brugeren skal kunne søge efter film ved at indtaste en del af titlen, uanset store og små bogstaver. | Implementeret | `MovieCollection.searchMovies()` og `UserInterface.searchMovies()` |
| **U – Usability** | Programmet skal vise en forståelig fejlbesked, hvis brugeren vælger et ugyldigt menupunkt. | Implementeret | `UserInterface.StartProgram()` |
| **R – Reliability** | Programmet skal kunne håndtere ugyldige tal og datoer uden at lukke ned. | Delvist implementeret | Datoer håndteres i `UserInterface.markMovieAsWatched()`, men nogle metoder bruger stadig `Scanner.nextInt()` og `nextBoolean()` |
| **P – Performance** | Filmsamlingen skal kun gemmes til CSV, hvis der er foretaget ændringer. | Implementeret | `Controller.saveMoviesIfChanged()` og variablen `changed` |
| **S – Supportability** | Programmet skal være opdelt i packages og have automatiserede JUnit-tests. | Implementeret i projektstrukturen | `domainmodel`, `datasource`, `ui` og testklasser i `src/test/java` |

## Status og forbedringsmuligheder

Programmet understøtter oprettelse, visning, søgning, redigering og sletning af film samt gemning og indlæsning fra CSV.

Derudover kan brugeren markere en film som set i dag eller på en tidligere dato og finde film, der ikke er set i et bestemt antal dage.

Der er også implementeret sortering efter titel, instruktør, årstal, længde og genre.

**Mulige forbedringer:**

- Håndtere ugyldige tal og boolean-input i alle menufunktioner.
- Sortere en kopi af filmsamlingen i stedet for at ændre den oprindelige liste.
- Udvide JUnit-testene til at dække sortering og brugerfladens funktioner.