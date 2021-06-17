# Popular Movies App

## App Functionality

An App to help users discover and filter between Popular and Top Rated movies from the themoviedb.org web API. 

The App displays a scrolling grid of movie posters, when a movie is selected a details screen is launched, which provides the user with more information about the movie, and a few trailers they which can watch on youtube.

This app utilizes the themoviedb.org web API core Android user interface components and fetches movie information using .

## Tech-Stack
<img src="https://media.giphy.com/media/nMuCPHmkBhoYOU6pQs/giphy.gif" width="300" align="right" hspace="20">

- Kotlin
- Dagger 2 (For Dependency Injection)
- rxJava (For Managing Background Tasks)
- Retrofit (For Networking)
- JetPack
    - ViewModel (For managing UI related data in a lifecycle conscious way)
    - LiveData (For notifying views of data changes)
- Picasso (For displaying images)
- Architecture
    - Clean Architecture
    - MVVM

### *How to preview the app

To preview the app please download your own API key from themoviedb.org

Create a util package in (data/src/main/java/com/gnova/data) if it doesn't already exist and create a Constants kotlin object in that util folder and put your API key there like so:

```Kotlin
object Constants {

    const val API_KEY =  "yourApiKeyHere"
}

```