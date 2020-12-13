# Paper Cop 👮

The traditional "Kallanum Polisum" game android-ized.

> Save Paper 📖, Save Trees 🌲, Save the World! 🗺️ 😜

## Download 📥

- [Download APK](https://github.com/theapache64/paper-cop/releases/latest/download/app-release.apk)

## Screenshots 📱

![](montage.png)

## Rules 📏

The game begins with a minimum of 3 players and contains mainly 3 characters, namely King 👑 (1000 points), Thief 👺  (0 points), and Police 👮 (700 points).

Characters are shuffled and distributed among the players with each player picking one character. After that players are ready to find out their character (which shall not be revealed to anybody). The player who got Police then has to identify the ‘Thief’ from the other 2 remaining players. 

If the Police, guesses correctly then the points are retained, or else he/she surrenders them to the Thief. Several rounds of this game are played before counting the points. The player with the highest score wins the game. 

If there are more than 3 more players, there will be more characters such as Queen 👸 (900 points), Minister 👨 (800 points), and Warrior 🗡️ (300) points. The number of warriors is limited to the remaining number of players.

The King has the highest points followed by the Queen. Points are awarded to the police or thief based on whether the police guesses the identity of the thief correctly.

## Files 📁

```
.
├── App.kt
├── core
│   └── Director.kt
├── data
│   ├── local
│   │   ├── entities
│   │   │   └── players
│   │   │       ├── PlayerEntity.kt
│   │   │       └── PlayersDao.kt
│   │   └── PaperCopDatabase.kt
│   └── repo
│       └── PlayersRepo.kt
├── di
│   └── modules
│       └── DatabaseModule.kt
├── feature
│   ├── base
│   │   ├── BaseActivity.kt
│   │   └── BaseViewModel.kt
│   ├── count
│   │   ├── CountActivity.kt
│   │   └── CountViewModel.kt
│   ├── find
│   │   ├── FindThiefActivity.kt
│   │   ├── FindThiefAdapter.kt
│   │   └── FindThiefViewModel.kt
│   ├── inputplayers
│   │   ├── InputPlayersActivity.kt
│   │   └── InputPlayersViewModel.kt
│   ├── pick
│   │   ├── PickActivity.kt
│   │   └── PickViewModel.kt
│   ├── players
│   │   ├── PlayersActivity.kt
│   │   ├── PlayersAdapter.kt
│   │   └── PlayersViewModel.kt
│   └── splash
│       ├── SplashActivity.kt
│       └── SplashViewModel.kt
├── model
│   └── Role.kt
├── utils
│   ├── extensions
│   │   ├── ContextExt.kt
│   │   └── SnackbarExt.kt
│   └── livedata
│       └── SingleLiveEvent.kt
└── widget
    └── PrimaryButton.kt

21 directories, 28 files
```

## Author ✍️

- theapache64