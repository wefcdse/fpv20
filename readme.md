# fpv20

## description

another fpv mod for Minecraft 1.20.1 fabric

inspired by [Minecraft FPV](https://www.curseforge.com/minecraft/mc-mods/fpv-drone)

video: https://www.bilibili.com/video/BV1o4421c7Ek/

!!! early development, but it's safe to use, since it will not break the save

## links

[curseforge](https://www.curseforge.com/minecraft/mc-mods/fpv20)

[github](https://github.com/wefcdse/fpv20)

## usage

the gui entry is in options - controls

playing fpv:

- select a controller
- set the sticks' channel name to:
    - throttle: `t`
    - yaw: `y`
    - pitch: `p`
    - roll: `r`
- set a switch's channel name to `sw`
- set throttle's calibration type to `max min`
- enter a world, turn the switch. and enjoy flying!

use the receiver block:

- select a controller
- enter a world
- put down a `receiver block`
- click on the `receiver block`
- set the channel to receive in the GUI
- set whether to receive the negative or
  the positive half of the channel.
  the output is always `abs(signal) * 15`

## use this or use [Minecraft FPV](https://www.curseforge.com/minecraft/mc-mods/fpv-drone)

why you should use this:

- you want to play in 1.20.1 and fabric
- you want a better performance. 1.20.1 fabric has
  more modern optimize mods
- you want to use a controller's input as a redstone signal

why you should use [Minecraft FPV](https://www.curseforge.com/minecraft/mc-mods/fpv-drone)

- you need a better and easy to use gui
- you need some more realistic visual effects, such as
  fish eye and noise
- you need a better physic simulation
- you want a configurable drone
- you need better sound effect
- you need to play in forge. This mod is in fabric,
  and it **cannot** run correctly in forge
  by [Sinytra Connector](https://www.curseforge.com/minecraft/mc-mods/sinytra-connector) currently

## advertisement

[simply snow](https://www.curseforge.com/minecraft/mc-mods/simply-snow)