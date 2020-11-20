# Popular Movies App

## App Functionality

An App to help users discover and filter between Popular and Top Rated movies from the themoviedb.org web API. 

The App displays a scrolling grid of movie posters, when a movie is selected a details screen is launched, which provides the user with more information about the movie, and a few trailers they which can watch on youtube.

This app utilizes the themoviedb.org web API core Android user interface components and fetches movie information using .

## Tech-Stack
<img src="https://media.giphy.com/media/nMuCPHmkBhoYOU6pQs/giphy.gif" width="300" align="right" hspace="20">

- Kotlin
- Dagger 2 (For Dependency Injection)
- Coroutines (For Managing Background Tasks)
- RxJava (For Managing Background Tasks - in a separate Branch)
- Retrofit (For Networking)
- JetPack
    - ViewModel (For managing UI related data in a lifecycle conscious way)
    - LiveData (For notifying views of data changes)
- Picasso (For displaying images)
- Architecture
    - MVVM (Used in Presentation Layer)