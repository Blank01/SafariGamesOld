# SafariGamesOld
Old sidemod for pixelmon that adds SafariGames
The server needs to be properly setup with custom spawn rates and starting pokemons.
It also needs Trading areas with traders, a Pokecenter and a Tournament area with enough arenas.

Last tested on Pixelmon 5.0.1

# How it works
There are 6 phases:
-Join phase
-Training phase 
-Middle phase
-Tournament phase
-Post phase

# Join phase
This phase lasts 5 minutes and players can join the server freely. After they chose their starting pokemon they will find themselves at the spawn point with their team dead so as to not being able to battle.

# Training phase
Players teams are healed.
Server switches to whitelist mode making it possible only for those who logged in during the Join phase to join.
Players are teleported to a pokecenter and are given some starting items, such as pokeballs and rare candies.
Players are now free to run around the world to catch and train their pokemons and use warps to teleport to designated biomes.
This phase lasts around 30 minutes.

# Middle phase
Players are teleported to a trading area where they can sell and buy stuff in order to prepare for the next phase.
This phase lasts 5 minutes.

# Tournament phase
Players are teleported to the tournament area and all their pokemons are killed in order to prevent fights outside tournament.
The tournament is a direct elimination tournament.
At the start of each tournament phase a message with all the matches is displayed and every time a match ends a message showing the winner is displayed.
Matches should last no more than 5 minutes.
After the last match of the tournament has ended the winner is displayed in chat.

# Post phase
After the tournament players are teleported to the pokecenter of the training phase and are free to do whatever they want.
In this phase there is no time limit.

# Commands
- /battlefault 
  Can be used during the training phase to stop a fight between pokemon in case it got stuck
- /evolve <number>
  Evolves the pokemon in team position <number>
- /warp ["warpName"]
  Warps the player to "warpName" which could be a biome or a custom warp

# Admin commands
- /killteam <player>
  Kills the team of a player
- /safari startgame
  Starts the game
- /setwarp <biome>
  In case "biome" option is selected adds a warp to the biome the player is currently standing on(only one warp per biome)
- /setwarp <arena> [1|2|save]
  To add an arena first call the command with the "1" option to set the first player position in an arena, then "2" for the second and finally "save" to confirm the arena
- /setwarp <"warpName">
  Adds a new custom warp named "warpName"
- /startgame
  Starts the Join phase
- /tournament add <player>
  Adds a player to the tournament
