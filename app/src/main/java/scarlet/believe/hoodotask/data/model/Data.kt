package scarlet.believe.hoodotask.data.model

data class Data(
    val id: String,
    val image: String,
    val likes: Int,
    val link: Any,
    val owner: Owner,
    val publishDate: String,
    val tags: List<String>,
    val text: String
)