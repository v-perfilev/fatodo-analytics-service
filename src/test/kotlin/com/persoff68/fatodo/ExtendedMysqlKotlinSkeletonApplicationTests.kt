package com.persoff68.fatodo

import com.persoff68.fatodo.annotation.WithCustomSecurityContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class ExtendedMysqlKotlinSkeletonApplicationTests(@Autowired val mvc: MockMvc) {

    @Test
    @WithCustomSecurityContext
    fun contextLoads() {
        main(arrayOf())
        mvc.get("/")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    @WithCustomSecurityContext
    fun testWrongPath() {
        mvc.perform(MockMvcRequestBuilders.get("/wrong-path"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

}
