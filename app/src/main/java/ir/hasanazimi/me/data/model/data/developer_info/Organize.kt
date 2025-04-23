package ir.hasanazimi.me.data.model.data.developer_info

data class Organize(
    val finishedPositionDate: String,
    val organizeLogo: String,
    val organizeName: String,
    val positionTitle: String,
    val positionType: String,
    val projects: List<Project>,
    val startedPositionDate: String
)