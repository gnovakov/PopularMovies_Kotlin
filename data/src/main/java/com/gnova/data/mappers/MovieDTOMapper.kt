package com.gnova.data.mappers

import com.gnova.data.models.MovieDTO
import com.gnova.domain.common.DomainMapper
import com.gnova.domain.models.Movie
import javax.inject.Inject

class MovieDTOMapper @Inject constructor() : DomainMapper<MovieDTO, Movie> {

    override fun mapToDomain(entity: MovieDTO): Movie {
        return Movie(
            id = entity.id,
            vote_average = entity.vote_average,
            title = entity.title,
            release_date = entity.release_date,
            backdrop_path = entity.backdrop_path,
            overview = entity.overview,
            poster_path = entity.poster_path
        )
    }

    override fun mapToEntity(domainModel: Movie): MovieDTO {
        return MovieDTO(
            id = domainModel.id,
            vote_average = domainModel.vote_average,
            title = domainModel.title,
            release_date = domainModel.release_date,
            backdrop_path = domainModel.backdrop_path,
            overview = domainModel.overview,
            poster_path = domainModel.poster_path
        )
    }

    fun mapToDomainList(movieDTOs: List<MovieDTO>): List<Movie> {
        return movieDTOs.map {
            mapToDomain(it) }
    }

    fun mapToEntityList(movies: List<Movie>): List<MovieDTO> {
        return movies.map {
            mapToEntity(it) }
    }
}