## Design Pattern
We used the strategy pattern when dealing with the different menus. This way, the menu displays and handles options depending on the type of the user currently logged in (client, expert, admin). The way this is setup is with a `Menu` class that is inherited by three specialized classes being the `ClientMenu`, `ExpertMenu` and `AdminMenu`. The `InstitutionConsole` holds an instance of `Menu` and calls the `displayMainMenuOption()` and `handleMenuInput()` methods from the `Menu` instance without caring for what class is actually being used.

## Architecture
The architecture is a three layered one. The first group of classes is responsible for the interactions with the user such as displaying information or reading input. These are the `InstitutionConsole` and the `Menu` classes. The second group is responsible for managing data. Those are the `Repository` classes. The final group encompasses the rest of the classes that deal with business logic.
