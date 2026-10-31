# FURPS – vores filmsamling

| | Krav | Opfyldt? | Hvor |
|---|---|---|---|
| F – Functionality | Brugeren kan søge efter film ved at indtaste en del af titlen, uden at store og små bogstaver har betydning. | Ja – i MovieCollection | `MovieCollection.searchMovies()` og JUnit-tests |
| U – Usability | Programmet viser en besked, hvis brugeren vælger et ugyldigt menupunkt. | Ja | `UserInterface.StartProgram()` |
| R – Reliability | Programmet skal håndtere bogstaver i stedet for tal ved indtastning af årstal uden at gå ned. | Nej – mangler | `UserInterface.createMovie()` |
| P – Performance | Programmet skal kun gemme filmsamlingen til en fil, når der er foretaget ændringer. | Nej – planlagt til senere | Kommende filhåndtering |
| S – Supportability | Filmhåndtering skal ligge i `MovieCollection`, og logikken skal være dækket af automatiserede JUnit-tests. | Delvist – tests for Movie og MovieCollection | `MovieCollection.java`, `MovieTest.java`, `MovieCollectionTest.java` |
