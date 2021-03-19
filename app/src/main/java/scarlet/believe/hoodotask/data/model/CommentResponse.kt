package scarlet.believe.hoodotask.data.model

data class CommentResponse(
    val data: List<CommentData>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val total: Int
)