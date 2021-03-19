package scarlet.believe.hoodotask.data.model

data class CommentData(
    val id: String,
    val owner: Owner,
    val publishDate: String,
    val message: String
)