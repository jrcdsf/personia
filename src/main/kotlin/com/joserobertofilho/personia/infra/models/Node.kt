package com.joserobertofilho.personia.infra.models

data class Node(
    val id: Long,
    val name: String,
    val parentNode: Node?,
    val children: Set<Node>
)
