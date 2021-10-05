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


## Architecture
<img src="https://github.com/gnovakov/PopularMovies_Kotlin/blob/master/appModules.jpg" width="500" align="right" hspace="20">

The app follows Uncle Bob's Clean Architecture with three layers, App, Data and Domain.

This app is small so it is not necessary to have a separate module for each layer, but I've implemented the modules as an example of Clean architecture and how it works, also if in the future I develop the app further, as the app grows it maintains strict coding discipline and separation of concerns where different modules with specific responsibilities make it easier for future modification and maintenance, also it keeps the code flexible where anything can be easily changed due to loose coupling



### Domain Module

Our Domain model/layer is the inner most layer and is completely isolated from the rest of the application, it has no access the other layers, and is a generic pure Kotlin layer, it contains our base data models/Domain entities, Interfaces for our Repository and a Mapper to map data between our Domain and Data layers. This layer is where we would also have our use cases/interactors but for simplicity's sake as the app is not large I decided to omit these. 

#### Domain Module/Layer Components:

- Domain Entities/Models (in this case called Movie & Trailer) - These describe the basic data structures we would work with, they are the building blocks of our application as the app is built around these data. They are pure data classes thus they do not have any annotations for phasing libraries to work with, they are pure and it is irrelevant how the data is being fetched, that is the job of the upper layers.

- Use Case/Interactors - A use case contains a single specific task that can be performed by the application. Use cases are used by the upper layers to interact with the Domain layers Models/Entities. *Again in this app I’ve decided to omit the use cases for simplicity's sake and directly use the Repository instead as the app is not very big.

- Interfaces (in this case Repository & Mapper) - The interfaces in the domain layer are the contract that the upper layers must follow, these are just interfaces and have no implementation detail, because the details don’t matter in the Domain layer, this is part of our separation of concerns, the Domain layer does not have to worry about how the implementation is handled, if it needs to change the Domain layer stays the same as it is isolated from the small details and changes that might occur.



### Data Module

Our Data layer can be found one step up from our Domain layer, it can access our Domain layer but has no access further up the chain. The Data layers purpose is to provide the data the application needs. In the Data layer we find concrete implementations of the data providers our app is using, such as Room or Retrofit (only Retrofit in this instance) and the models it requires, we also have some other implementations here such as Mappers that help Map Data between the Data and Domain layers and the implementation of the Repository.

#### Data Module/Layer Components:

- Data Provider Implementation (In this case Retrofit) - The data provider implementations help handle the Getting and setting of our data from an Api, to a database, or cache etc… In this case we are only using Retrofit to Get data from the themoviedb.org web API.

- Data Entities/Models or DTOs (in this case called Movie & Trailer) - When using libraries such as Retrofit we need to parse the data provided by this Data Provider with libraries such as Gson or Moshi, these libraries provide annotations we need to apply to our entities to make the parsing work, but because we want to keep our Domain layer isolated, we create entities in the data layer where we can implement these annotations, we would then use Mappers to map the data between our Data and Domain layers.

- Interface Implementations (In this case the Mapper Class and the Repository) - This is where we implement the Interfaces we created in our Domain layer, these allow us to communicate between the Data and Domain layers while still keeping our Domain layer isolated. Because of the simplicity of the app the Repository Implementation does not need to do any checks for cache etc… we only have methods which directly invoke the API.




### Presentation/App Module

The Presentation/App module is the UI and Framework layer in the application, it is our top most layer and has access to all the layers below it. It is the closest to what the user sees on the screen, it contains our activities, fragments, view models, adapters etc… it also contains our Dependency Injection implementation (Dagger) and our Data Providers Configuration (Retrofit in this case).

#### Presentation/App Module/Layer Components:

- MainActivity - Acts as our NavHostFragment, which loads our fragments (HomeFragment & Detail Fragment)

- ViewModels (HomeViewModel & DetailViewModel) - The ViewModels invoke our Repository methods according to user actions or Automatically (in our case it is automatic when a screen/fragment loads getMovies & getTrailers), it also updates our live data which is then observed in our fragments.

- ViewState - A sealed class which defines the state of our activity/fragment, the state is delivered via LiveData

- Dependency Injection Implementation & Data Providers Configuration - In our case we are using Dagger 2 for our Dependency Injection and Retrofit as our only Data Provider to fetch data from the themoviedb.org web API.




### Data Flow

The below diagram shows the data flow when loading Movies or Trailers in the current itteration of the app:

<img src="https://github.com/gnovakov/PopularMovies_Kotlin/blob/master/dataFlow.jpg" width="1600" align="centre" hspace="20">


#### Data Flow Note: 

as the Use Cases would sit in our Domain layer the View Model would call them first, and they would in turn call the Repository in the Data layer, the Repo would do its thing (manage between Api, Internal storage, cache etc...) and send the response to the Mapper in turn Mapping the data between the Data and Domain models and sending the response back to the Use Cases who in turn gives the App module what it was looking for.

But how would the domain layer be able to access the data layer if the Domain layer is isolated and has no access to the layers above?

Well because we have created our Repository Interface in the Domain layer and implemented it on our Data layer, our Use Cases have access to the methods in our Repository Interface while still keeping the Domain layer isolated and blind to whatever goes on in our Data layer, thats the benifit of interfaces we get in this instance.
