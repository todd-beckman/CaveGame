# CaveGame

A project for a class. All of the cancerous "design" choices are the result of
an incompetent professor from CU Boulder. It would have been much easier to start from scratch, but alas, this is a class assignment.

This repository is used as a fallback to prevent loss of the assignment midway through.

## Save Data Protocol

A save data structure for this game was made for both player persistence and level design. Levels are simply
save files with the intended start location. The structure is as follows:

    - command:arg:arg&command:arg:arg& ... etc.

Separate commands using ampersands (&). The tilde (~) is used to indicate a new line. Do not place
any whitespace except for the ' ' space character in string descriptions. Let capital arguments be exact
and lowercase arguments be descriptions.


| Command | argument1 | argument2 | argument3 | argument4 |
|---------|-----------|-----------|-----------|-----------|
| action  | string (original dialog)| | | |
| addroom | Wall | | | |
| addroom | Room | string (room description) | | |

The following commands cannot be used until all CaveSites in question are added.

| Command | argument1 | argument2 | argument3 | argument4 |
|---------|-----------|-----------|-----------|-----------|
| setside | int (target CaveSite's ID) | int (direction using Direction enum) | int (destination's ID) | |
| putitem | Key | int (destination's room) | | |
| putitem | Treasure | int (destination room) | string (description) | |
| putplayer | int (destination's room) | | | |
| addroom | Door | int (dest Room's ID) | int (src Room's ID) | int (Key's ID) |

The following commands cannot be used until the Player's location has been set. These are also
commands that should only appear in in-progress save files, due to allowing the player to simply
start with the files.

| Command | argument1 | argument2 | argument3 | argument4 |
|---------|-----------|-----------|-----------|-----------|
| giveplayer | Key | int (Key's ID) | | |
| giveplayer | Treasure | string (description) | | |

