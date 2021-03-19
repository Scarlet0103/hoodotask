package scarlet.believe.hoodotask.data.model

data class Response(
    val data: List<Data>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val total: Int
)