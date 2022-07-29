package com.joserobertofilho.personia.infra.repositories

import com.joserobertofilho.personia.infra.entities.EmployeeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<EmployeeEntity, Long> {
    fun findByName(name: String): EmployeeEntity?

    @Query(
        value = "select e.\"name\", m.\"name\" as manager, mm.\"name\" as senior from employee e left join employee m on m.id = e.supervisor_id left join employee mm on mm.id = m.supervisor_id where e.\"name\" = ?1",
        nativeQuery = true
    )
    fun retrieveHierarchyByEmployeeName(name: String): Map<String, Any>

    @Query(
        value = "select e.\"name\", m.\"name\" as manager from employee e left join employee m on m.id = e.supervisor_id",
        nativeQuery = true
    )
    fun retrievePartialHierarchy(): List<Map<String, String>>

    @Query(value = "select * from employee where supervisor_id = ?1", nativeQuery = true)
    fun findEmployeesBySupervisorId(supervisorId: Long): Set<EmployeeEntity>?

    @Query(value = "select * from employee where supervisor_id is null", nativeQuery = true)
    fun findSeniorSupervisor(): EmployeeEntity?


}