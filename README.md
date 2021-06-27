# Foititopoli
[![Desktop Jar](https://github.com/UoM2021-CSC402-Team15/foititopoli/actions/workflows/desktop.yml/badge.svg)](https://github.com/UoM2021-CSC402-Team15/foititopoli/actions/workflows/desktop.yml)  [![Android Apk](https://github.com/UoM2021-CSC402-Team15/foititopoli/actions/workflows/android.yml/badge.svg)](https://github.com/UoM2021-CSC402-Team15/foititopoli/actions/workflows/android.yml)  
![GitHub](https://img.shields.io/github/license/UoM2021-CSC402-Team15/foititopoli)  [![GitHub stars](https://img.shields.io/github/stars/UoM2021-CSC402-Team15/foititopoli)](https://github.com/UoM2021-CSC402-Team15/foititopoli/stargazers) [![GitHub forks](https://img.shields.io/github/forks/UoM2021-CSC402-Team15/foititopoli)](https://github.com/UoM2021-CSC402-Team15/foititopoli/network) [![YouTube Video Views](https://img.shields.io/youtube/views/YWZ7AvJJJXA?style=social)](https://youtu.be/YWZ7AvJJJXA?list=LL)

<details>
 <summary><strong>Table of Contents</strong> (click to expand)
</summary>

* [General info](#general-info)
* [Technologies](#technologies)
* [Releases](#releases)
* [Setup](#setup)
* [Documentation](#documentation)
* [Debugging](#debugging)
* [License](#license)

  
  </details>

<img width="647" alt="image" src="https://user-images.githubusercontent.com/31484500/123337047-59c2bf80-d54f-11eb-872e-0f31779674cc.png">

## General info
4th Semester Team Project  
Software Engineering course at the University of Macedonia

Foititopoli is a variation of the classic board game Monopoly where the player takes the role of a student in the university.


## Technologies
Project built with:
* libGDX Framework: 1.10.0

## Releases
You can get a runnable jar file of the latest commits [here](https://github.com/UoM2021-CSC402-Team15/foititopoli/actions)

## Setup
```shell
$ git clone https://github.com/UoM2021-CSC402-Team15/foititopoli
$ cd foititopoli
```

### Building jar for Windows/Linux/MacOS
```shell
$ ./gradlew desktop:dist        (Linux/MacOS)
  or
$ .\gradlew.bat desktop:dist    (Windows)
```
Resulting jar file is in /desktop/build/libs/

### Building apk for Android Devices
```shell
$ ./gradlew android:assembleRelease        (Linux/MacOS)
  or
$ .\gradlew.bat android:assembleRelease    (Windows)
```
WARNING! Resulting apk is unsigned. It will NOT install on Android devices unless you sign it. See more [here](https://developer.android.com/studio/publish/app-signing).  
Resulting apk file is in android/build/outputs/apk/release/
### Building for iOS Devices
###### Coming Soon

## Documentation
You can find documentation for the project [here](https://github.com/UoM2021-CSC402-Team15/foititopoli/tree/master/.Documentation).  
JavaDoc is also available [here](https://uom2021-csc402-team15.github.io/foititopoli/javadoc/).

## Debugging

### Using the in game console
When in-game you can press the <kbd>`</kbd> or <kbd>~</kbd> key to activate the custom debug window. Press the key again to hide the window.  
Input the command in the bottom textbox and press <kbd>Enter</kbd> to run it.

### Available Commands

### move

moves the current player to the designated square
<details>
  <summary>Square Location Chart</summary>

![Screenshot 2021-06-26 at 20-40-04 Φοιτητόπολη Data](https://user-images.githubusercontent.com/31484500/123521345-c6a89780-d6be-11eb-9ae3-518adaefffde.png)
</details>

```properties
move <side>,<square>          ex. move 2,6 (values from 0,0 to 3,9)
```
### hours
Adds the input integer to the total of the current player (use a negative integer to remove)
```properties
hours <value>                 ex. hours -600
```

### card
Forces the current player to pick a random card
```properties
card
```
### select
Switches to the selected player
```properties
select <index>                 ex. select 3 (index of the player with 1 as the first value)
```


## License
This project is licensed under the GNU GPL V3.0 - see the [LICENSE](LICENSE) file for details.
