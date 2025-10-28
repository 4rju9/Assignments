package app.netlify.dev4rju9.vijayiassignment

import app.netlify.dev4rju9.vijayiassignment.model.Dish
import org.junit.Assert.*
import org.junit.Test
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class NavigationArgumentsTest {

    val list = listOf(
        Dish(
            "Butter Chicken Rice",
            "Spicy & Creamy / Delicious",
            "https://foodish-api.com/images/butter-chicken/butter-chicken22.jpg"
        ),
        Dish(
            "Spicy Burger",
            "Spicy & Hot / Burger",
            "https://foodish-api.com/images/burger/burger2.jpg"
        ),
        Dish(
            "Masala Dosa",
            "Chatpata & Masala / Dosa",
            "https://foodish-api.com/images/dosa/dosa2.jpg"
        ),
        Dish(
            "Special Pasta",
            "Special Creamy / Pasta",
            "https://foodish-api.com/images/pasta/pasta2.jpg"
        )
    )

    @Test
    fun `Encoding of arguments, while navigation`() {

        list.forEach { dish ->
            val name = URLEncoder.encode(dish.name, StandardCharsets.UTF_8.toString())
            val desc = URLEncoder.encode(dish.description, StandardCharsets.UTF_8.toString())
            val url = URLEncoder.encode(dish.url, StandardCharsets.UTF_8.toString())

            assertFalse(name.contains(" "))
            assertFalse(desc.contains("/"))
            assertTrue(url.contains("%2F"))
        }
    }

    @Test
    fun `Decoding of arguments, while navigation` () {

        list.forEach { dish ->

            var name = URLEncoder.encode(dish.name, StandardCharsets.UTF_8.toString())
            var desc = URLEncoder.encode(dish.description, StandardCharsets.UTF_8.toString())
            var url = URLEncoder.encode(dish.url, StandardCharsets.UTF_8.toString())

            name = URLDecoder.decode(name, "UTF-8")
            desc = URLDecoder.decode(desc, "UTF-8")
            url = URLDecoder.decode(url, "UTF-8")

            assertEquals(dish.name, name)
            assertEquals(dish.description, desc)
            assertEquals(dish.url, url)

        }

    }

}