package com.joserobertofilho.personia.domain.boundaries

interface HierarchyUseCaseInterface {
    fun createHierarchy(relationships: Map<String, String>): MutableMap<String, Set<String>>
    fun getSupervisorAndSeniorSupervisorByEmployee(name: String): Map<String, Any>
    fun getFullHierarchy(): Map<String, Any>?
}