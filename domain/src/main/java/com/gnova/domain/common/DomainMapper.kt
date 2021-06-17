package com.gnova.domain.common

interface DomainMapper <E, D> {

    fun mapToDomain(entity: E) : D

    fun mapToEntity(domainModel: D) : E
}