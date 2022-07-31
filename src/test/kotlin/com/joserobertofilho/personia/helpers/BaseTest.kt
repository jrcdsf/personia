package com.joserobertofilho.personia.helpers

import com.fasterxml.jackson.databind.ObjectMapper

open class BaseTest {
    protected val objectMapper: ObjectMapper = ObjectMapper()
}