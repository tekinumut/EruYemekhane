# EruYemekhane

EruYemekhane is an Android application that displays the cafeteria lists of Erciyes University to show one approach to using some of the best practices in Android Development. Inspired from [Guide to app architecture.](https://developer.android.com/jetpack/guide)

### Libraries

- MVVM (design pattern)
- Navigation (single activity architecture)
- [Retrofit](https://github.com/square/retrofit) With Coroutines (for fetch data)
- LiveData and Flow
- Room (for access fetched data offline)
- Hilt (for dependency injection)
- DataStore ( for storage of simple data)
- PreferencesFragment ( for settings page)
- [Jsoup](https://jsoup.org/) (for parse html)
- [Leak Canary](https://github.com/square/leakcanary) (for detect memory leaks)
- [Truth](https://github.com/google/truth) (for testing)

<img src="https://user-images.githubusercontent.com/33953921/125138224-ee641a80-e116-11eb-9894-0c691fae8262.png" width="24" height="24" align="left">
To build project please remove anything related to signingConfigs and firebase.
</br></br>

# Pages

### Home

- Contains menus for personnel and students.
- The user can see the total calories of today's food along with the calories of each food.
- You can access OptionsMenu to navigate Settings/About pages.

<img src="https://user-images.githubusercontent.com/33953921/125132484-0a62be80-e10d-11eb-944d-16cc85c4becd.png" width="288" height="512">&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; <img src="https://user-images.githubusercontent.com/33953921/125132490-0cc51880-e10d-11eb-8fa8-85cfda6e66ad.png" width="288" height="512">

### Settings

Settings page includes this options.

- Switch between light/dark mode.
- Disable ads for 1 month to watch a rewardAd.
- Enable/Disable auto fetch data on the launch of app.

<img src="https://user-images.githubusercontent.com/33953921/125133471-99240b00-e10e-11eb-9f79-70fe3ba0301b.png" width="288" height="512">

### About

- Informs the user about how to benefit features of app.
- Send feedback.
- Access to Github Page of app.

<img src="https://user-images.githubusercontent.com/33953921/125133625-c7a1e600-e10e-11eb-8bf1-415fc4967646.png" width="288" height="512">
