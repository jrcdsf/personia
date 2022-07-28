package com.joserobertofilho.personia.infra.entities

import javax.persistence.*

@Entity
@Table(name = "employee")
data class EmployeeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long=0,

    @Column(nullable = false)
    val name: String ="",

    @Column(name = "supervisor_id")
    val supervisorId: Long? = null
    )
