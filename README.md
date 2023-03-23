# Special Nametags (Fabric)
<p><img src="https://img.shields.io/github/license/BisUmTo/specialnametags.svg" alt="GitHub license" width="102" height="20" />
<a href="https://multicore.network/discord" target="_blank" rel="noopener noreferrer"><img src="https://img.shields.io/badge/chat%20on-discord-7289D" alt="Discord chat" width="100" height="20" /></a>
<a href="https://www.curseforge.com/minecraft/mc-mods/specialnametags"><img src="http://cf.way2muchnoise.eu/full_841082_downloads.svg" alt="CurseForge downloads" width="118" height="20" /></a></p>

This small fabric mod allows you to have more special names to give to mobs (like "Dinnerbone").

# How to install

Download `.jar` file from [https://www.curseforge.com/minecraft/mc-mods/specialnametags](https://www.curseforge.com/minecraft/mc-mods/specialnametags)

Put the file in mod folder

# How to use

Rename mobs with special nametags to get special results:

| **Names**                                       | **Description**                                                                               | **NBT analogy**                                                                                                    |
|-------------------------------------------------|-----------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| `freeze` `noai` `congela` `congelato`           | Mob will no longer move and you will not be able to hit him.                                  | `{NoAI:true,Invulnerable:true}`                                                                                    |
| `unfreeze` `ai` `scongela` `scongelato`         | Mob will move normally and you will be able to hit him again.                                 | `{NoAI:false,Invulnerable:false}`                                                                                  |
| `silent` `silenzia` `muta` `mutato`             | Mob will no longer emit sounds.                                                               | `{Silent:true}`                                                                                                    |
| `unsilent` `smuta` `smutato`                    | Mob will emit sounds normally.                                                                | `{Silent:false}`                                                                                                   |
| `baby` `cucciolo` `bambino`                     | Mob will stay in baby form.                                                                   | `{IsBaby:true,Age:-24000}` (age restored to -24000 every 12000 ticks)                                              |
| `unbaby` `adult` `adulto`                       | Mob will grow up.                                                                             | `{IsBaby:false,Age:0}`                                                                                             |
| `gravity` `gravità`                             | Mob will be affected by gravity.                                                              | `{NoGravity:false}`                                                                                                |
| `nogravity` `ungravity` `floating` `fluttuante` | Mob will no longer be affected by gravity.                                                    | `{NoGravity:true}`                                                                                                 |
| `invulnerable` `invulnerabile`                  | Mob will be immune to not-creative damage.                                                    | `{Invulnerable:true}`                                                                                              |
| `vulnerable` `vulnerabile`                      | Mob will be able to take damage.                                                              | `{Invulnerable:false}`                                                                                             |                
| `noname` `unname` `nonome`                      | Mob will no longer have custom name, but without be able to despawn.                          | `{CustomName:'""'}`                                                                                                |
| `killer rabbit`                                 | Rabbit will convert into Evil variant.                                                        | `{RabbitType:99}`                                                                                                  |
| `nozombification` `nozombificazione`            | Piglin or Hoglin will no longer zombificate in Overworld.                                     | `{IsImmuneToZombification:true}`                                                                                   |
| `zombification` `zombificazione`                | Piglin or Hoglin will be able to zombificate in Overworld.                                    | `{IsImmuneToZombification:false}`                                                                                  |
| `johnny`                                        | Vindicator will stay in Evil state even if renamed.                                           | `{Johnny:true}`                                                                                                    |
| `unjohhny`                                      | Vindicator state is set to default one.                                                       | `{Johnny:false}`                                                                                                   |
| `noclip`                                        | Mob will not collide with blocks. (If he is affected by gravity, he will fall through blocks) | Not accessible through NBTs                                                                                        |
| `clip`                                          | Mob will normally collide with blocks.                                                        | No accessible through NBTs                                                                                         |
| `sit` `seduto`                                  | Mob will sit on the ground.                                                                   | Give `gb.sitted` tag to mob, summon an invisible armor_stand with `gb.sitted.armor_stand` and the mob will ride it |
| `sleep` `sdraiato`                              | Mob will lay down on the floor.                                                               | Give `gb.is_sleeping` tag to mob, `{SleepingX:${X},SleepingY:${Y},SleepingZ:${Z}}` every gametick.                 |
| `unsleep` `unsit` `alzato`                      | Mob will stand normally.                                                                      | Remove `gb.sitted` and `sb.is_sleeping` tags to mob, mobs unmounts the veicle.                                     |
