Project maintained by teabot, contact via IRC
## Summary ##
A Java API for controlling home lighting systems. Initially I'll be working with a Rako system.

Initial project goals are to allow the setting of room scenes from a Java based API via the Rako Ethernet bridge. If this is successful the plan is to also implement features necessary to configure/program Rako devices in a similar manner to RakoSoft.

I would like to first use Candela to build a Rako control interface for the [SqueezeBox](http://www.logitechsqueezebox.com/).

## Current equipment ##
  * 500w dimmer
  * 7 button control panel
  * Rako Ethernet bridge
  * RakoSoft
  * iPhone with Rako app

## Method ##
  1. Construct a minimal Rako system with one channel and an Ethernet bridge **(Complete)**
  1. Contact Rako to see if they have an API available. **(Complete)**
  1. Was sent a simple API for URL and UDP based selection of scenes.
  1. Obtain connectivity between the bridge and the iPhone app and / or RakoSoft on a network that allows snooping **(Complete)**
  1. I connected a wireless access point, the Rako Ethernet bridge, and a MacBook to a 10baseT hub. This allowed monitoring of the UDP commands sent from the iPhone app.
  1. Monitor traffic between iPhone app / RakoSoft / Bridge Web UI to determine a suitable candidate for reverse engineering
  1. Okay, so this is somewhat simpler with my new initial goal of using the Rako telnet protocol.
  1. Create some simple tests that deliver observed packets to the bridge **(In progress)**
  1. Reverse enginner protocol
  1. Implement the protocol with Java :-)
  1. Started. Have a unit test running that changes a scene.

## Status ##
  * I have had a little snoop on the RakoSoft traffic with [WireShark](http://www.wireshark.org/) - the iPhone app uses UDP.
  * Connected via telnet - issued via RS232 commands
  * Noticed that bridge eventually accepts an address by DHCP
  * API coming along nicely
  * Discovers bridge using NetBIOS
  * Builds house configuration from RakoBridge XML config.
  * Models Rako House / Room / Channel / Scene