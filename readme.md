# fpv20

## description

another fpv mod for Minecraft 1.20.1 fabric

it can also run in forge
by [Sinytra Connector](https://www.curseforge.com/minecraft/mc-mods/sinytra-connector).
when used in forge, you need to set `is_in_forge` to `true` in `config/fpv20_common.json`

inspired by [Minecraft FPV](https://www.curseforge.com/minecraft/mc-mods/fpv-drone)


[//]: # (!!! early development, but it's safe to use, since it will not break the save)

## links

[curseforge](https://www.curseforge.com/minecraft/mc-mods/fpv20)

[github](https://github.com/wefcdse/fpv20)

[video](https://www.bilibili.com/video/BV1o4421c7Ek/)


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

slow motion

- set one channel's name to the same as `config/fpv20_client.json/slow_motion_switch_name`
- when this channel's value is over 0.5, it starts slow motion,
the drone will fly slow.

use the receiver block:

- select a controller
- enter a world
- put down a `receiver block`
- click on the `receiver block`
- set the channel to receive in the GUI
- set whether to receive the negative or
  the positive half of the channel.
  the output is always `abs(signal) * 15`

toggling the sticks on the screen (OSD):

- use the keybind (by default `O`)
- use the button in the fpv setting

## use this or use [Minecraft FPV](https://www.curseforge.com/minecraft/mc-mods/fpv-drone)

why you should use this:

- you want to play in 1.20.1 and fabric (or forge, using [Sinytra Connector](https://www.curseforge.com/minecraft/mc-mods/sinytra-connector))
- you want a better performance. 1.20.1 fabric has
  more modern optimize mods
- you want to use a controller's input as a redstone signal

why you should use [Minecraft FPV](https://www.curseforge.com/minecraft/mc-mods/fpv-drone)

- you need a better and easy to use gui
- you need some more realistic visual effects, such as
  fish eye and noise
- you need a realistic physical simulation. 
This mod's physical simulation **feels** good, 
but is not realistic.
- you want a configurable drone

[//]: # (- you need better sound effect)

[//]: # (- you need to play in forge. This mod is in fabric,)

[//]: # (  and it **cannot** run correctly in forge)

[//]: # (  by [Sinytra Connector]&#40;https://www.curseforge.com/minecraft/mc-mods/sinytra-connector&#41; currently)

## advertisement

[simply snow](https://www.curseforge.com/minecraft/mc-mods/simply-snow)